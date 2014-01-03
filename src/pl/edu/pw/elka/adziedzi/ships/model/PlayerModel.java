/**
 * 
 */
package pl.edu.pw.elka.adziedzi.ships.model;

import static pl.edu.pw.elka.adziedzi.ships.common.Constants.*;

import java.util.*;

import pl.edu.pw.elka.adziedzi.ships.common.EndGameException;
import pl.edu.pw.elka.adziedzi.ships.common.FIELD_STATE;
import pl.edu.pw.elka.adziedzi.ships.common.MyPoint;

/**
 * Determine for player: fleet, sea.
 * ( Okresla atrybuty gracza: flote, morze. )
 * 
 * @author Adam Dziedzic grupa: K1I1
 * May 1, 2009
 * model
 */
class PlayerModel 
{
	/** Sea for player. */
	private SeaModel sea = new SeaModel();
	
	// finals for ships - special number of ships	
	
	/** Number of masts on the whole (every mast in fleet). */
	public static int numberOfMasts;
	
	/** Aircraft. */
	private Aircraft[] aircraft = new Aircraft[NUMBER_OF_AIRCRAFTS];
	
	/** 2 two submarines. */
	private Submarine[] submarine = new Submarine[NUMBER_OF_SUBMARINES];
	
	/** 3 three frigates. */
	private Frigate[] frigate = new Frigate[NUMBER_OF_FRIGATES];
	
	/** 4 four boats. */
	private Boat[] boat = new Boat[NUMBER_OF_BOATS];
	
	
	/** Count masts which has been already hit. */
	private int lostMasts = 0;
	
	/** Store messages: for this player and for his opponent. */
	MessageModel message = new MessageModel();   // Creates empty message.
	
	/**
	 * Constructor without parameters.
	 * Initiate field hitShips;
	 * 
	 * @param lostMasts
	 */
	public PlayerModel()
	{
		 numberOfMasts = NUMBER_OF_AIRCRAFTS*Aircraft.numberOfMasts
				  +NUMBER_OF_SUBMARINES*Submarine.numberOfMasts
				  +NUMBER_OF_FRIGATES*Frigate.numberOfMasts
				  +NUMBER_OF_BOATS*Boat.numberOfMasts;
	}
	
	// Messages:
	/**
	 * @return the message Message for this player. 
	 */
	public String getMessageForMe()
	{
		return message.getMessageForMe();
	}
	
	/**
	 * @return the message Message for Opponent. 
	 */
	public String getMessageForOpponent()
	{
		return message.getMessageForOpponent();
	}

	//SHIP DEVELOPMENT///////////////////////////////////////////////////////////////////
	/**
	 * Creates a ship with 4 masts. Points determine location of the ship on the sea.
	 * 
	 * @param points Table of points, which represents location of a ship on the sea.
	 * @return boolean Return true if operation executed, return false in another case. 
	 */
	public boolean createAircraft(ArrayList<MyPoint> points)
	{
		// 1) Check if aircraft has arleady been created.
		boolean busy = true;
	    // to find the first free reference to aicraft
		int freeIndex = 0; 
		for(int i=0; i<NUMBER_OF_AIRCRAFTS; i++)
        {
			if(aircraft[i] == null)
			{
			    busy = false;
				freeIndex = i;
				break;
			}
        }
		if(busy == true)
		{
			message.setMessageForMe("Warning! The Aircraft has arleady been created, sir. " +
				  "Create the other ships. \n");
			return false;
		}
		// 2) Check if ship is connected. 
		if(Aircraft.isConnected(points) == false)
		{
			message.setMessageForMe("The aircraft is not connected.\n");
			return false;
		}
		// 3) Check if ship is located on the sea.
        if(sea.isShipOnSea(points) == false)
        {
            message.setMessageForMe("Aircraft must be on the sea.\n");
            return false;
        }
		// 4) Check if this location is free.
		if(sea.isLocationFree(points) == false)
		{
			message.setMessageForMe("This position is busy.\n");
			return false;
		}
		Aircraft freeAircraft = new Aircraft();
        aircraft[freeIndex] = freeAircraft;
		sea.setShip(freeAircraft, points);
		return true;
	}
	
	/**
	 * Create a ship with 3 masts. Points determine location of the ship on the sea.
	 * 
	 * @param points Table of points, which represents location of a ship on the sea.
	 * @return boolean Return true if operation executed, return false in another case. 
	 */
	public boolean createSubmarine(ArrayList<MyPoint> points)
	{
	
	    // 1) Check if submarines have arleady been created.
        boolean busy = true;
        // to find the first free reference to submarine
        int freeIndex = 0; 
        for(int i=0; i<NUMBER_OF_SUBMARINES; i++)
        {
            if(submarine[i] == null)
            {
                busy = false;
                freeIndex = i;
                break;
            }
        }
		if(busy == true)
		{
			message.setMessageForMe("Warning! All Submarines has arleady been created, sir. " +
				  "Create the other ships. \n");
			return false;
		}
		// 2) Check if ship is connected. 
		if(Submarine.isConnected(points) == false)
		{
			message.setMessageForMe("The submarine is not connected.\n");
			return false;
		}
        // 3) Check if ship is located on the sea.
        if(sea.isShipOnSea(points) == false)
        {
            message.setMessageForMe("Submarine must be on the sea.\n");
            return false;
        }
		// 4) Check if this location is free.
		if(sea.isLocationFree(points) == false)
		{
			message.setMessageForMe("This position is busy.\n");
			return false;
		}
		Submarine freeSubmarine = new Submarine();
        submarine[freeIndex] = freeSubmarine;
		sea.setShip(freeSubmarine, points);
		return true;
	}
	
	/**
	 * Create a ship with 2 masts. Points determine location of the ship on the sea.
	 * 
	 * @param points Table of points, which represents location of a ship on the sea.
	 * @return boolean Return true if operation executed, return false in another case. 
	 */
	public boolean createFrigate(ArrayList<MyPoint> points)
	{
		// 1) Check if frigates have arleady been created.
        boolean busy = true;
        // to find the first free reference to frigate
        int freeIndex = 0; 
        for(int i=0; i<NUMBER_OF_FRIGATES; i++)
        {
            if(frigate[i] == null)
            {
                busy = false;
                freeIndex = i;
                break;
            }
        }
		if(busy == true)
		{
			message.setMessageForMe("Warning! All Frigates has arleady been created, sir. " +
				  "Create the other ships. \n");
			return false;
		}
		// 2) Check if ship is connected. 
		if(Frigate.isConnected(points) == false)
		{
			message.setMessageForMe("The frigate is not connected.\n");
			return false;
		}
		// 3) Check if ship is located on the sea.
        if(sea.isShipOnSea(points) == false)
        {
            message.setMessageForMe("Frigate must be on the sea.\n");
            return false;
        }
		// 4) Check if this location is free.
		if(sea.isLocationFree(points) == false)
		{
			message.setMessageForMe("This position is busy.\n");
			return false;
		}
		Frigate freeFrigate = new Frigate();
        frigate[freeIndex] = freeFrigate;
		sea.setShip(freeFrigate, points);
		return true;
	}
	
	/**
	 * Create a ship with 1 mast. Points determine location of the ship on the sea.
	 * 
	 * @param points Table of points, which represents location of a ship on the sea.
	 * @return boolean Return true if operation executed, return false in another case. 
	 */
	public boolean createBoat(ArrayList<MyPoint> points)
	{
		// 1) Check if all boats have arleady been created.
        boolean busy = true;
        // to find the first free reference to boat
        int freeIndex = 0; 
        for(int i=0; i<NUMBER_OF_BOATS; i++)
        {
            if(boat[i] == null)
            {
                busy = false;
                freeIndex = i;
                break;
            }
        }
		if(busy == true)
		{
			message.setMessageForMe("Warning! All Boats has arleady been created, sir. " +
				  "Create the other ships.\n ");
			return false;
		}
		// 2) Check if ship is connected. 
		if(Boat.isConnected(points) == false)
		{
			message.setMessageForMe("The boat is not connected.\n");
			return false;
		}
		// 3) Check if ship is located on the sea.
        if(sea.isShipOnSea(points) == false)
        {
            message.setMessageForMe("Boat must be on the sea.\n");
            return false;
        }
		// 4) Check if this location is free.
		if(sea.isLocationFree(points) == false)
		{
			message.setMessageForMe("This position is busy.\n");
			return false;
		}
		
        Boat freeBoat = new Boat();
        boat[freeIndex] = freeBoat;
		sea.setShip(freeBoat, points);
		return true;
	}
	
    /**
     * Check if all ships have been created.
     *
     *
     * @return true if all ships have been created and false in the other way
     */
	public boolean isFleetSet()
	{
		// check the last references to ships
		// ships are created from first to last index
		if(aircraft[NUMBER_OF_AIRCRAFTS-1] != null && 
		   submarine[NUMBER_OF_SUBMARINES-1] != null &&
		   frigate[NUMBER_OF_FRIGATES-1] != null &&
		   boat[NUMBER_OF_BOATS-1] != null)
		{
			return true;
		}
		return false;
	}
	
	//The main part of GAME. ////////////////////////////////////////////////////////////
	/**
	 * @return Return true if operation was executed (player hit 
	 * mast), return false in another case (field has already been hit).
	 */ 
	public boolean opponentHit(MyPoint point)
	{ 
		// In addition this function should set massages for players and add hitShips.
		HIT_RESULT result =  sea.opponentHit(point, message);
		/* rule: hit by hit (zasada - strzał za strzał) 
		if(result == HIT_RESULT.WRONG)
		{
			return false;
		}
		else if(result == HIT_RESULT.MISS)
			 {
			     return true;
			 }
			 else // if(result == HIT_RESULT.HIT)
			 {
				 ++lostMasts;
				 return true;
			 }
		*/
		// rule: if hit then continue
		if(result == HIT_RESULT.MISS)
		{
			return true;
		}
		else 
			if(result == HIT_RESULT.HIT)
			{
				++lostMasts;
			}
		return false;
	}
	
	/**
	 * Check if player has won.
	 * 
	 * @return boolean Return true if player has won and false in another case.
	 */
	public boolean isLost()
	{
		if(lostMasts == numberOfMasts)
		{
			return true;
		}
		return false;
	}
	
	/**
	 * Add the number of masts which has been hit.
	 * 
	 * @throws EndGameException when then is end of game
	 */
	public void addHitShips() throws EndGameException
	{
		++lostMasts;
		if(lostMasts==20)
			throw new EndGameException();
	}
	
	/**
	 * @return hitShips The number of mast which has been hit. 
	 */
	public int getHitShips()
	{
		return lostMasts;
	}
	
	/**
	 * @return This player own sea.
	 */
	public FIELD_STATE[][] getSeaForMe()
	{
		return sea.getSeaForMe();
	}
	
	/**
	 * @return This player sea for Opponent.
	 */
	public FIELD_STATE[][] getSeaForOpponent()
	{
		return sea.getSeaForOpponent();
	}
	
}
