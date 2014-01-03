package pl.edu.pw.elka.adziedzi.ships.network.messages;

import pl.edu.pw.elka.adziedzi.ships.view.View;

/**
 * Disable enemy sea.
 *
 * @author Adam Dziedzic grupa K1I1
 * May 28, 2009
 * network.messagesFromServer
 */
public class DisableEnemySeaMessage implements ServerMessage
{
	
	/** for serialization: */
    private static final long serialVersionUID = 1L;
    
	/**
	 * Disable enemy sea.
	 * 
	 */
	public void execute(View view) 
	{
		view.disableEnemySea();
	}
}
