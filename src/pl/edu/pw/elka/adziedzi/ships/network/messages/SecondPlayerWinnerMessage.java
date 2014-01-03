package pl.edu.pw.elka.adziedzi.ships.network.messages;

import pl.edu.pw.elka.adziedzi.ships.view.View;

/**
 * To inform player about winner.
 *
 * @author Adam Dziedzic grupa K1I1
 * May 28, 2009
 * network.messagesFromServer
 */
public class SecondPlayerWinnerMessage implements ServerMessage
{
	/** for serialization: */
    private static final long serialVersionUID = 1L;
	
    /**
     * Method which inform second player about victory.
     *
     * @param view View which is modified.
     */
	public void execute(View view) 
	{
		view.InformFrame("You are Winner, sir! Congratulations!", "Victory");
	}
}
