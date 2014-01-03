/**
 * 
 */
package pl.edu.pw.elka.adziedzi.ships.view;

import java.awt.*;
import javax.swing.*;

/**
 * TextView for messages to player.
 *
 * @author Adam Dziedzic grupa K1I1
 * May 26, 2009
 * view
 */
class TextView extends PanelView
{
    
    /** for serialization: */
    private static final long serialVersionUID = 1L;
    
    /** area for text */
    private JTextArea textArea;
    
    /**
     * Creates area for messages and determines its position and size.
     * 
     * @param x position x of textView
     * @param y position y of textView
     * @param width
     * @param height
     */
    public TextView(int x, int y, int width, int height)
    {
        super(x, y, width, height);
        
        setLayout(new BorderLayout());
        textArea = new JTextArea("Ship Deployment.\n");
        // text area is not "editable"
        textArea.setEditable(false);
        // enable line wrap ( włącza zawijanie wierszy w obszarze tekstowym)
        textArea.setLineWrap(true);
        // wrap line with all world (zawijanie wierszy z całymi wyrazami)
        textArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane);
    }
    
    /**
     * Append new message at the end of text in textArea.
     *
     * @param newMessage This message is appended at the end of text area.
     */
    public void append(String newMessage)
    {
        textArea.append(newMessage);
        this.validate();
    }
   
}
