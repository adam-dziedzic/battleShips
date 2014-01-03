/**
 * 
 */
package pl.edu.pw.elka.adziedzi.ships.view;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Frame to inform client that this server is not available.
 *
 * @author Adam Dziedzic grupa K1I1
 * May 28, 2009
 * view
 */
class CommunicationFrame extends JFrame
{
    /** for serialization: */
    private static final long serialVersionUID = 1L;
    
    /** Width of frame. */
    private static final int DEFAULT_WIDTH = 200;
    /** Height of frame. */
    private static final int DEFAULT_HEIGHT = 150;
    
    /** message to display */
    private String message;
    /** to write a message */
    private JLabel labelMessage;
    /** to commit the information */
    private JButton okButton;
    /** panel for text field */
    private JPanel labelPanel;
    /** panel for button OK */
    private JPanel okPanel;
   
    /** it must be only one object of this class. */
    private static CommunicationFrame server = null;
    
    public static CommunicationFrame instance(String message)
    {
        if(server == null)
        {    
            SwingUtilities.invokeLater(new Runnable()
            {
                public void run()
                {
                    server = new CommunicationFrame();
                }
            });
                
        }
        return server;
    }
    
    /** 
     * Constructor of ThisServerIsNotAvailable Frame. 
     */
    public CommunicationFrame()
    {
        // private methods for this constructor
        setFrameOnDisplay();
        setPanels();
        setVisible(true);
    }
    
    /**
     * Set the base options of frame on display: title, image, size, location. 
     * 
     */
    private void setFrameOnDisplay()
    {
        // frame title
        setTitle("Server");
        // set that it is impossible to change the size of frame
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        // set cursor - the pointer was established at the top of mast
        Cursor cursor = kit.createCustomCursor(img, new Point( 15, 0), "ships");
        setCursor(cursor);
    }
    
    private void setPanels()
    {
     
        // panel for textField
        labelPanel = new JPanel();
        // panel for button Ok
        okPanel = new JPanel();
       
        // add textField for addressIP
        labelMessage = new JLabel(message);
        labelPanel.setLayout(new BorderLayout());
        labelPanel.add(labelMessage);
        
        // add ok button
        okButton = new JButton("OK");
        okPanel.add(okButton);
        okButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                setVisible(false);
                dispose();
            }
        });
        
      
        // set layout of frame
        setLayout(new GridLayout(2, 1));
      
        add(labelPanel);
        add(okPanel);
        
    }
   
}
