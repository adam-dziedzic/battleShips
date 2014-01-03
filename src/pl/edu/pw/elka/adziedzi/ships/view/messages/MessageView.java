/**
 * 
 */
package pl.edu.pw.elka.adziedzi.ships.view.messages;

import java.io.*;

import pl.edu.pw.elka.adziedzi.ships.common.PLAYER;
import pl.edu.pw.elka.adziedzi.ships.view.*;


/**
 * Contains message which is send from View to Controller.
 *
 * @author Adam Dziedzic grupa K1I1
 * May 25, 2009
 * view
 */
public abstract class MessageView implements Serializable
{
    /** for serialization: */
    private static final long serialVersionUID = 1L;
    public final PLAYER player = View.getPlayer();
}
