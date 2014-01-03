/**
 * 
 */
package pl.edu.pw.elka.adziedzi.ships.model;

import java.util.*;

import pl.edu.pw.elka.adziedzi.ships.common.FIELD_STATE;
import pl.edu.pw.elka.adziedzi.ships.common.MyPoint;
import static pl.edu.pw.elka.adziedzi.ships.common.Constants.*;

/**
 * SeaModel represents sea, which consists of fields. 
 * (Klasa reprezentujaca morze.)
 * 
 * @author Adam Dziedzic grupa: K1I1
 * May 16, 2009
 * model
 */
class SeaModel
{
    
    /** Represents sea and the state of fields on the sea. */
	private FieldModel[][] sea = null; 
	/* Poniewaz natura ArrayList<> jest jednowymiarowa, wybralem  
	 * jednak tablice dwuwymiarowa, bardziej oddaje charakter Sea.
	 * Ponadto zawsze jest ta sama liczba pól na morzu 10x10.
	 */

	/**
	 * Initiates fields on the sea as UNUSED.
	 * ( Inicjalizuje pola na morzu jako puste (UNUSED). )
     * 
	 */
	public SeaModel()
	{
		sea = new FieldModel[ROWS][COLS];
		
		for(int i=0; i<ROWS; i++)
			for(int j=0; j<COLS; j++)
			{
				sea[i][j] = new FieldModel();
			}
	}

	/**
	 * Opponent hit to the field on the sea.
	 * ( Oddanie strzalu przez przeciwnika w dany punkt na morzu (Sea).)
	 * 
	 * @param point Point determines filed which is hit.
	 * @param message Message for players.
	 */
	public HIT_RESULT opponentHit(MyPoint point, MessageModel message)
	{
		return sea[point.getX()][point.getY()].opponentHit(message);
	}
	
    /** 
     * Check if ship is on the sea (not out of bounds) 
     * 
     * @param points Points to check.
     */
    public boolean isShipOnSea(final ArrayList<MyPoint> points)
    {
        for(MyPoint point : points)
        {
            if(point.getX() < 0 || point.getX() > 9 
               || point.getY() < 0 || point.getY() > 9)
            return false;
        }
        return true;
    }
    
	/**
	 * Check if fields on the sea are free and a ship can be located in this place.
	 * 
	 * @param points The points which represent location of ship. 
	 * @return true if ship can be located and return false in another case (there are busy fields).
	 */
	public boolean isLocationFree(final ArrayList<MyPoint> points)
	{
		// ships should not touch each other	
	
        // takes neighbour
        HashSet<FieldModel> neighbours = getNeighbours(points);
        
        for(FieldModel field : neighbours)
        {
            // checks if every neighbour is free
            if(field.getState() == FIELD_STATE.SHIP)
                return false;
        }	
		return true;
	}
	
	/**
	 * Finds neighbours of point. 
	 * 
	 * @param point Point which neighbours have to be selected.
	 * @return HashSet<Point> Unique collection of points.
	 */
	private HashSet<FieldModel> getNeighbours(final ArrayList<MyPoint> points)
	{
		HashSet<FieldModel> result = new HashSet<FieldModel>();
		
        for(MyPoint point : points)
        {
            int x = point.getX();
            int y = point.getY();
            
            // point has to include in the sea
            assert x>=0 && x<COLS;
            assert y>=0 && y<ROWS;
            
            // go through square 3x3 and point (x,y) inside in it
            // for (x,y) there is 9 (nine) points 
            // 1 2 3
            // 4 5 6
            // 7 8 9
            // 5 - this point
            int x1 = x-1;
            int y1 = y-1;
            int x9 = x+1;
            int y9 = y+1;
            int xi, yi; //indexes
            for(xi = x1; xi <= x9; ++xi)
                for(yi = y1; yi <= y9; ++yi)
                {
                    // point not out of bound
                    if( (xi>=0 && xi<COLS) && (yi>=0 && yi<ROWS) )
                    {
                        result.add(sea[xi][yi]);
                    }
                }
        }

       return result;
	}
	
    /**
     * Set ships on the sea.
     *
     *
     * @param ship Ship which is now located on the sea.
     * @param points Points of location.
     */
	public void setShip(ShipModel ship, ArrayList<MyPoint> points)
	{	
		// get masts to set on the sea
		MastModel[] masts = ship.getMasts();
		
		int numberOfPoints = points.size();
		
		// the number of masts has to be equal to number of points
		assert masts.length == numberOfPoints;
		
		// set masts on the sea with respects to points
		for(int i=0; i < numberOfPoints; i++)
		{
			sea[points.get(i).getX()][points.get(i).getY()] = masts[i];
		}
		
	}
	
	/**
     * 
	 * @return Player own sea.
	 */
	public FIELD_STATE[][] getSeaForMe()
	{
	    // indexes: i, j are necessary 
		FIELD_STATE[][] result = new FIELD_STATE[ROWS][COLS];
		for(int i=0; i<ROWS; i++)
			for(int j=0; j<COLS; j++)
			{
				result[i][j] = sea[i][j].getState();
			}
		return result;
	}
	
	/**
     * 
	 * @return Player sea for Opponent.
	 */
	public FIELD_STATE[][] getSeaForOpponent()
	{
		// indexes: i, j are necessary 
		FIELD_STATE[][] result = new FIELD_STATE[ROWS][COLS];
		for(int i=0; i<ROWS; i++)
			for(int j=0; j<COLS; j++)
			{
				result[i][j] = sea[i][j].getStateForOpponent();
			}
		return result;
	}
	
}
