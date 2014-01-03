package pl.edu.pw.elka.adziedzi.ships.model;

/**
 * Describe result of hit.
 * (Opisuje rezultat strzału.)
 * 
 * @author Adam Dziedzic grupa: K1I1 
 * May 2, 2009 
 * model
 */
enum HIT_RESULT 
{
	/** WRONG: player hit in field, which state was: miss, hit or destroyed */
	WRONG,
	/** MISS: player hit in unused field */
	MISS,
	/** HIT: player hit in mast */
	HIT
};
