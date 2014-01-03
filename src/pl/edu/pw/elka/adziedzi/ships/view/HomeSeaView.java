/**
 * 
 */
package pl.edu.pw.elka.adziedzi.ships.view;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import javax.imageio.ImageIO;

import pl.edu.pw.elka.adziedzi.ships.common.MyPoint;
import pl.edu.pw.elka.adziedzi.ships.view.messages.MessageView;
import pl.edu.pw.elka.adziedzi.ships.view.messages.ShipMessage;



/**
 * Home sea view.
 *
 * @author Adam Dziedzic grupa K1I1
 * May 26, 2009
 * view
 */
class HomeSeaView extends SeaView
{
 
    /** for serialization: */
    private static final long serialVersionUID = 1L;

    /* to determine the kind of cursor: */
   
    /** default cursor */
    private final Cursor defaultCursor = Cursor.getDefaultCursor();
    
    /** active cursor - predefined */
    private Cursor activeCursor = defaultCursor;
    
    /** ship located horizontally */
    private Cursor cursorHorizontally = defaultCursor;
    
    /** ship located vertically */
    private Cursor cursorVertically = defaultCursor;
    
    /** 4 masts vertically */
    private Cursor ver4;
    
    /** 4 masts horizontally */
    private Cursor hor4;
    
    /** 3 masts vertically */
    private Cursor ver3;
    
    /** 3 masts horizontally */
    private Cursor hor3;
    
    /** 2 masts vertically */
    private Cursor ver2;
    
    /** 2 masts horizontally */
    private Cursor hor2;
    
    /** 1 mast vertically */
    private Cursor ver1;
    
    /** 1 mast horizontally */
    private Cursor hor1; 
    
    /** 
     * Valves for events from mouse - to send points of ships on the sea.
     * The order is important for instant: it is better to boatValve was first 
     */
    private List<Valve> valves = new LinkedList<Valve>();
    
    /** list for coordinates of ships */
    private ArrayList<MyPoint> location = new ArrayList<MyPoint>();
    
    /**
     * Creates home sea and determines its position and size.
     * 
     * @param x position x of sea 
     * @param y position y of sea
     * @param width
     * @param height
     */
    public HomeSeaView(int x, int y, int width, int height, BlockingQueue<MessageView> queue)
    {
        super(x, y, FrameView.seaWidth, FrameView.seaHeight, queue);
        loadImages();
        addValves();
        addMouseListener(new MouseHandler());
    }
    
    /**
     * Images of ships and there are two images of every ship:
     * horizontally mode and vertically mode.
     * Auxiliary method for constructor.
     *
     */
    private void loadImages()
    {
        URL ver4Url = getClass().getResource("images/4ver.gif");
        URL hor4Url = getClass().getResource("images/4hor.gif");
        URL ver3Url = getClass().getResource("images/3ver.gif");
        URL hor3Url = getClass().getResource("images/3hor.gif");
        URL ver2Url = getClass().getResource("images/2ver.gif");
        URL hor2Url = getClass().getResource("images/2hor.gif");
        URL shipUrl = getClass().getResource("images/ship.gif");
        
        try{
        BufferedImage ver4Img = ImageIO.read(ver4Url);
        BufferedImage hor4Img = ImageIO.read(hor4Url);
        BufferedImage ver3Img = ImageIO.read(ver3Url);
        BufferedImage hor3Img = ImageIO.read(hor3Url);
        BufferedImage ver2Img = ImageIO.read(ver2Url);
        BufferedImage hor2Img = ImageIO.read(hor2Url);
        BufferedImage ver1Img = ImageIO.read(shipUrl);
        BufferedImage hor1Img = ImageIO.read(shipUrl);
        
        Toolkit tk = Toolkit.getDefaultToolkit();
        
        ver4 = tk.createCustomCursor(ver4Img, new Point(12,12), "ver4");
        hor4 = tk.createCustomCursor(hor4Img, new Point(12,12), "hor4");
        ver3 = tk.createCustomCursor(ver3Img, new Point(12,12), "ver3");
        hor3 = tk.createCustomCursor(hor3Img, new Point(12,12), "hor3");
        ver2 = tk.createCustomCursor(ver2Img, new Point(12,12), "ver2");
        hor2 = tk.createCustomCursor(hor2Img, new Point(12,12), "hor2");
        ver1 = tk.createCustomCursor(ver1Img, new Point(12,12), "ver1");
        hor1 = tk.createCustomCursor(hor1Img, new Point(12,12), "hor1");
        
        }
        catch( IOException e)
        {
            e.printStackTrace();
        }
  
    }
    
    /** add valves */
    private void addValves()
    {
        valves.add(new BoatValve());
        valves.add(new FrigateValve());
        valves.add(new SubmarineValve());
        valves.add(new AircraftValve());
    }
    
    /**
     * Set cursors 4 masts. 
     *
     */
    public void setCursorsAircraft()
    {
        activeCursor = ver4;
        cursorVertically = ver4;
        cursorHorizontally = hor4;
        setCursor(ver4);
    }
    
    /**
     * Set cursors 3 masts. 
     *
     */
    public void setCursorsSubmarine()
    {
        activeCursor = ver3;
        cursorVertically = ver3;
        cursorHorizontally = hor3;
        setCursor(ver3);
    }
    
    /**
     * Set cursors 2 masts. 
     *
     */
    public void setCursorsFrigate()
    {
        activeCursor = ver2;
        cursorVertically = ver2;
        cursorHorizontally = hor2;
        setCursor(ver2);
    }
    
    /**
     * Set cursors 1 mast. 
     *
     */
    public void setCursorsBoat()
    {
        activeCursor = ver1;
        cursorVertically = ver1;
        cursorHorizontally = hor1;
        setCursor(ver1);
    }
    
    private class MouseHandler extends MouseAdapter
    {
       public void mousePressed(MouseEvent event)
       {
           if(event.getModifiers() == MouseEvent.BUTTON1_MASK)
           {
               // take point which was clicked
               Point p = event.getPoint();
               
               MyPoint point = new MyPoint();
               point.setX( (int)(p.getX() / SeaView.getImageWidth() ) );
               point.setY( (int)(p.getY() / SeaView.getImageHeight() ) );
               
               boolean end = false;
               
               location = new ArrayList<MyPoint>();
               
               // look for appropriate valve to send coordinates of ship on the sea
               for(Valve v : valves)
               {    
                   end = v.execute(point);
                   if(end == true) break;
               }
               
               sendMessage(new ShipMessage(location));
               
               activeCursor = cursorHorizontally = cursorVertically = defaultCursor;
               setCursor(activeCursor);
               
           }
           if(event.getModifiers() == MouseEvent.BUTTON3_MASK)
           {
               if(activeCursor == cursorVertically)
               {
                   activeCursor = cursorHorizontally;
               }
               else
               {
                   activeCursor = cursorVertically;
               }
               setCursor(activeCursor);
           }
       }
    }
    
    /** valve to send points of fields on the sea */
    private interface Valve
    {
        public boolean execute(MyPoint point);
    }
    
    /** valve to send points of aircraft */
    private class AircraftValve implements Valve
    {
        public boolean execute(MyPoint point)
        {
            if(cursorHorizontally != hor4)
            {
                return false;
            }
            
            // ship is located vertically
            if(activeCursor == ver4)
            {
                int x = point.getX();
                int y = point.getY();
                location.add(point);
                location.add(new MyPoint(x, y+1));
                location.add(new MyPoint(x, y+2));
                location.add(new MyPoint(x, y+3));
                
                return true;
            }
            
             // hor4 - ship is located horizontally
            
                int x = point.getX();
                int y = point.getY();
                location.add(point);
                location.add(new MyPoint(x+1, y));
                location.add(new MyPoint(x+2, y));
                location.add(new MyPoint(x+3, y));
                
                return true;
        }
    }
    
    /** valve to send points of submarine */
    private class SubmarineValve implements Valve
    {
        public boolean execute(MyPoint point)
        {
            if(cursorHorizontally != hor3)
            {
                return false;
            }
            
            // ship is located vertically
            if(activeCursor == ver3)
            {
                int x = point.getX();
                int y = point.getY();
                location.add(point);
                location.add(new MyPoint(x, y+1));
                location.add(new MyPoint(x, y+2));
                
                return true;
            }
            
             // hor3 - ship is located horizontally
            
                int x = point.getX();
                int y = point.getY();
                location.add(point);
                location.add(new MyPoint(x+1, y));
                location.add(new MyPoint(x+2, y));
                
                return true;
        }
    }
    
    /** valve to send points of Frigate */
    private class FrigateValve implements Valve
    {
        public boolean execute(MyPoint point)
        {
            if(cursorHorizontally != hor2)
            {
                return false;
            }
            
            // ship is located vertically
            if(activeCursor == ver2)
            {
                int x = point.getX();
                int y = point.getY();
                location.add(point);
                location.add(new MyPoint(x, y+1));
                
                return true;
            }
            
             // hor3 - ship is located horizontally
            
                int x = point.getX();
                int y = point.getY();
                location.add(point);
                location.add(new MyPoint(x+1, y));
                
                return true;
        }
    }
    
    /** valve to send points of Frigate */
    private class BoatValve implements Valve
    {
        public boolean execute(MyPoint point)
        {
            if(cursorHorizontally != hor1)
            {
                return false;
            }
            
            location.add(point);
            
            return true;
        }
    }
}
