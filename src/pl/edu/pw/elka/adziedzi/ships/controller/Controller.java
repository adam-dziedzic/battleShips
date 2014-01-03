/**
 * Package controller - control communication between model and view.
 */
package pl.edu.pw.elka.adziedzi.ships.controller;

import static pl.edu.pw.elka.adziedzi.ships.common.Constants.AIRCRAFT_NUMBER_Of_MASTS;
import static pl.edu.pw.elka.adziedzi.ships.common.Constants.BOAT_NUMBER_OF_MASTS;
import static pl.edu.pw.elka.adziedzi.ships.common.Constants.FRIGATE_NUMBER_OF_MASTS;
import static pl.edu.pw.elka.adziedzi.ships.common.Constants.SUBMARINE_NUMBER_OF_MASTS;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import pl.edu.pw.elka.adziedzi.ships.common.MyPoint;
import pl.edu.pw.elka.adziedzi.ships.common.PLAYER;
import pl.edu.pw.elka.adziedzi.ships.model.Model;
import pl.edu.pw.elka.adziedzi.ships.network.messages.ButtonsDisableMessage;
import pl.edu.pw.elka.adziedzi.ships.network.messages.ButtonsEnableMessage;
import pl.edu.pw.elka.adziedzi.ships.network.messages.ClientClosedMessage;
import pl.edu.pw.elka.adziedzi.ships.network.messages.DisableEnemySeaMessage;
import pl.edu.pw.elka.adziedzi.ships.network.messages.EnableEnemySeaMessage;
import pl.edu.pw.elka.adziedzi.ships.network.messages.EnemySeaServerMessage;
import pl.edu.pw.elka.adziedzi.ships.network.messages.FirstPlayerWinnerMessage;
import pl.edu.pw.elka.adziedzi.ships.network.messages.HomeSeaServerMessage;
import pl.edu.pw.elka.adziedzi.ships.network.messages.SecondPlayerWinnerMessage;
import pl.edu.pw.elka.adziedzi.ships.network.messages.ServerClosedMessage;
import pl.edu.pw.elka.adziedzi.ships.network.messages.ServerMessage;
import pl.edu.pw.elka.adziedzi.ships.network.messages.TextServerMessage;
import pl.edu.pw.elka.adziedzi.ships.view.View;
import pl.edu.pw.elka.adziedzi.ships.view.messages.HitMessage;
import pl.edu.pw.elka.adziedzi.ships.view.messages.MainFrameClosed;
import pl.edu.pw.elka.adziedzi.ships.view.messages.MessageView;
import pl.edu.pw.elka.adziedzi.ships.view.messages.NewGameMessage;
import pl.edu.pw.elka.adziedzi.ships.view.messages.ShipMessage;


/**
 * The interface of server controller.
 * 
 * @author Adam Dziedzic grupa: K1I1
 * May 20, 2009
 * controller
 */
public class Controller
{
    /** queue for receive messages from view */
	private BlockingQueue<MessageView> messageQueue;
    
    /** To send information from controller to client by serverOut */
    private BlockingQueue<ServerMessage> queueForServerOut;
    
    /** direct reference to view */
    private View view;
    
    /** direct reference to model */
    private Model model;
    
    /** valves for messages */
    private List<Valve> valves = new LinkedList<Valve>();
    
    /** Creates new controller.
     * 
     * @param view Direct reference to view.
     * @param queue Queue for messages from view. 
     */
    Controller(View view, BlockingQueue<MessageView> messageQueue ,
                BlockingQueue<ServerMessage> queueForServerOut)
    {
        this.view = view;
        this.messageQueue = messageQueue;
        this.queueForServerOut = queueForServerOut;
        this.model = Model.instance();
        
        addValves();
        // begin main loop of game
        mainLoop();
    }
    
    /**
     * The main loop of game. 
     * It takes new messages from queue and executes them.
     *
     */
    private void mainLoop()
    {
        ValveResponse response = ValveResponse.EXECUTED;
        MessageView message = null;
        
        while(response != ValveResponse.FINISH)
        {
            // take message from messageQueue
            try
            {
            	
                message = (MessageView)messageQueue.take();
            }
            catch(InterruptedException e)
            {
            	e.printStackTrace();
            }
            
            // look for valve which can execute message
            for(Valve valve : valves)
            {
            
               response = valve.execute(message);
               // check: if message was not executed continue -> else (executed or finish of game): break loop
               if(response != ValveResponse.MISS)
                  break;
            }
        }
    }
   
    /**
     * Add valves to list.
     *
     */
    private void addValves()
    {
        valves.add(new HitValve());
        valves.add(new ShipValve());
        valves.add(new NewGameValve());
        valves.add(new MainFrameClosedValve());
    }
    
    /** to determined response of valve object which excecutes message */
    enum ValveResponse 
    { 
        /** this valve can not execute this message */
        MISS, 
        /** this valve executed message */
        EXECUTED, 
        /** first player (server) closed main frame */
        FINISH
    };
    
    /** interface for classes which execute messages */
    private interface Valve
    {
        /** execute action in response to message */
        public ValveResponse execute(MessageView message);
    }
    
    /////////////////////////////////////////////////////////////////////////////////////
    /**
     * Execute messages which informs about finish of game when first 
     * or second player finished game by main frame closing.
     *  
     */
    private class MainFrameClosedValve implements Valve
    {
        /** executes message 
         * 
         * @param message Message for execute.
         */
        public ValveResponse execute(MessageView message)
        {
            if(message.getClass() != MainFrameClosed.class)
            {
                return ValveResponse.MISS;
            }
            
            if(message.player == PLAYER.FIRST)
            {
                // cause that thread ServerOut is closed and give information to Client
                // that server is closed
                try
                {
                    queueForServerOut.put(new ServerClosedMessage());
                }
                catch(InterruptedException e)
                {
                	e.printStackTrace();
                }
                return ValveResponse.FINISH;
            }
                // message.player == PLAYER.SECOND: second player is not connected
                view.addMessage("Warning! Opponent is not connected!\n");
                view.InformAndCloseFrame("Opponent is not connected", "Network Information");
                try
                {
                    // to close ClientIn thread
                    queueForServerOut.put(new ClientClosedMessage());
                }
                catch(InterruptedException e)
                {
                	e.printStackTrace();
                }
            return ValveResponse.EXECUTED;
        }
    }
    /////////////////////////////////////////////////////////////////////////////////////
    /**
     * Execute message which inform about point which player hit.
     *  
     */
    private class HitValve implements Valve
    {   
    	/** message about hit */
    	private HitMessage hitMessage;
    	
    	/** PLAYER player who hit */
    	private PLAYER playerHit;
    	
        /** 
         * executes hit message
         * 
         * @param message Message for execute.
         */
        public ValveResponse execute(MessageView message)
        {	
            // check if this valve can execute message
            if(message.getClass() != HitMessage.class)
            {
                return ValveResponse.MISS;
            }
            hitMessage = (HitMessage)message;
            // take player who hits (who is active during battle)
            playerHit = model.getHitPlayer();
            // check if this is a ship deployment part of game (if there is no player who hits)
            if( playerHit == null)
            {
                return ValveResponse.EXECUTED;
            }
            // if this player shouldn't hit
            if(hitMessage.player != playerHit)
            {
            	thisPlayerShouldNotHit();
                return ValveResponse.EXECUTED;
            }
            // when it is battle part of game - then every hit is caught by model
            model.hit(hitMessage.getPoint());
            sendInformation(playerHit);  // send information - it depends on the player who hit
            checkWinners();
            return ValveResponse.EXECUTED;
        }
        
        /**
         * Send information to player if he should not hit.
         *
         */
        private void thisPlayerShouldNotHit()
        {
        	if(hitMessage.player == PLAYER.FIRST)
            {
                view.addMessage("You have to wait for your turn.\n"); 
            }
            else
            {
                try
                {
                    queueForServerOut.put(new TextServerMessage("You have to wait for your turn.\n"));
                }
                catch(InterruptedException e)
                {
                	e.printStackTrace();
                }
            }
        }
        
        /**
         * Send information to players after hit.
         *
         * @param player Player who hit.
         */
        private void sendInformation(PLAYER player)
        {
            // then we have to refresh seas
            if(player == PLAYER.FIRST)
            {
                view.setEnemySea(model.getSecondPlayerSeaForOpponent());
                try
                {
                    queueForServerOut.put(new HomeSeaServerMessage(model.getSecondPlayerSeaOwn()));
                }
                catch(InterruptedException e)
                {
                	e.printStackTrace();
                }
            }
            else // Player Second has hit.
            {
                view.setHomeSea(model.getFirstPlayerSeaOwn());
                try
                {
                    queueForServerOut.put(new EnemySeaServerMessage(model.getFirstPlayerSeaForOpponent()));
                }
                catch(InterruptedException e)
                {
                	e.printStackTrace();
                }
            }
            // messages are ever sent
            view.addMessage(model.getMessageForFirstPlayer()); 
            try
            {
                queueForServerOut.put(new TextServerMessage(model.getMessageForSecondPlayer()));
            }
            catch(InterruptedException e)
            {
            	e.printStackTrace();
            }
        }
        
        /**
         * Check if player first or second won.
         *
         */
        private void checkWinners()
        {
        	if(model.isFirstWinner())
        	{
        		view.InformFrame("You are Winner, sir! Congratulations!", "Victory");
        		view.setEnemySea(model.getSecondPlayerSeaOwn());
        		view.disableEnemySea();
        		try
                {
                    queueForServerOut.put(new FirstPlayerWinnerMessage());
                    queueForServerOut.put(new EnemySeaServerMessage(model.getFirstPlayerSeaOwn()));
                    queueForServerOut.put(new DisableEnemySeaMessage());
                }
                catch(InterruptedException e)
                {
                	e.printStackTrace();
                }
                
        	}
        	else if(model.isSecondWinner())
        	{
        		view.InformFrame("Unfortunatelly. You lost, sir.", "Defeat");
        		view.setEnemySea(model.getSecondPlayerSeaOwn());
        		view.disableEnemySea();
        		try 
        		{
        			queueForServerOut.put(new SecondPlayerWinnerMessage());
                    queueForServerOut.put(new EnemySeaServerMessage(model.getFirstPlayerSeaOwn()));
                    queueForServerOut.put(new DisableEnemySeaMessage());
        		}
        		catch(InterruptedException e)
                {
        			e.printStackTrace();
                }
        	}
        	
        }
    }
    /////////////////////////////////////////////////////////////////////////////////////
    /**
     * Execute message which inform about new ship.
     *  
     */
    private class ShipValve implements Valve
    {
    	 /** ship message */
    	 private ShipMessage shipMessage;
    	 private boolean wasSentFirst = false;
    	 private boolean wasSentSecond = false;
    	 private boolean wasSentBattle = false;
    	
        /** 
         * executes message - ship location 
         * 
         * @param message Message for execute.
         */
        public ValveResponse execute(final MessageView message)
        {
            if(message.getClass() != ShipMessage.class)
            {
                return ValveResponse.MISS;
            }
            shipMessage = (ShipMessage)message;
            createShip(shipMessage);
            if(message.player == PLAYER.FIRST)
            {
                view.setHomeSea(model.getFirstPlayerSeaOwn());
                view.addMessage(model.getMessageForFirstPlayer());
                // check which ships are located and disable appropriate buttons
                checkLocatedShipsFirst();
            }
            else
            {
                try
                {
                    queueForServerOut.put(new HomeSeaServerMessage(model.getSecondPlayerSeaOwn()));
                    queueForServerOut.put(new TextServerMessage(model.getMessageForSecondPlayer()));
                    checkLocatedShipsSecond();
                }
                catch(InterruptedException e)
                {
                	e.printStackTrace();
                }
            }
            // Check if all ships are located (fleets are set)
            if(model.areFleetsSet() && wasSentBattle == false)
            {
                view.addMessage("All Ships are located.\n" + "Battle !!!");
                try
                {
                    queueForServerOut.put(new TextServerMessage("All Ships are located.\n" + "Battle !!!"));
                }
                catch(InterruptedException e)
                {
                	e.printStackTrace();
                }
                wasSentBattle = true;
            }
            return ValveResponse.EXECUTED;
        }
        
        /**
         * To create ship on the sea.
         *
         * @param message Message contains location of ship and determined player
         */
        private void createShip(final ShipMessage message)
        {
            ArrayList<MyPoint> location = message.getLocation();
            // at the beginning create local variable of ship size and player who 
            // is locating ship
            int size = location.size();
            PLAYER player = message.player;
            // the most frequently are created boats 
            if(size == BOAT_NUMBER_OF_MASTS)
            {
                model.createBoat(player, location);
            }
            else
                if(size == FRIGATE_NUMBER_OF_MASTS)
                {
                    model.createFrigate(player, location);
                }
                else
                    if(size == SUBMARINE_NUMBER_OF_MASTS)
                    {
                        model.createSubmarine(player, location);
                    }   
                    else
                        if(size == AIRCRAFT_NUMBER_Of_MASTS)
                        {
                            model.createAircraft(player, location);
                        }
        }
        
        /**
         * Check which ships are located and disable appropriate buttons.
         *
         */
        private void checkLocatedShipsFirst()
        {
        	if(model.isFirstPlayerFleetSet() && wasSentFirst == false)
        	{
        		view.addMessage("You have been created your fleet.\n");
        		view.disableButtons();
        		try
                {
                    queueForServerOut.put(new TextServerMessage("Your opponent created all ships!\n"));
                }
                catch(InterruptedException e)
                {
                	e.printStackTrace();
                }
                wasSentFirst = true;
        	}
        }
        
        /**
         * Check which ships are located and disable appropriate buttons.
         *
         */
        private void checkLocatedShipsSecond()
        {
        	if(model.isSecondPlayerFleetSet() && wasSentSecond == false)
        	{
        		view.addMessage("Your opponent created all ships!\n");
        		try
                {
                    queueForServerOut.put(new TextServerMessage("You have been created your fleet.\n"));
                    queueForServerOut.put(new ButtonsDisableMessage());
                }
                catch(InterruptedException e)
                {
                	e.printStackTrace();
                }
                wasSentSecond = true;
        	}
        }
        
    }
    
    /**
     * Execute new game.
     *
     */
    private class NewGameValve implements Valve
    {
    	public ValveResponse execute(MessageView message) 
    	{
    		if(message.getClass() != NewGameMessage.class)
            {
                return ValveResponse.MISS;
            }
    		
    		model = Model.refreshModel();
    		
    		view.setEnemySea(model.getSecondPlayerSeaForOpponent());
    		view.setHomeSea(model.getFirstPlayerSeaOwn());
    		view.addMessage("\n\nShip Deployment.\n");
    		view.enableButtons();
    		view.enableEnemySea();
    		try
            {
                queueForServerOut.put(new EnemySeaServerMessage(model.getFirstPlayerSeaForOpponent()));
                queueForServerOut.put(new HomeSeaServerMessage(model.getFirstPlayerSeaOwn()));
                queueForServerOut.put(new TextServerMessage("\n\nShip deployment.\n"));
                queueForServerOut.put(new ButtonsEnableMessage());
                queueForServerOut.put(new EnableEnemySeaMessage());
            }
            catch(InterruptedException e)
            {
            	e.printStackTrace();
            }
    		return ValveResponse.EXECUTED;
    	}
    }
}
