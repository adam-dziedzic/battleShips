/**
 * 
 */
package pl.edu.pw.elka.adziedzi.ships.view;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.BlockingQueue;

import pl.edu.pw.elka.adziedzi.ships.common.MyPoint;
import pl.edu.pw.elka.adziedzi.ships.view.messages.HitMessage;
import pl.edu.pw.elka.adziedzi.ships.view.messages.MessageView;

/**
 * Special sea - enemy sea.
 *
 * @author Adam Dziedzic grupa K1I1
 * May 26, 2009
 * view
 */
class EnemySeaView extends SeaView
{
    /** for serialization: */
    private static final long serialVersionUID = 1L;
    
    /** Mouse listener. */
    private MouseHandler mouseListener;
    
    /**
     * Creates enemy sea and determines its position and size.
     * 
     * @param x position x of sea 
     * @param y position y of sea
     * @param width
     * @param height
     */
    public EnemySeaView(int x, int y, int width, int height, BlockingQueue<MessageView> queue)
    {
        super(x, y, width, height, queue); 
        mouseListener = new MouseHandler();
        addMouseListener(mouseListener);
    }
    
    /**
     * Executes hit.
     *
     * @param point which was hit
     */
    private void hit(MyPoint point)
    {
        sendMessage(new HitMessage(point));
    }
    
    /**
     * Disable Sea.
     *
     */
    public void disableSea()
    {
    	removeMouseListener(mouseListener);
    }
    
    /**
     * Enable Sea.
     *
     */
    public void enableSea()
    {
    	addMouseListener(mouseListener);
    }
    
    /**
     * Serves mouse clicks in points on the opponent sea.
     * 
     */
    private class MouseHandler extends MouseAdapter
    {

        public void mouseClicked(MouseEvent event)
        {
            // take point which was clicked
            Point p = event.getPoint();
            
            MyPoint point = new MyPoint();
            point.setX( (int)(p.getX() / SeaView.getImageWidth() ) );
            point.setY( (int)(p.getY() / SeaView.getImageHeight() ) );
            
            // hit on the field on the opponent sea
            hit(point);
        }
    }
    
}
