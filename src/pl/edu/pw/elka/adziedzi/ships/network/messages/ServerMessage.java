/**
 * 
 */
package pl.edu.pw.elka.adziedzi.ships.network.messages;

import java.io.Serializable;

import pl.edu.pw.elka.adziedzi.ships.view.View;


/**
 * Interface for messages which are send from controller to client
 * by serverOut and network.
 *
 * @author Adam Dziedzic grupa K1I1
 * May 28, 2009
 * network.messagesFromServer
 */
public interface ServerMessage extends Serializable
{
    /**
     * Method which executes action for view object. 
     *
     * @param view View which is modified.
     */
    public void execute(View view);
}
