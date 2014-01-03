/**
 * 
 */
package pl.edu.pw.elka.adziedzi.ships.model;

import static pl.edu.pw.elka.adziedzi.ships.common.Constants.*;

/**
 * Statek typu Boat - lajba.
 * 
 * @author Adam Dziedzic grupa: K1I1 
 * May 2, 2009 
 * model
 */
class Boat extends ShipModel 
{
	public static final int numberOfMasts = BOAT_NUMBER_OF_MASTS;	// przyjmujemy okreslona liczbe masztow
	public static final String name = "Boat";	// łajba
	
	/**
	 * @param numberOfMasts
	 * @param name
	 */
	public Boat() 
	{
		super(numberOfMasts, name);
	}

}
