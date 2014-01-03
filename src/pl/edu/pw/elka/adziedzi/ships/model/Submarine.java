/**
 * 
 */
package pl.edu.pw.elka.adziedzi.ships.model;

import static pl.edu.pw.elka.adziedzi.ships.common.Constants.*;

/**
 * Statek typu Submarine - lodz podwodna. 
 * 
 * @author Adam Dziedzic grupa: K1I1
 * May 16, 2009
 * model
 */
class Submarine extends ShipModel 
{
	public static final int numberOfMasts = SUBMARINE_NUMBER_OF_MASTS;		// przyjmujemy okreslona liczbe masztow
	public static final String name = "Submarine";  // lodz podwodna
	
	/**
	 * @param numberOfMasts 
	 * @param name
	 */
	public Submarine() 
	{
		super(numberOfMasts, name);
	}
	
}
