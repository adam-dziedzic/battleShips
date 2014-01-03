/**
 * 
 */
package pl.edu.pw.elka.adziedzi.ships.view;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.util.concurrent.BlockingQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import pl.edu.pw.elka.adziedzi.ships.common.FIELD_STATE;
import pl.edu.pw.elka.adziedzi.ships.view.messages.MainFrameClosed;
import pl.edu.pw.elka.adziedzi.ships.view.messages.MessageView;



/**
 * Frame of view - here is main view.
 * Object Frame contains all view components.
 *
 * @author Adam Dziedzic grupa K1I1
 * May 22, 2009
 * view
 */
class FrameView extends JFrame 
{   
    /** for serialization: */
    private static final long serialVersionUID = 1L;
    
    /** width of sea */
    public static final int seaWidth = 250;
    /** height of sea */
    public static final int seaHeight = 250;
    
    /** distance between seas horizontally */
    public static final int distanceX = 20;
    
    /** distance between seas vertically */
    public static final int distanceY = 20;
    
    /** width of frame */
    public static final int DEFAULT_WIDTH = seaWidth * 2 + distanceX * 3;
    
    /** height of frame */
    public static final int DEFAULT_HEIGHT = seaHeight * 3/2 + distanceX * 6;
 
    /** enemy sea */
    private EnemySeaView enemySea;
    
    /** home sea */
    private HomeView homeView;
    
    /** text area */
    private TextView textView;
    
    /** queue for sending messages from view to controller */
    private static BlockingQueue<MessageView> queue = null;
        
    /** FrameView - it must be only one object of class FrameView. */
    private static FrameView frameView = null;

    /**
     * Creates instance of class FrameView.
     * 
     * @param queue Blocking queue.
     * @return Instance (which is only one) of class FrameView. 
     */
    public static FrameView instance(BlockingQueue<MessageView> blockingQueue)
    {
        if(frameView == null)
        {    
            queue = blockingQueue;
            frameView = new FrameView();
        }
        return frameView;
    }

    /** 
     * Constructor of Frame.
     * Modifier private - secure only one instance of class FrameView.
     * 
     * @param queue BlockingQueue for information from View to Controller
     */
    private FrameView()
    {
        // private methods for this constructor
        setFrameOnDisplay();
        setPanels();
        setVisible(true);
        // creates menu bar for frame
        setJMenuBar(new MenuView(queue, this));
    }
    
    /**
     * Set the base options of frame on display: title, image, size, location. 
     * (Ustawienia podstawowe - opcje ramki na ekranie. )
     */
    private void setFrameOnDisplay()
    {
   
        // frame title
        setTitle("Battleship");
        // set that it is impossible to change the size of frame
        setResizable(false);
        // to check size of display
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        // set width and height of frame
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        //set the location of frame in the central place of display
        setLocation((screenWidth-DEFAULT_WIDTH)/2, (screenHeight-DEFAULT_HEIGHT)/2);
        // set image
        URL imgUrl = getClass().getResource("images/logo.gif");
        Image img = kit.getImage(imgUrl);
        setIconImage(img);
        // set mode of window closing
        addWindowListener(new FrameCloser());
    }
        
    private void setPanels()
    {
        JPanel mainPanel = new JPanel();
        // set frame without LayoutManager
        mainPanel.setLayout(null);
        //mainPanel.setBackground(Color.gray);
        // add mainPanel to frame
        add(mainPanel);
        // Enemy Sea ////////////////////////////////////////////////////////////////////
        JLabel enemySeaTitle = new JLabel("Enemy Sea");
        mainPanel.add(enemySeaTitle);
        enemySeaTitle.setBounds(distanceX, distanceY - 15, seaWidth, 15);
        // argument are: position x, position y, width, height
        enemySea = new EnemySeaView(distanceX, distanceY, seaWidth, seaHeight, queue);
        mainPanel.add(enemySea);
        
        // Home View/////////////////////////////////////////////////////////////////////
        // set this panel in second part of frame
        homeView = new HomeView(distanceX * 2 + seaWidth, 0, seaWidth, seaHeight * 3/2 + distanceY * 3, queue);
        mainPanel.add(homeView);
        
        // Text Area ////////////////////////////////////////////////////////////////////
        JLabel textViewTitle = new JLabel("Messages");
        mainPanel.add(textViewTitle);
        textViewTitle.setBounds( distanceX , seaHeight + distanceY * 3 - 15 , seaWidth, 15);
        textView = new TextView( distanceX, seaHeight + distanceY * 3, seaWidth, seaHeight/2);
        mainPanel.add(textView);   
    }
    
    /**
     * Class of object which closes window. 
     */
    private class FrameCloser extends WindowAdapter
    {
        public void windowClosing(WindowEvent e)
        {
            close();
            try
            {
                queue.put(new MainFrameClosed());
            }
            catch(InterruptedException exception)
            {
            	exception.printStackTrace();
            }
        }
    }

    /** 
     * Close frame.
     */
    public void close()
    {
        frameView.setVisible(false);
        frameView.dispose();
    }
    
    /**
     * 
     * @return the enemySea
     */
    public void setEnemySea(final FIELD_STATE [][] sea)
    {
        enemySea.setSea(sea);
    }

    /**
     * 
     * @return the home sea
     */
    public void setHomeSea(final FIELD_STATE [][] sea)
    {
        homeView.setHomeSea(sea);
    }
    
    /**
     * 
     * @return the textView
     */
    public void appendTextView(String text)
    {
        textView.append(text);
    }
    
    /**
     * Disable AircraftButton
     *
     */
    public void disableButtons()
    {
    	homeView.disableButtons();
    }
    
    /**
     * Enable AircraftButton
     *
     */
    public void enableButtons()
    {
    	homeView.enableButtons();
    }
    
    /**
     * Disable enemy sea.
     * 
     */
     public void disableEnemySea()
     {
    	 enemySea.disableSea();
     }
     
     /**
      * Enable enemy sea.
      * 
      */
      public void enableEnemySea()
      {
     	 enemySea.enableSea();
      }
}