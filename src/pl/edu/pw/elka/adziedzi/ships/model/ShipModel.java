/**
 * 
 */
package pl.edu.pw.elka.adziedzi.ships.model;

import java.util.*;

import pl.edu.pw.elka.adziedzi.ships.common.FIELD_STATE;
import pl.edu.pw.elka.adziedzi.ships.common.MyPoint;

/**
 * Represents ship with masts and name.
 * 
 * @author Adam Dziedzic grupa: K1I1
 * May 1, 2009
 * model
 */
abstract class ShipModel 
{
	/** Store the masts of ship. */
	private MastModel[] mast;
	
	/** The name of ship. */
	private String name = "ShipModel";
	
	/**
	 * Prepare place for building ship.
	 * (Przygotowuje miejsce (inicjalizuje tablice) na budowe
	 * statku o okreslonej liczbie masztow.)
	 * 
	 * @param numberOfMasts Number of masts.
	 * @param name Name of ship.
	 */
	public ShipModel(final int numberOfMasts, final String name)
	{
		mast = new MastModel[numberOfMasts];
		for(int i=0; i < mast.length; i++)
			mast[i] = new MastModel(this);
		this.name = name;
	}
	
	/**
	 * @return A name of ship.
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Check if every mast of ship has been hit. 
	 * (Sprawdza czy zostaly trafione wszystkie maszty statku.)
	 * @return Return true if every mast of ship has been hit and false in another case. 
	 */
	public boolean isEveryMastHit()
	{
		// review every mast of ship and check if mast has been hit
		for(MastModel m : mast)
			if( m.getState() == FIELD_STATE.SHIP) return false;   //jeszcze sa nietrafione wszystkie maszty
		
		return true;
	}

	
	/**
	 * Sink the ship.
	 */
	public void sink()
	{
		// ship is destroyed 
		for(MastModel m : mast)
			m.sink();
		
	}
	/**
	 * Check if ship is connected.
	 * @param points The points which represents ship.
	 * @return true if ship is connected and false in another case
	 */
	public static boolean isConnected(final ArrayList<MyPoint> points)
	{
		// ships contain 1, 2, 3 or 4 masts
		assert points.size() > 0 && points.size() < 5;
		// if ship contains 1 mast
		if(points.size() == 1)
			return true;
		// ship contains more than 1 mast: take coordinates (x, y) of the first ship
		int x = points.get(0).getX();
		int y = points.get(0).getY();
		// x the same for points (ship is located vertically) (jezeli statek ulokowany wzgledem x)
		if( x == points.get(1).getX())
		{   
			// the same x, sort points respect to y
			Collections.sort(points, MyPoint.getYComparator());
			for(MyPoint p : points)
			{
				if( p.getX() != x || p.getY() != y)
				 return false;
				// the next point should have the same x coordinate and the next
				// y coordinate
				++y;
			}
		}
		// should be the same y for points (ship located horizontally)
		else
		{
			// the same y, sort poinst respect to x
			Collections.sort(points, MyPoint.getXComparator());
			for(MyPoint p : points)
			{
				if( p.getX() != x || p.getY() != y)
				 return false;
				// the next point should have the same y coordinate and the next
				// x coordinate
				++x;
			}
		}
		return true;
	}
	
	
	/**
	 * Method is used to set ships on the sea.
	 * ( Metoda wykorzystywana w SeaModel do ustawiania statku na morzu. )
	 * 
	 * @return Return masts' table of ship.
	 * (Zwraca tablice masztow nalezacych do danego statku.)
	 */
	public MastModel[] getMasts()
	{
		return mast;
	}
	
}
