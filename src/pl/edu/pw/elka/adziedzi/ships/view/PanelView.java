/**
 * 
 */
package pl.edu.pw.elka.adziedzi.ships.view;

import javax.swing.*;

/**
 * Common class for panels in the frameView.
 *
 * @author Adam Dziedzic grupa K1I1
 * May 26, 2009
 * view
 */
abstract class PanelView extends JPanel
{
    
    /** for serialization: */
    private static final long serialVersionUID = 1L;
    
    /**
     * Determines position (x,y) of panel in the frameView and its dimension (width and height).
     * 
     * @param x position x of panel
     * @param y position y of panel
     * @param width width of panel
     * @param height height of panel
     */
    public PanelView(int x, int y, int width, int height)
    {
        
        super.setBounds(x, y, width, height);
    }
    
}
