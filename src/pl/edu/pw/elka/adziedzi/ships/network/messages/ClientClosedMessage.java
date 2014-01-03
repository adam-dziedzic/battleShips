/**
 * 
 */
package pl.edu.pw.elka.adziedzi.ships.network.messages;

import pl.edu.pw.elka.adziedzi.ships.view.View;

/**
 * To inform thred ClientIn that main frame was closed.
 *
 * @author Adam Dziedzic grupa K1I1
 * May 28, 2009
 * network.messagesFromServer
 */
public class ClientClosedMessage implements ServerMessage
{
    
    /** for serialization: */
    private static final long serialVersionUID = 1L;
    
    public void execute(View view)
    {}
}
