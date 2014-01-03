/**
 * 
 */
package pl.edu.pw.elka.adziedzi.ships.model;

import static pl.edu.pw.elka.adziedzi.ships.common.Constants.*;

/**
 * Statek typu Frigate - fregata. 
 * 
 * @author Adam Dziedzic grupa: K1I1 
 * May 2, 2009 
 * model
 */
class Frigate extends ShipModel 
{
	public static final int numberOfMasts = FRIGATE_NUMBER_OF_MASTS; // przyjmujemy okreslona liczbe masztow
	public static final String name = "Frigate";    // fregata
	
	/**
	 * @param numberOfMasts
	 * @param name
	 */
	public Frigate() 
	{
		super(numberOfMasts, name);
	}

}
