/**
 * 
 */
package pl.edu.pw.elka.adziedzi.ships.view;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.concurrent.SynchronousQueue;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import pl.edu.pw.elka.adziedzi.ships.network.SERVER_CLIENT;
import pl.edu.pw.elka.adziedzi.ships.network.messages.NetMessage;

    
/**
 * Chooser for Server or Client - at the beginning of game.
 * 
 * @author Adam Dziedzic grupa: K1I1
 * May 21, 2009
 * view
 */
public class ServerClientChooserView extends JFrame
{
    /** for serialization: */
    private static final long serialVersionUID = 1L;
    
    /** Width of frame. */
    private static final int DEFAULT_WIDTH = 320;
    /** Height of frame. */
    private static final int DEFAULT_HEIGHT = 250;
    
    /** Information about choice: server or client, and address of server. */
    private static NetMessage netMessage = new NetMessage();
    /** server or client option */
    private static SynchronousQueue<NetMessage> queue = null;
    
    /** to choose if server or client */
    private ButtonGroup buttonGroup;
    /** to write a server address */
    private JTextField addressIP;
    /** to commit the information */
    private JButton okButton;
    
    /** panel for buttonGroup */
    private JPanel radioButtonPanel;
    /** panel for text field */
    private JPanel textPanel;
    /** panel for button OK */
    private JPanel okPanel;
   
    /** ServerClientChooserView - it must be only one object of this class. */
    private static ServerClientChooserView chooser = null;
    
    public static ServerClientChooserView instance(SynchronousQueue<NetMessage> synchronousQueue)
    {
        if(chooser == null)
        {    
            queue = synchronousQueue;
            
            SwingUtilities.invokeLater(new Runnable()
            {
                public void run()
                {
                    chooser = new ServerClientChooserView();
                }
            });
                
        }
        return chooser;
    }
    
    /** 
     * Constructor of ServerClientChooser Frame. 
     */
    private ServerClientChooserView()
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
        setTitle("Battleship: Server Client Chooser");
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
        // set cursor - the pointer was established at the top of mast
        Cursor cursor = kit.createCustomCursor(img, new Point( 15, 0), "ships");
        setCursor(cursor);
    }
    
    private void setPanels()
    {
        // group: server, client
        buttonGroup = new ButtonGroup();
        // first panel for buttons
        radioButtonPanel = new JPanel();
        // second panel for textField
        textPanel = new JPanel();
        // third panel for button Ok
        okPanel = new JPanel();
        
        // add radio button to buttonGroup
        addRadioButton("Server", SERVER_CLIENT.SERVER);
        addRadioButton("Client", SERVER_CLIENT.CLIENT);
        
        // add textField for addressIP
        addressIP = new JTextField("127.0.0.1", 20);
        // set not enable text at the beginnig, activated when client is chosen
        addressIP.setEnabled(false);
        textPanel.add(addressIP);
        
        // add ok button
        okButton = new JButton("OK");
        okPanel.add(okButton);
        okButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                netMessage.setServerAddress(addressIP.getText().trim());
                // send message to mainController
                try
                {   
                    queue.put(netMessage);
                }
                catch(InterruptedException e)
                {
                	e.printStackTrace();
                }
                setVisible(false);
                dispose();
            }
        });
        
        
        Border etched = BorderFactory.createEtchedBorder();
        // set border (obramowanie) for radioButtons
        Border titledButton = BorderFactory.createTitledBorder(etched, "Choose the mode of connection:");
        radioButtonPanel.setBorder(titledButton);
        // set border (obramowanie) for text
        Border titledText = BorderFactory.createTitledBorder(etched, "Client: give address of server");
        textPanel.setBorder(titledText);

        // set layout of frame
        setLayout(new GridLayout(3, 1));
        
        // add panels to frame
        add(radioButtonPanel);
        add(textPanel);
        add(okPanel);
        
    }
    
    private void addRadioButton(String buttonName, final SERVER_CLIENT serverClient)
    {
       JRadioButton button = new JRadioButton(buttonName);
       button.addActionListener(new ActionListener()
          {
             public void actionPerformed(ActionEvent event)
             {
                netMessage.setServerClientOption(serverClient);
                if(serverClient == SERVER_CLIENT.CLIENT)
                {
                    addressIP.setEnabled(true);
                }
                else
                {
                    addressIP.setEnabled(false);
                    addressIP.setText("");
                }
             }
          });
       buttonGroup.add(button);
       radioButtonPanel.add(button);
    }
    
}
