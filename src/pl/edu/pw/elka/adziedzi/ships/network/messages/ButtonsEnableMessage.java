package pl.edu.pw.elka.adziedzi.ships.network.messages;

import pl.edu.pw.elka.adziedzi.ships.view.View;

/**
 * To disable buttons.
 *
 * @author Adam Dziedzic grupa K1I1
 * May 28, 2009
 * network.messagesFromServer
 */
public class ButtonsEnableMessage implements ServerMessage
{
	
	/** for serialization: */
    private static final long serialVersionUID = 1L;
	
	/**
     * Method which enable buttons. 
     *
     * @param view View which is modified.
     */
    public void execute(View view)
    {
    	view.enableButtons();
    }
}

