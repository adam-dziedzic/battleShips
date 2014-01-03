/**
 * 
 */
package pl.edu.pw.elka.adziedzi.ships.view.messages;

import java.util.ArrayList;

import pl.edu.pw.elka.adziedzi.ships.common.MyPoint;


/**
 * Message to send location of ship.
 *
 * @author Adam Dziedzic grupa K1I1
 * May 27, 2009
 * view.messages
 */
public class ShipMessage extends MessageView
{
    
    /** for serialization: */
    private static final long serialVersionUID = 1L;
    
    private ArrayList<MyPoint> location;
    
    /**
     * Creates information with location of ship.
     * 
     * @param location location of ship on the sea
     */
    public ShipMessage(ArrayList<MyPoint> location)
    {
       this.location = location; 
    }

    /**
     * @return the location
     */
    public ArrayList<MyPoint> getLocation()
    {
        return location;
    }
    
}
