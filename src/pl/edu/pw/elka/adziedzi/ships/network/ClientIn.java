/**
 * 
 */
package pl.edu.pw.elka.adziedzi.ships.network;

import static pl.edu.pw.elka.adziedzi.ships.network.NumberOfPorts.PortServerOut;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;

import pl.edu.pw.elka.adziedzi.ships.network.messages.ClientClosedMessage;
import pl.edu.pw.elka.adziedzi.ships.network.messages.ServerClosedMessage;
import pl.edu.pw.elka.adziedzi.ships.network.messages.ServerMessage;
import pl.edu.pw.elka.adziedzi.ships.view.View;


/**
 * ClientIn - receive information from contoller by serverOut and network.
 *
 * @author Adam Dziedzic grupa K1I1
 * May 28, 2009
 * network
 */
public class ClientIn implements Runnable
{
    /** View - direct refernce to view. */
    private View view;
    
    /** Address of server. */
    private String address;
    
    /** socket */
    private Socket socket;
    
    /** Stream to receive information from controller which is sent by serverOut and network. */
    private ObjectInputStream inStream;
    
    /**
     * Constructor of ClientIn.
     * 
     * @param address Address of server.
     * @param view  Direct refernce to view.
     */
    public ClientIn(String address, View view)
    {
        this.address = address;
        this.view = view;
    }
    
    public void run()
    {   
        try
        {
            // creates new socket, wait for server 4 seconds
            socket = new Socket();
            try
            {
                // wait for server 10 seconds
                socket.connect(new InetSocketAddress(address, PortServerOut), 4000);
            }
            catch( ConnectException e)
            {
                view.InformAndCloseFrame( "Server is not available! ", "Server Message");
                return;
            }
            try
            {
                InputStream inputStream = socket.getInputStream();
                inStream = new ObjectInputStream(inputStream);
                readObjectsFromStream();
            }
            finally
            {
                socket.close();
            }
        }  
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    
    public void readObjectsFromStream()
    {
        ServerMessage message = null;
        
        // do until thread is not interrupted
        while(true)
        {
            try
            {
                message = (ServerMessage)inStream.readObject(); 
                message.execute(view);
            }
            catch(ClassNotFoundException e)
            {
            	e.printStackTrace();
            }
            catch(IOException e)
            {
            	e.printStackTrace();
            }
            
            if(message instanceof ServerClosedMessage || message instanceof ClientClosedMessage)
            {
                // Server inform about close in command: message.execute(view);
                return;
            }
        }
    }
}
