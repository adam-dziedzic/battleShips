/**
 * 
 */
package pl.edu.pw.elka.adziedzi.ships.network.messages;

import pl.edu.pw.elka.adziedzi.ships.common.FIELD_STATE;
import pl.edu.pw.elka.adziedzi.ships.view.View;


/**
 * Set enemy sea for client.
 *
 * @author Adam Dziedzic grupa K1I1
 * May 28, 2009
 * network.messagesFromServer
 */
public class EnemySeaServerMessage implements ServerMessage
{
    /** for serialization: */
    private static final long serialVersionUID = 1L;
    
    /** Home sea for Client. */
    private FIELD_STATE[][] sea;

    /**
     * Constructor which set enemy sea for client.
     * 
     * @param sea Enemy sea for client.
     */
    public EnemySeaServerMessage(FIELD_STATE[][] sea)
    {
        super();
        this.sea = sea;
    }
    
    /**
     * Method which executes action for view object. 
     *
     * @param view View which is modified.
     */
    public void execute(View view)
    {
        view.setEnemySea(sea);
    }

}
