/**
 * 
 */
package pl.edu.pw.elka.adziedzi.ships.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * Inform player about state or special situation of game.
 *
 * @author Adam Dziedzic grupa K1I1
 * May 29, 2009
 * view
 */
public class InformationFrame extends JDialog
{
   
    /** for serialization: */
    private static final long serialVersionUID = 1L;
    
    /** Icon for OptionPane */
    private BufferedImage icon;
     
    /** 
     * Construct information frame.
     * 
     * @param message message set in frame
     * @param title title of frame
     * @param frame frame parent
     */
    public InformationFrame(final String message, final String title, final FrameView frame)
    {
       // true confirm that window is modal
       super(frame, title, true);
       JTextArea text = new JTextArea(message);
       text.setDisabledTextColor(Color.black);
       text.setEnabled(false);
       add(text, BorderLayout.CENTER);
       // Button "OK" closes window. 
       JButton ok = new JButton("Ok");
       ok.addActionListener(new ActionListener()
          {
             public void actionPerformed(ActionEvent event)
             {
                setVisible(false);
                dispose();
             }
          });
       JPanel panel = new JPanel();
       panel.add(ok);
       add(panel, BorderLayout.SOUTH);
       //set icon
       loadIcon();
       setIconImage(icon);
       setResizable(false);
       // set size - it depends on the length of message
       setSize(250, 150);
       // set information frame inside frame of parent
       Point point = frame.getLocation();
       Dimension size = frame.getSize();
       setLocation((int)(point.x + (size.getWidth()-250)/2), (int)(point.y + (size.getHeight()-150)/2));
       setVisible(true);  
    }
    
    /** load icon for JOptionPane */
    private void loadIcon()
    {
        try
        {
            URL imgUrl = getClass().getResource("images/logo.gif");
            icon = ImageIO.read(imgUrl);
        }
        catch( IOException e )
        {
            e.printStackTrace();
        }
    }
    
}
