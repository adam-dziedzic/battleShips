package pl.edu.pw.elka.adziedzi.ships.network.messages;

import pl.edu.pw.elka.adziedzi.ships.view.View;

/**
 * Enable enemy sea.
 *
 * @author Adam Dziedzic grupa K1I1
 * May 28, 2009
 * network.messagesFromServer
 */
public class EnableEnemySeaMessage implements ServerMessage
{
	
	/** for serialization: */
    private static final long serialVersionUID = 1L;
    
	/**
	 * Enable enemy sea.
	 * 
	 */
	public void execute(View view) 
	{
		view.enableEnemySea();
	}
}


