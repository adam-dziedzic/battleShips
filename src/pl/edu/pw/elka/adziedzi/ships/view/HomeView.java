/**
 * 
 */
package pl.edu.pw.elka.adziedzi.ships.view;

import java.util.concurrent.BlockingQueue;

import javax.swing.JButton;
import javax.swing.JLabel;

import pl.edu.pw.elka.adziedzi.ships.common.FIELD_STATE;
import pl.edu.pw.elka.adziedzi.ships.view.messages.MessageView;


import java.awt.event.*;
/**
 * Home View represents home sea and buttons for creates home ships.
 *
 * @author Adam Dziedzic grupa K1I1
 * May 26, 2009
 * view
 */
class HomeView extends PanelView
{

    /** for serialization: */
    private static final long serialVersionUID = 1L;
    
    /** home sea */
    private HomeSeaView homeSea;
    
    /** buttons view for ship deployment */
    private ButtonView buttonView;
    
    /**
     * Constructor for HomeView.
     * 
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public HomeView(int x, int y, int width, int height, BlockingQueue<MessageView> queue)
    {
        super(x, y, width, height);
        setLayout(null);
        
        // Home Sea /////////////////////////////////////////////////////////////////////
        JLabel homeSeaTitle = new JLabel("Home Sea");
        add(homeSeaTitle);
        homeSeaTitle.setBounds( 0, FrameView.distanceY - 15, FrameView.seaWidth, 15);
        // arguments are: position x, position y, width, height
        homeSea = new HomeSeaView( 0, FrameView.distanceY, FrameView.seaWidth, FrameView.seaHeight, queue);
        add(homeSea);
        
        // Buttons for ship deployment /////////////////////////////////////////////////
        JLabel buttonViewTitle = new JLabel("Create ship");
        add(buttonViewTitle);
        buttonViewTitle.setBounds( 0, FrameView.seaHeight + FrameView.distanceY * 3 - 15 , FrameView.seaWidth, 15);
        buttonView = new ButtonView( 0, FrameView.seaHeight + FrameView.distanceY * 3, FrameView.seaWidth, FrameView.seaHeight/2);
        add(buttonView);
    }
    
    /**
     * set home sea
     *
     * @param home Sea
     */
    public void setHomeSea(final FIELD_STATE [][] sea)
    {
        homeSea.setSea(sea);
    }
    
    /**
     * Disable AircraftButton
     *
     */
    public void disableButtons()
    {
    	buttonView.disableButtons();
    }
    
    /**
     * Enable AircraftButton
     *
     */
    public void enableButtons()
    {
    	buttonView.enableButtons();
    }
    
    /**
     * ButtonView stores buttons for ship deployment.
     * 
     */
    private class ButtonView extends PanelView
    {
        
        /** for serialization: */
        private static final long serialVersionUID = 1L;
        
        private JButton createAircraft = new JButton ("   Create Aircraft   ");
        private JButton createSubmarine = new JButton(" Create Submarine");
        private JButton createFrigate = new JButton  ("   Create Frigate    ");
        private JButton createBoat = new JButton     ("     Create Boat      ");
        
        ActionListener aircraftListener;
        ActionListener submarineListener;
        ActionListener frigateListener;
        ActionListener boatListener;
        
        /**
         * Creates area for buttons and determines its position and size.
         * 
         * @param x position x of textView
         * @param y position y of textView
         * @param width
         * @param height
         */
        public ButtonView(int x, int y, int width, int height)
        {
            super(x, y, width, height);
            
            add(createAircraft);
            add(createSubmarine);
            add(createFrigate);
            add(createBoat);
            
            addListeners();
        }
        
        private void addListeners()
        {
            aircraftListener =  new ActionListener()
            {
               public void actionPerformed(ActionEvent e)
               {
                   homeSea.setCursorsAircraft();
               }
                
            };
            createAircraft.addActionListener(aircraftListener);
            
            
            submarineListener =  new ActionListener()
            {
               public void actionPerformed(ActionEvent e)
               {
                   homeSea.setCursorsSubmarine();
               }
                
            };
            createSubmarine.addActionListener(submarineListener);
            
            frigateListener = new ActionListener()
            {
               public void actionPerformed(ActionEvent e)
               {
                   homeSea.setCursorsFrigate();
               }
                
            };
            createFrigate.addActionListener(frigateListener);
            
            
            boatListener =  new ActionListener()
            {
               public void actionPerformed(ActionEvent e)
               {
                   homeSea.setCursorsBoat();
               }
                
            };
            createBoat.addActionListener(boatListener);
        }
        
        /**
         * Disable AircraftButton.
         *
         */
        public void disableButtons()
        {
        	createAircraft.removeActionListener(aircraftListener);
        	createSubmarine.removeActionListener(submarineListener);
        	createFrigate.removeActionListener(frigateListener);
        	createBoat.removeActionListener(boatListener);
        }

        /**
         * Enable AircraftButton.
         *
         */
        public void enableButtons()
        {
        	createAircraft.addActionListener(aircraftListener);
        	createSubmarine.addActionListener(submarineListener);
        	createFrigate.addActionListener(frigateListener);
        	createBoat.addActionListener(boatListener);
        }
    }
    
}
