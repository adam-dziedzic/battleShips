/**
 * 
 */
package pl.edu.pw.elka.adziedzi.ships.network.messages;

import pl.edu.pw.elka.adziedzi.ships.network.SERVER_CLIENT;


/**
 * NetMessage needed at the beginning of game.
 * 
 * @author Adam Dziedzic grupa: K1I1
 * May 12, 2009
 * network
 */
public class NetMessage
{
    /** Determines if player is connected as client or server. */
    private SERVER_CLIENT serverClientOption = null;
    /** Address of server which client want to connect with. */
    private String serverAddress = "";
    
    /**
     * 
     * @return the serverAddress
     */
    public String getServerAddress()
    {
        return serverAddress;
    }
    
    /**
     * 
     * @param serverAddress the serverAddress to set
     */
    public void setServerAddress(String serverAddress)
    {
        this.serverAddress = serverAddress;
    }
    
    /**
     * 
     * @return the serverClientOption
     */
    public SERVER_CLIENT getServerClientOption()
    {
        return serverClientOption;
    }
    
    /**
     * 
     * @param serverClientOption the serverClientOption to set
     */
    public void setServerClientOption(SERVER_CLIENT serverClientOption)
    {
        this.serverClientOption = serverClientOption;
    }
}
