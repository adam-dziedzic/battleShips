/**
 * 
 */
package pl.edu.pw.elka.adziedzi.ships.model;

import static pl.edu.pw.elka.adziedzi.ships.common.Constants.*;

/**
 * Aircraft.
 * (Statek typu Aircraft - lotniskowiec.)
 * 
 * @author Adam Dziedzic grupa: K1I1
 * May 3, 2009
 * model
 */
class Aircraft extends ShipModel 
{

	public static final int numberOfMasts = AIRCRAFT_NUMBER_Of_MASTS;		// przyjmujemy okreslona liczbe masztow
	public static final String name = "Aircraft";   // lotniskowiec
	
	/**
	 * Konstruktor bezargumentowy - domyslnie ustawia numberOfMasts masztow i nazwe statku,
	 * a takze buduje maszty.
	 */
	public Aircraft() 
	{
		super(numberOfMasts, name);
	}

}
