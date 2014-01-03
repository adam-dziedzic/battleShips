/**
 * Package contains common parts for model and controller.
 */
package pl.edu.pw.elka.adziedzi.ships.common;

/**
 * Describe state of field on the sea. (Typ wyliczeniowy do oznaczania stanu
 * pola (Field) na morzu (Sea).
 * 
 * @author Adam Dziedzic grupa: K1I1
 */
public enum FIELD_STATE 
{
	/** Field is not used. */
	UNUSED, 
	/** Field was hit and it was missed. */
	MISS,
	/** On field is mast and field was not hit. */
	SHIP,
	/** On field was mast, but was hit. */
	HIT, 
	/** On field was mast and the ship which contains this mast was sunk. */
	DESTROYED
};
