/**
 * 
 */
package pl.edu.pw.elka.adziedzi.ships.view;

import static pl.edu.pw.elka.adziedzi.ships.common.Constants.COLS;
import static pl.edu.pw.elka.adziedzi.ships.common.Constants.ROWS;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.EnumMap;
import java.util.concurrent.BlockingQueue;

import javax.imageio.ImageIO;

import pl.edu.pw.elka.adziedzi.ships.common.FIELD_STATE;
import pl.edu.pw.elka.adziedzi.ships.view.messages.MessageView;



/**
 * Presents all sea with its fields in an appropriate state. 
 *
 * @author Adam Dziedzic grupa K1I1
 * May 2, 2009
 * view
 */
class SeaView extends PanelView
{
    /** for serialization: */
    private static final long serialVersionUID = 1L;
    
    /** Define width of one field on the sea. */
    private static int imageWidth;
    
    /** Define height of one field on the sea. */
    private static int imageHeight;
    
    /** Store link between Field state and his image representation: MISS -> missImg */   
    private static EnumMap<FIELD_STATE, BufferedImage> images = null;
    
    /** queue for sending messages from view to controller */
    private BlockingQueue<MessageView> queue = null;
    
    /** Represents sea and the state of fields on the sea. */
    private FIELD_STATE[][] sea;
    
    /**
     * Constructor for sea.
     * 
     * @param x position x
     * @param y position y
     * @param width width of panel
     * @param height height of panel
     */
    public SeaView(int x, int y, int width, int height, BlockingQueue<MessageView> queue)
    {
        
        super(x, y, width, height);
        
        this.queue = queue;
        
        // load images - auxiliary method if images have not been loaded
        if(images == null)
        {
            loadImages();
        }
        
        // initialize sea - all fields as unused
        sea = new FIELD_STATE[ROWS][COLS];
        
        for(int i=0; i<ROWS; i++)
            for(int j=0; j<COLS; j++)
            {
                sea[i][j] = FIELD_STATE.UNUSED;
            }
    }
    
    public void setSea(FIELD_STATE [][] newSea)
    {
        sea = newSea;
        repaint();
    }
    
    /**
     * Draw all Sea - draw field by field which is selected from
     * table "sea" and each state of field has represetation in image stored in EnumMap ("images")
     * 
     */
    public void paintComponent(Graphics g)
    {
       // Draw sea.
       for (int i = 0; i < ROWS; i++)
          for (int j = 0; j < COLS; j++)
          {
              g.drawImage((Image)images.get(sea[i][j]), i * imageWidth, j * imageHeight, imageWidth, imageHeight, null);          
          }      
    }
    
    /**
     * Auxiliary method which loads images and set their size.
     * 
     */
    private void loadImages()
    {
        URL unusedUrl = getClass().getResource("images/unused.gif");
        URL missUrl = getClass().getResource("images/miss.gif");
        URL shipUrl = getClass().getResource("images/ship.gif");
        URL hitUrl = getClass().getResource("images/hit.gif");
        URL destroyedUrl = getClass().getResource("images/destroyed.gif");
        
        try{
        BufferedImage unusedImg = ImageIO.read(unusedUrl);
        BufferedImage missImg = ImageIO.read(missUrl);
        BufferedImage shipImg = ImageIO.read(shipUrl);
        BufferedImage hitImg = ImageIO.read(hitUrl);
        BufferedImage destroyedImg = ImageIO.read(destroyedUrl);
        
        // put images and enums into the map
        images = new EnumMap<FIELD_STATE, BufferedImage>(FIELD_STATE.class);
        
        images.put(FIELD_STATE.UNUSED, unusedImg);
        images.put(FIELD_STATE.MISS, missImg);
        images.put(FIELD_STATE.SHIP, shipImg);
        images.put(FIELD_STATE.HIT, hitImg);
        images.put(FIELD_STATE.DESTROYED, destroyedImg);
        
        }
        catch( IOException e)
        {
            e.printStackTrace();
        }
        // image has width equals: size of panel / number of images in row
        imageWidth = FrameView.seaWidth / COLS;
        // image has height equals: size of panel / number of images in col
        imageHeight = FrameView.seaHeight / ROWS;
    }

    /**
     * @return the imageHeight
     */
    public static int getImageHeight()
    {
        return imageHeight;
    }

    /**
     * @return the imageWidth
     */
    public static int getImageWidth()
    {
        return imageWidth;
    }
    
    /**
     * Send message to controller.
     *
     * @param message
     */
    public void sendMessage(final MessageView message)
    {
        try
        {
            queue.put(message);
        }
        catch(InterruptedException execption)
        {
        	execption.printStackTrace();
        }
    }
     
}
