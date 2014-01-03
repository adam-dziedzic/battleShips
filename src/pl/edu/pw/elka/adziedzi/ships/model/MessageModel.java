/**
 * 
 */
package pl.edu.pw.elka.adziedzi.ships.model;

/**
 * Store messages for players.
 * Player has got his own object message, which contains message 
 * for player and his opponent.
 * 
 * @author Adam Dziedzic grupa: K1I1
 * May 20, 2009
 * model
 */
class MessageModel
{
	// At the beginning messages are empty. 

	/** Message for this player - "for me". */
	private String messageForMe = "";   
	
	/** Message for opponent. */
    private String messageForOpponent = "";  
	
    
	/**
	 * @return the message for this player (from this object of message)
	 */
	public String getMessageForMe()
	{
		// A message which has been used have to be cleaned.
		String result = messageForMe;
		// clean message
		messageForMe = "";
		return result;
	}
	
	/**
	 * @return the message for Opponent (from this object of message)
	 */
	public String getMessageForOpponent()
	{
		// A message which has been used have to be cleaned.
		String result = messageForOpponent;
		// clean message
		messageForOpponent = "";
		return result;
	}
    	
	/**
	 * @param messageForMe the messageForMe to set (in this object of message)
	 */
	public void setMessageForMe(String messageForMe)
	{
		this.messageForMe = messageForMe;
	}

	/**
	 * @param messageForOpponent the messageForOpponent to set (in this object of message)
	 */
	public void setMessageForOpponent(String messageForOpponent)
	{
		this.messageForOpponent = messageForOpponent;
	}
	
}
