/**
 * 
 */
package pl.edu.pw.elka.adziedzi.ships.view.messages;

import pl.edu.pw.elka.adziedzi.ships.common.*;

/**
 * Message which contains the point which was hit on the sea.
 *
 * @author Adam Dziedzic grupa K1I1
 * May 27, 2009
 * view.messages
 */
public class HitMessage extends MessageView
{
    /** for serialization: */
    private static final long serialVersionUID = 1L;
    
    /** point which was hit */
    private MyPoint point = new MyPoint();

    /** 
     * Constructor of message which contains point which was hit.
     * 
     * @param point Point which was hit.
     */
    public HitMessage(MyPoint point)
    {
        this.point = point;
    }
    
    /**
     * @return the point
     */
    public MyPoint getPoint()
    {
        return point;
    }

    /**
     * @param point the point to set
     */
    public void setPoint(MyPoint point)
    {
        this.point = point;
    } 
}
