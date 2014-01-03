/**
 * Package model - store the information for controller.
 */
package pl.edu.pw.elka.adziedzi.ships.model;

import java.util.ArrayList;

import pl.edu.pw.elka.adziedzi.ships.common.FIELD_STATE;
import pl.edu.pw.elka.adziedzi.ships.common.MyPoint;
import pl.edu.pw.elka.adziedzi.ships.common.PLAYER;


/**
 * This class provides interface of model. 
 * Contains two internal states: 
 * 1) First (the first Player hits),
 * 2) Second (the second Player hits).
 * 
 * @author Adam Dziedzic grupa: K1I1
 * @version May 3, 2009
 * model
 */
public class Model
{
    // (Patterns: Facade, Singleton.)
    // (Pattern State:
    //  2 (two) internal states of model: First (the firstPlayer player hits),
    //                                    Second (the secondPlayer player hits) )
    
    /** Model model; It must be only one object of class Model. */
    private static Model model = null;
    
    /////////////////////////////////////////////////////////////////////////////////////
    /* Stores general information about game match (Składuje informacje o aktualnej rozgrywce.) */   
     
    // Model contains two players:
    /** First player. */
    private PlayerModel firstPlayer = new PlayerModel();
    
    /** Second player. */
    private PlayerModel secondPlayer = new PlayerModel();
    
    // Model determines relation between players: who hits and who defends.
    
    /** Informs about the round - who player hits. (Okresla ture gry.) 
     *  At the beginning the first player should hit. 
     */
    private PlayerState hitPlayer = null;
    
    /////////////////////////////////////////////////////////////////////////////////////
    
    /** Secure only one instance of class Model. */
    private Model()
    {}
    
    /**
     * Creates instance of class Model.
     * 
     * @return Instance (which is only one) of class Model. 
     */
    public static Model instance()
    {
        if(model == null)
        {
            model = new Model();
        }
        return model;
    }
    
    /**
     * Creates new instance of class Model.
     * 
     * @return New instance (which is only one) of class Model. 
     */
    public static Model refreshModel()
    {
    	if(model != null)
    	{
    	   model = new Model();
    	}
    	return model;
    }
    
    // The main part of GAME. ///////////////////////////////////////////////////////////
    /**
     * Implements an action of hit, which depends on the
     * state of model. (Player who hits.)
     * 
     * @param Point point in which plater hit
     * @return boolean Return true if operation executed, return false in another case
     *         (field has already been hit). 
     */
    public boolean hit(MyPoint point)
    {
        return hitPlayer.hit(point);
    }
    
    /**
     * @return Player who hits.
     */
    public PLAYER getHitPlayer()
    {
        if(hitPlayer == null) return null;
        
        return hitPlayer.getPlayer();
    }
    
    /**
     * Change a player who hits. (Zmienia gracza, ktory strzela.)
     * (It is executed in hit() method in PlayerState, 
     *  so it should not be used in controller. )
     *  
     */
    public void changePlayer()
    {
        hitPlayer.changePlayer();
    }
    
    //SHIP DEVELOPMENT. /////////////////////////////////////////////////////////////////
    /**
     * Create a ship with 4 masts.
     * 
     * @param player Determines the player who create ship. 
     * @param points Table of points, which determines location of ship on the sea.
     * @return boolean Return true if operation executed, return false in another case. 
     */
    public boolean createAircraft(PLAYER player, ArrayList<MyPoint> points)
    {
        // (Sprawdza czy podano odpowiednia liczbe masztow:)
        assert points.size() == Aircraft.numberOfMasts; 
        
        boolean result;
        // (Sprawdza, dla ktorego gracza utworzyc Aircraft (Lotniskowiec).)
        if(player == PLAYER.FIRST)
        {
            result =  firstPlayer.createAircraft(points);
        }
        else
        {
            result =  secondPlayer.createAircraft(points);
        }
        
        if(areFleetsSet())
        {
            // begin battle
            hitPlayer = FirstPlayerState.instance();
        }
        
        return result;
    }
    
    /**
     * Create a ship with 3 masts.
     * 
     * @param player Determines the player who create ship. 
     * @param points Table of points, which determines location of ship on the sea.
     * @return boolean Return true if operation executed, return false in another case. 
     */
    public boolean createSubmarine(PLAYER player, ArrayList<MyPoint> points)
    {
        // (Sprawdza czy podano odpowiednia liczbe masztow:)
        assert points.size() == Submarine.numberOfMasts;
        
        boolean result;
        // (Sprawdza, dla ktorego gracza utworzyc Submarine (Lodz podwodna).)
        if(player == PLAYER.FIRST)
        {
            result =  firstPlayer.createSubmarine(points);
        }
        else
        {
            result = secondPlayer.createSubmarine(points);
        }
        if(areFleetsSet())
        {
            // begin battle
            hitPlayer = FirstPlayerState.instance();
        }
        
        return result;
    }
    
    /**
     * Create a ship with 2 masts.
     * 
     * @param player Determines the player who create ship. 
     * @param points Table of points, which determines location of ship on the sea.
     * @return boolean Return true if operation executed, return false in another case. 
     */
    public boolean createFrigate(PLAYER player, ArrayList<MyPoint> points)
    {
        // (Sprawdza czy podano odpowiednia liczbe masztow:)
        assert points.size() == Frigate.numberOfMasts; 
        
        boolean result;
        // (Sprawdza, dla ktorego gracza utworzyc Frigate (Fregata).)
        if(player == PLAYER.FIRST)
        {
            result = firstPlayer.createFrigate(points);
        }
        else
        {
            result = secondPlayer.createFrigate(points);
        }
        
        if(areFleetsSet())
        {
            // begin battle
            hitPlayer = FirstPlayerState.instance();
        }
        
        return result;
        
    }
    
    /**
     * Create a ship with 1 mast.
     * 
     * @param player Determines the player who creates ship. 
     * @param points Table of points, which determines location of ship on the sea.
     * @return boolean Return true if operation executed, return false in another case. 
     */
    public boolean createBoat(PLAYER player, ArrayList<MyPoint> points)
    {
        // (Sprawdza czy podano odpowiednia liczbe masztow:)
        assert points.size() == Boat.numberOfMasts;
        
        boolean result;
        // (Sprawdza, dla ktorego gracza utworzyc Boat (Lodke).)
        if(player == PLAYER.FIRST)
        {
            result = firstPlayer.createBoat(points);
        }
        else
        {
            result = secondPlayer.createBoat(points);
        }
        
        if(areFleetsSet())
        {
            // begin battle
            hitPlayer = FirstPlayerState.instance();
        }
        
        return result;
    }
    
    /**
     * Check if fleets of first and second player are set.
     *
     * @return true if fleets are set and false in the other way
     */
    public boolean areFleetsSet()
    {
        return firstPlayer.isFleetSet() && secondPlayer.isFleetSet();
    }
    
    /**
     * Check if first player fleet is set.
     *
     * @return true if fleets are set and false in the other way
     */
    public boolean isFirstPlayerFleetSet()
    {
        return firstPlayer.isFleetSet();
    }
    
    /**
     * Check if second fleet is set.
     *
     * @return true if fleets are set and false in the other way
     */
    public boolean isSecondPlayerFleetSet()
    {
        return secondPlayer.isFleetSet();
    }
    
    /**
	 * Check if player first has won.
	 * 
	 * @return boolean Return true if player has won and false in another case.
	 */
    public boolean isFirstWinner()
    {
    	return secondPlayer.isLost();
    }
    
    /**
	 * Check if player second has won.
	 * 
	 * @return boolean Return true if player has won and false in another case.
	 */
    public boolean isSecondWinner()
    {
    	return firstPlayer.isLost();
    }
    
    // Messages: get /////////////////////////////////////////////////////////////////////
    /**
     * @return the messageForFirstPlayer
     */
    public String getMessageForFirstPlayer()
    {
         return firstPlayer.getMessageForMe() + secondPlayer.getMessageForOpponent();
    }

    /**
     * @return the messageForSecondPlayer
     */
    public String getMessageForSecondPlayer()
    {
        
        return secondPlayer.getMessageForMe() + firstPlayer.getMessageForOpponent();
    }

    /**
     * @return the firstPlayerSeaForOpponent
     */
    public FIELD_STATE[][] getFirstPlayerSeaForOpponent()
    {
        return firstPlayer.getSeaForOpponent();
    }

    /**
     * @return the firstPlayerSeaOwn
     */
    public FIELD_STATE[][] getFirstPlayerSeaOwn()
    {
        return firstPlayer.getSeaForMe();
    }

    /**
     * @return the secondPlayerSeaForOpponent
     */
    public FIELD_STATE[][] getSecondPlayerSeaForOpponent()
    {
        return secondPlayer.getSeaForOpponent();
    }

    /**
     * @return the secondPlayerSeaOwn
     */
    public FIELD_STATE[][] getSecondPlayerSeaOwn()
    {
        return secondPlayer.getSeaForMe();
    }
    
    //PlayerState////////////////////////////////////////////////////////////////////////
    
    // Getters for PlayerState.
    
    /**
     * @return the firstPlayer
     */
    private PlayerModel getFirstPlayer()
    {
        return firstPlayer;
    }
    
    /**
     * @return the secondPlayer
     */
    private PlayerModel getSecondPlayer()
    {
        return secondPlayer;
    }
    
    /**
     * @param hitPlayer the hitPlayer to set
     */
    private void setHitPlayer(PlayerState hitPlayer)
    {
        this.hitPlayer = hitPlayer;
    }
    
    /** 
     * Interface, which determines a player who hits: firstPlayer or secondPlayer. 
     */
    private interface PlayerState
    {
        /** 
         * Define method hit() for an appropriate state. 
         * 
         * @param point point to hit
         */ 
        public boolean hit(MyPoint point);
        
        /** 
         * Return player who hits. 
         */
        public PLAYER getPlayer();
        
        /**
         *  Change player: active player who should hit now. 
         */
        public void changePlayer();
    }
    
    /** State when first player hits. */
    private static class FirstPlayerState implements PlayerState
    {
        /** It must be only one object of class FirstPlayerState. */
        private static FirstPlayerState firstPlayerState = null;
        
        /** Secure only one instance of class FirstPlayerState. */
        private FirstPlayerState()
        {}
        
        /**
         * Creates an instance of class FirstPlayerState.
         * 
         * @return Instance (which is only one) of class FirstPlayerState. 
         */
        public static FirstPlayerState instance()
        {
            if(firstPlayerState == null)
            {
                firstPlayerState = new FirstPlayerState();
            }
            return firstPlayerState;
        }
        
        /** 
         * First player hits in an appropriate point on the sea of second Player.
         * 
         * @param Point point in which plater hit
         * @return boolean Return true if operation executed, return false in another case
         *         (field has already been hit). 
         */ 
        public boolean hit(MyPoint point)
        {   
            // result is true when player hit in unused field or mast and in 
            // another case result is false (player hit in filed which state was miss, hit or destroyed)
            boolean result = Model.instance().getSecondPlayer().opponentHit(point);
            if(result == true) changePlayer();
            return result;
        }
        
        /** 
         * @return First player.
         */
        public PLAYER getPlayer()
        {
            return PLAYER.FIRST;
        }
        
        /** 
         * Change player: active player second. 
         */
        public void changePlayer()
        {
            Model.instance().setHitPlayer(SecondPlayerState.instance());
        }
    }
    
    /** State when second player hits. */
    private static class SecondPlayerState implements PlayerState
    {
        /** It must be only one object of class SecondPlayerState. */
        private static SecondPlayerState secondPlayerState = null;
        
        /** Secure only one instance of class SecondPlayerState. */
        private SecondPlayerState()
        {}
        
        /**
         * Creates an instance of class SecondPlayerState.
         * 
         * @return Instance (which is only one) of class SecondPlayerState. 
         */
        public static SecondPlayerState instance()
        {
            if(secondPlayerState == null)
            {
                secondPlayerState = new SecondPlayerState();
            }
            return secondPlayerState;
        }
        
        /** 
         * Second player hits in an appropriate point on the sea of first Player. 
         * 
         * @param point point to hit
         * @return boolean Return true if operation executed, return false in another case
         *         (field has already been hit). 
         */ 
        public boolean hit(MyPoint point)
        {
            // result is true when player hit in unused field or mast with ship and in 
            // another case result is false
            boolean result =  Model.instance().getFirstPlayer().opponentHit(point);
            if(result == true) changePlayer();
            return result;
        }
        
        /**
         *  @return Second player. 
         */
        public PLAYER getPlayer()
        {
            return PLAYER.SECOND;
        }
        
        /** 
         * Change player: active player first. 
         * 
         */
        public void changePlayer()
        {
            Model.instance().setHitPlayer(FirstPlayerState.instance());
        }
    
    }
    
}

