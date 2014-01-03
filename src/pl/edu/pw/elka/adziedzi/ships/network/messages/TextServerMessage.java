/**
 * 
 */
package pl.edu.pw.elka.adziedzi.ships.network.messages;

import pl.edu.pw.elka.adziedzi.ships.view.View;

/**
 * Class which contains text message for client.
 * It is send by ServerOut and network to client.
 *
 * @author Adam Dziedzic grupa K1I1
 * May 28, 2009
 * network.messagesFromServer
 */
public class TextServerMessage implements ServerMessage
{
    /** for serialization: */
    private static final long serialVersionUID = 1L;
    
    /** text which is sent from controller to client by ServerOut and network. */
    private String textMessage;
    
    public TextServerMessage(String textMessage)
    {
        super();
        this.textMessage = textMessage;
    }
 
    /**
     * Method which executes action for view object. 
     *
     * @param view View which is modified.
     */
    public void execute(View view)
    {
       view.addMessage(textMessage);
    }

}
