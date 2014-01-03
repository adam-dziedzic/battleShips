/**
 * 
 */
package pl.edu.pw.elka.adziedzi.ships.model;

import pl.edu.pw.elka.adziedzi.ships.common.FIELD_STATE;

/**
 * To describe a special kind of field - Mast.
 * ( Opis wyjątkowego rodzaju pola - masztu.)
 * 
 * @author Adam Dziedzic grupa: K1I1
 * May 6, 2009
 * model
 */
class MastModel extends FieldModel 
{

	// Maszt jest specjalnym rodzajem pola (FieldModel) zawierajacym dodatkowo 
	// odnosnik na obiekt statku, do ktorego nalezy.
	/** Reference on ship, which contains this mast. */
	private ShipModel ship;
	
	/**
	 * Creates a new instatnce of Mast.
	 * (Tworzy nowy maszt i jednoczesnie okresla, do ktorego statku dany maszt nalezy.
	 * Kazdy maszt nalezy do jednego ze statkow.)
	 * 
	 * @param ship Ship which contains this mast. 
	 */
	public MastModel(ShipModel ship)
	{ 
		super(ShipState.instance());
		this.ship = ship;
	}

	/**
	 * 
	 * @return Return a reference to Ship, which contains this Mast.
	 */
	public ShipModel getShip() 
	{
		return ship;
	}

	/**
	 * 
	 * @param ship Set a ship, which contains this mast.
	 */
	public void setShip(ShipModel ship) 
	{
		this.ship = ship;
	}
	
	/**
	 * Hit on mast. (Uderzenie w maszt.)
	 * 
	 * @param message Messages for player and for his opponent.
	 * @return true if state has been changed - it means that hit has been correct.
	 */
	public HIT_RESULT opponentHit(MessageModel message)
	{
		HIT_RESULT result = super.opponentHit(message);
        
        // if it was this hit which destroyed ship and every mast has been hit
		// if once again this field will be hit it return hit or destroyed
		if( result == HIT_RESULT.HIT && ship.isEveryMastHit()) 
		{
			ship.sink();
			message.setMessageForOpponent("Well done. We destroyed the enemy " +
					  ship.getName() + "!\n");
			message.setMessageForMe("Unfortunately... Our " + ship.getName() +
             " has been destroyed.\n");
		}
		
		return result;
	}
	
	/**
	 * Sink mast - a part of ship. (Zatapia maszt).
	 * It is necessary for method which sink all ship.
	 * 
	 */
	public void sink()
	{
		// there must be changed from state hit to destroyed
		assert getState() == FIELD_STATE.HIT;
		setState(DestroyedState.instance());
	}
	
	/////////////////////////////////////////////////////////////////////////////////////
	// Inner implementation of state.
	// I used the design pattern: State to describe a state of mast.
	
	/** The first state of mast - ShipState. */
	private static class ShipState implements FieldState
	{
		/** It must be only one object of class HitState. */
		private static ShipState shipState = null;
		  
		/**
		 * To secure only one instance of class ShipState.
		 * This method must be private.
		 */ 
		private ShipState()
		{}
		   
		/** 
		 * @return Instance (which is only one) of class ShipState. 
		 */
		public static ShipState instance()
		{
		   if(shipState == null)
		   {
			   	shipState = new ShipState();
		   }
		   return shipState;
		}
		   
		
		/**
		 * Hit on mast of ship.
		 * 
		 * @param message Message for players.
		 * @return The next state for this mast after hit: HitState
		 * @see pl.edu.pw.elka.adziedzi.ships.model.FieldModel.FieldState#opponentHit(MessageModel)
	     */
		public FieldState opponentHit(MessageModel message)
		{
            message.setMessageForOpponent("Bingo! We have hit, sir!\n");
            message.setMessageForMe("Our ship has been hit, sir.\n");
			
            return HitState.instance();
		}
		   
		/**
		 * @return The present state of field.
		 */
		public FIELD_STATE getState()
		{
			return FIELD_STATE.SHIP;
		}
		
		/**
		 * @return The present state of field for Opponent.
		 */
		public FIELD_STATE getStateForOpponent()
		{
			return FIELD_STATE.UNUSED;
		}
		
	}	
	
	/** The second state of mast - HitState. */
	private static class HitState implements FieldState
	{
		/** It must be only one object of class HitState. */
		private static HitState hitState = null;
		  
		/** 
		 * To secure only one instance of class HitState.
		 * This method must be private.
		 * 
		 */
		private HitState()
		{}
		   
		/**
		 * @return Instance (which is only one) of class HitState. 
		 */
		public static HitState instance()
		{
		   if(hitState == null)
		   {
			   	hitState = new HitState();
		   }
		   return hitState;
		}
	
		/** 
		 * Hit on hit mast.
		 * 
		 * @param message Message for players.
		 * @return The hit has not been correct so state is not changed: HitState
		 * @see pl.edu.pw.elka.adziedzi.ships.model.FieldModel.FieldState#opponentHit(MessageModel)
		 */
		
		public FieldState opponentHit(MessageModel message)
		{
			// set message for player who hits
			message.setMessageForOpponent("This position has arleady been hit, sir - try again.\n");
			
			// do not change the player, because the player 
			// who hits have to repeat the hit
			return HitState.instance();
		}
		   
		/** 
		 * @return The present state of field. 
		 */
		public FIELD_STATE getState()
		{
			return FIELD_STATE.HIT;
		}
		
		/** 
		 * @return The present state of field for Opponent. 
		 */
		public FIELD_STATE getStateForOpponent()
		{
			return FIELD_STATE.HIT;
		}
	}	
	
	/** The third state of mast - DestroyedState. */
	private static class DestroyedState implements FieldState
	{
		/** It must be only one object of class DestroyedState. */
		private static DestroyedState destroyedState = null;
		  
		/**
		 * To secure only one instance of class DestroyedState.
		 * This method must be private.
		 * 
		 */ 
		private DestroyedState()
		{}
		   
		/** 
		 * @return Instance (which is only one) of class ShipState. 
		 */
		public static DestroyedState instance()
		{
		   if(destroyedState == null)
		   {
			   	destroyedState = new DestroyedState();
		   }
		   return destroyedState;
		}
	
		/** 
		 * Hit on destroyed mast.
		 * 
		 * @param message Message for players.
		 * @return The hit has not been correct so state is not changed: DestroyedState
		 * @see pl.edu.pw.elka.adziedzi.ships.model.FieldModel.FieldState#opponentHit(MessageModel)
	     */
		public FieldState opponentHit(MessageModel message)
		{
			// set message for plater who hits
			message.setMessageForOpponent("This position has arleady been hit, sir - try again.\n");
			
			// do not change the player, because the player 
			// who hits have to repeat the hit
			return DestroyedState.instance();
		}
		   
		/**
		 * @return The present state of field.
		 */
		public FIELD_STATE getState()
		{
			return FIELD_STATE.DESTROYED;
		}
		
		/**
		 * @return The present state of field for Opponent.
		 */
		public FIELD_STATE getStateForOpponent()
		{
			return FIELD_STATE.DESTROYED;
		}
		
	}	

}
