/**
 * 
 */
package pl.edu.pw.elka.adziedzi.ships.model;

import pl.edu.pw.elka.adziedzi.ships.common.FIELD_STATE;

/**
 * Describe one field on the sea.
 * (Opis pojedynczego pola na morzu (Sea).)
 * 
 * @author Adam Dziedzic grupa: K1I1 
 * May 2, 2009 
 * model
 */
class FieldModel
{
	
	/** Field has an appropriate state: { UNUSED, MISS; (SHIP, HIT, DAMAGED: for Mast) }*/
	private FieldState state;

	/**
	 * Default constructor of field (The first state of field is UnusedState).
	 * (Domyslny konstruktor pola (Ustawia stan na UnusedState).)
	 */
	public FieldModel()
	{
		this.state = UnusedState.instance();
	}
    
    /**
     * Creates new field with beginning state.
     * 
     * @param state the beginning state of filed
     */
    public FieldModel(final FieldState state)
    {
        this.state = state;
    }

	/**
	 * Return the present state of field.
	 * 
	 * @return The present state of field. (Zwraca aktualny stan pola.)
	 */
	public FIELD_STATE getState()
	{
		return state.getState();
	}

	/**
     * @param state the state to set
     */
    protected void setState(FieldState state)
    {
        this.state = state;
    }

    /**
	 * Return the present state of field for Opponent.
	 * 
	 * @return The state of field for Opponent.
	 */
	public FIELD_STATE getStateForOpponent()
	{
		return state.getStateForOpponent();
	}
	
	/**
	 * Hit on field. (Uderzenie na pole.)
	 * 
	 * @param message Messages for player and for his opponent.
	 * @return not WRONG if state has been changed - it means that hit has been correct
	 */
	public HIT_RESULT opponentHit(MessageModel message)
	{
        FieldState oldState = state;
        
		state = state.opponentHit(message);
		
		if(oldState.getState() == FIELD_STATE.UNUSED)
        {
		    return HIT_RESULT.MISS;
        }
		else if(oldState.getState() == FIELD_STATE.SHIP)
			 {
			 	return HIT_RESULT.HIT;
			 }
			else // oldState : MISS, HIT, DESTROYED
			{
				return HIT_RESULT.WRONG;
			}
	}

	// ////////////////////////////////////////////////////////////////////////////////////
	// Inner implementation of state.
	// I used the design pattern: State to describe a state of field.

	/**
	 * FieldState describes the internal state of Field.
	 */
	protected interface FieldState
	{
		/**
		 * Hit on field.
		 * 
		 * @param message Message for players.
		 * @return The next state for field after hit. 
		 */
		public FieldState opponentHit(MessageModel message);
		
		/**
		 * Return the present state of field.
		 * 
		 * @return The present state of field. 
		 * (Zwraca aktualny stan pola.)
		 */
		public FIELD_STATE getState();
		
		/**
		 * Return the present state of field for Opponent.
		 * 
		 * @return The state of field for Opponent.
		 */
		public FIELD_STATE getStateForOpponent();
	}

	// Every class of state should only implements an apropriate methods.
	// There is only one object of every class, which describes state of field:
	// Singleton.

	/**
	 * The first state of field - UnusedState. 
	 * 
	 */
	private static class UnusedState implements FieldState
	{
		/** It must be only one object of class UnusedState. */
		private static UnusedState unusedState = null;

		/**
		 * To secure only one instatnce of class UnusedState.
		 * 
		 */
		private UnusedState()
		{
		}

		/**
		 * @return Instance (which is only one) of class UnusedState.
		 */
		public static UnusedState instance()
		{
			if (unusedState == null)
			{
				unusedState = new UnusedState();
			}
			return unusedState;
		}

		/**
		 * Hit on unused field.
		 * 
		 * @pararm message Message for players.
		 * @return The next state for this field: MiisState
		 * @see pl.edu.pw.elka.adziedzi.ships.model.FieldModel.FieldState#opponentHit(MessageModel)
		 */
		public FieldState opponentHit(MessageModel message)
		{
			// set a message for player who defends
			message.setMessageForOpponent("We have missed, sir. \n");
			message.setMessageForMe("Opponent has missed. \n");

			//the player is always changed after hit in field with UnusedState
			return MissState.instance();
		}

		/**
		 * @return The present state.
		 */
		public FIELD_STATE getState()
		{
			return FIELD_STATE.UNUSED;
		}

		/**
		 * @return The present state for Opponent.
		 */
		public FIELD_STATE getStateForOpponent()
		{
			return FIELD_STATE.UNUSED;
		}

	}

	/** 
	 * The second state of field - MissState (chybienie). 
	 * 
	 */
	private static class MissState implements FieldState
	{
		/** It must be only one object of class MissState. */
		private static MissState missState = null;

		/**
		 * To secure only one instance of class MissState.
		 * 
		 */
		private MissState()
		{
		}

		/**
		 * @return Instance (which is only one) of class UnusedState.
		 */
		public static MissState instance()
		{
			if (missState == null)
			{
				missState = new MissState();
			}
			return missState;
		}

		/**
		 * Hit on miss filed.
		 * 
		 * @param message Message for player who hits (for opponent).
		 * @return The state MissState, because it is the last state for field.
		 * @see pl.edu.pw.elka.adziedzi.ships.model.FieldModel.FieldState#opponentHit(MessageModel)
		 */
		public FieldState opponentHit(MessageModel message)
		{
			// this state is the last for field

			// set a message for player who hits
			message.setMessageForOpponent("This position has arleady been hit, sir - try again. \n");

			// do not change the player, because the player
			// who hits have to repeat the hit
			return MissState.instance();
		}

		/**
		 * @return The present state.
		 */
		public FIELD_STATE getState()
		{
			return FIELD_STATE.MISS;
		}

		/**
		 * @return The present state of field for Opponent.
		 */
		public FIELD_STATE getStateForOpponent()
		{
			return FIELD_STATE.MISS;
		}

	}

}
