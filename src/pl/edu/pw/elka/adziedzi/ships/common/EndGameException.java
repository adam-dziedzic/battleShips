/**
 * 
 */
package pl.edu.pw.elka.adziedzi.ships.common;

/**
 * Exception, which is thrown at the end of game.
 * @author Adam Dziedzic grupa: K1I1
 * May 7, 2009
 * common
 */
public class EndGameException extends Exception
{
	/** It was necessary. */
	private static final long serialVersionUID = 1L;
	
	/** The player who is winner. */
	private PLAYER winner;
	
	/* Every class which extends Exception should posses 
	 * constructor without parameters and constructor 
	 * with message. 
	 */
	/** Constructor of exception. */
	public EndGameException() {}
	
	/** Constructor with communication. */
	public EndGameException(String message)
	{
		super(message);
	}
	
	/**
	 * Create exception which send information who is winner. 
	 * @param winner the player who is winner. 
	 */
	public EndGameException(PLAYER winner)
	{
		this.winner = winner;
	}
	
	/**
	 * @return Player who is winner.
	 */
	public PLAYER getWinner()
	{
		return winner;
	}
}
