/**
 * 
 */
package pl.edu.pw.elka.adziedzi.ships.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.BlockingQueue;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import pl.edu.pw.elka.adziedzi.ships.view.messages.MainFrameClosed;
import pl.edu.pw.elka.adziedzi.ships.view.messages.MessageView;
import pl.edu.pw.elka.adziedzi.ships.view.messages.NewGameMessage;


/**
 * Menu for frameView.
 *
 * @author Adam Dziedzic grupa K1I1
 * May 6, 2009
 * view
 */
class MenuView extends JMenuBar
{
    /** for serialization: */
    private static final long serialVersionUID = 1L;
    
    /** Frame which contains this menuView. */
    private FrameView frame;
    
    /** Menu item to begin new game. */
    private JMenuItem newGame;
    
    /** Menu item to exit game. */
    private JMenuItem exitGame;
    
    /** Menu item with help. */
    private JMenuItem about;
    
    /** Menu item with information about author. */
    private JMenuItem authorOfGame;
    
    /** queue for sending messages from view to controller: here for exit game from JMenuItem */
    private BlockingQueue<MessageView> queue = null;
    
    /** 
     * Constructor - menu bar.
     * 
     * @param blockingQueue queue needed for message Exit Game and New Game
     */
    MenuView(BlockingQueue<MessageView> queue, final FrameView frame)
    {
        this.queue = queue;
        this.frame = frame;
        setMenuFile();
        setMenuOptions();
    }
    
    /** private auxiliary method for constructor to create JMenu - File  (metoda pomocnicza)*/
    private void setMenuFile()
    {
        JMenu fileMenu = new JMenu("File");
        
        newGame = new JMenuItem("New Game");
        fileMenu.add(newGame);
        newGame.addActionListener(new NewGameListener());
        
        fileMenu.addSeparator();
        
        exitGame = new JMenuItem("Exit Game");
        fileMenu.add(exitGame);
        exitGame.addActionListener(new ExitGameListener());
        
        add(fileMenu);
    }
    
    /** set JMenu - Options */
    private void setMenuOptions()
    {
        JMenu optionsMenu = new JMenu("Options");
        
        about = new JMenuItem("About Ships");
        optionsMenu.add(about);
        about.addActionListener(new AboutListener());        
       
        optionsMenu.addSeparator();
        
        authorOfGame = new JMenuItem("Author");
        optionsMenu.add(authorOfGame);
        authorOfGame.addActionListener(new AuthorListener());
        
        add(optionsMenu);
    }
    
    /** Listener for ExitGame - JMenuItem */
    private class ExitGameListener implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            try 
            {
                queue.put(new MainFrameClosed());
            }
            catch(InterruptedException exception)
            {
            	exception.printStackTrace();
            }
            frame.close();
        }
    }
    
    /** Listener for New Game - JMenuItem */
    private class NewGameListener implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
        	try 
            {
                queue.put(new NewGameMessage());
            }
            catch(InterruptedException execption)
            {
            	execption.printStackTrace();
            }
        }
    }
    
    /** Listener for About - JMenuItem */
    private class AboutListener implements ActionListener
    {
        private String message = " Ships: \n" +
                "1 x Aircraft\n" +
                "2 x Submarines\n" +
                "3 x Frigates\n" +
                "4 x Boats\n";
        
        public void actionPerformed(ActionEvent event)
        {
            new InformationFrame(message, "About", frame);
        }
    }
    
    /** Listener for Author - JMenuItem */
    private class AuthorListener implements ActionListener
    {
        private String message = " Adam Dziedzic \n e-mail: adam.cajf@gmail.com" +
                "\n\n version: 27.05.2009";
        
        public void actionPerformed(ActionEvent event)
        {
            new InformationFrame(message, "Author", frame);
        }
    }
 
}
