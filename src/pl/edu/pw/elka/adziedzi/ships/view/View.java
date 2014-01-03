/**
 * 
 */
package pl.edu.pw.elka.adziedzi.ships.view;

import java.util.concurrent.BlockingQueue;

import javax.swing.SwingUtilities;

import pl.edu.pw.elka.adziedzi.ships.common.FIELD_STATE;
import pl.edu.pw.elka.adziedzi.ships.common.PLAYER;
import pl.edu.pw.elka.adziedzi.ships.view.messages.MainFrameClosed;
import pl.edu.pw.elka.adziedzi.ships.view.messages.MessageView;



/**
 * The interface for view.
 * 
 * @author Adam Dziedzic grupa: K1I1
 * May 2, 2009
 * view
 */
public class View
{
    /** Queue for sending messages from view to controller. */
    private static BlockingQueue<MessageView> queue = null;
        
    /** View - it must be only one object of class View. */
    private static View view = null;
    
    /** Determines if view belongs to FirstPlayer(Server) or SecondPlayer(Client) */
    private static PLAYER player;
    
    /** Frame - main frame of game. */
    private static FrameView frame;

    /**
     * Creates instance of class View.
     * 
     * @param queue BlockingQueue for View to send messages to controller.
     * @param playerChoice Determines which player use this view (FIRST == SERVER, SECOND == CLIENT)
     * @return Instance (which is only one) of class View. 
     */
    public static View instance(BlockingQueue<MessageView> blockingQueue, final PLAYER playerChoice)
    {
        if(view == null)
        {    
            queue = blockingQueue;
            player = playerChoice;
            
            SwingUtilities.invokeLater(new Runnable()
            {
                public void run()
                {
                    view = new View();
                }
            });
                
        }
        return view;
    }
    
    /**
     * Default constructor.
     * Creates frame and gets 3 special parts of frame which are used to 
     * presents state of game.
     *
     */
    private View()
    {
        frame = FrameView.instance(queue);
    }
    
    /**
     * @return player Player who uses this view.  
     */
    public static PLAYER getPlayer()
    {
        return player;
    }
    
    /**
     * Set opponent (enemy) sea.
     */
    public void setEnemySea(final FIELD_STATE[][] sea)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                frame.setEnemySea(sea);
            }
        });
    }
    
    /**
     * Set home sea.
     */
    public void setHomeSea(final FIELD_STATE[][] sea)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                frame.setHomeSea(sea);
            }
        });
    }
    
    /**
     * Append message for player.
     * It appends messages at the end of text field.
     * 
     * @param String String to append to text field.
     */
    public void addMessage(final String textMessage)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                frame.appendTextView(textMessage);
            }
        });
    }
    
    /**
     * Display information that this server is not available
     * and close main window.
     *
     * @param message Message which caused that the client view should be closed.
     * @param title Title of child frame.
     */
    public void InformAndCloseFrame(final String message, final String title)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                new InformationFrame(message, title, frame );
                try
                {
                    queue.put(new MainFrameClosed());
                }
                catch(InterruptedException e)
                {
                	e.printStackTrace();
                }
                frame.close();
            }
        });
    }
    
    /**
     * Display information in child frame.
     *
     * @param message Message which caused that the client view should be closed.
     * @param title Title of child frame.
     */
    public void InformFrame(final String message, final String title)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                new InformationFrame(message, title, frame );
            }
        });
    }
    
    /**
     * Disable buttons after ship deployment.
     *
     */
    public void disableButtons()
    {
    	 SwingUtilities.invokeLater(new Runnable()
         {
             public void run()
             {
            	frame.disableButtons();
             }
         });
    }
    
    /**
     * Enable buttons.
     *
     */
    public void enableButtons()
    {
    	 SwingUtilities.invokeLater(new Runnable()
         {
             public void run()
             {
            	frame.enableButtons();
             }
         });
    }
    
    /**
     * Disable enemy sea there is winner.
     *
     */
    public void disableEnemySea()
    {
    	SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
            	frame.disableEnemySea();
            }
        });
    }
    
    /**
     * Enable enemy sea there is winner.
     *
     */
    public void enableEnemySea()
    {
    	SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
            	frame.enableEnemySea();
            }
        });
    }
    
}
