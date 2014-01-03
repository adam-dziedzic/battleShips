/**
 * 
 */
package pl.edu.pw.elka.adziedzi.ships.network;

import static pl.edu.pw.elka.adziedzi.ships.network.NumberOfPorts.PortServerIn;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.BlockingQueue;

import pl.edu.pw.elka.adziedzi.ships.view.View;
import pl.edu.pw.elka.adziedzi.ships.view.messages.MainFrameClosed;
import pl.edu.pw.elka.adziedzi.ships.view.messages.MessageView;


/**
 * Class to reveive information from client by network.
 *
 * @author Adam Dziedzic grupa K1I1
 * May 28, 2009
 * network
 */
public class ServerIn implements Runnable
{
    
    /** creates new sever socket to send information to clinet */
    private ServerSocket serverSocket;
    
    /** socket */
    private Socket incoming;
    
    /** Queue for send messages from client to controller. */
    private BlockingQueue<MessageView> queueForController;
    
    /** Stream to receive information from client to controller by serverIn and network. */
    private ObjectInputStream inStream;
    
    /** direct reference to view - to send information about network */
    private View view;
    
    /**
     * Constructor to create ServerIn with queue for
     * messages from client to controller.
     * 
     * @param messageQueue queue for messages
     */
    public ServerIn(BlockingQueue<MessageView> queueForController, View view)
    {
        super();
        this.queueForController = queueForController;
        this.view = view;
    }
    
    /**
     * Begin new thread which serves receiving information from client to controller 
     * by network.
     * 
     */
    public void run()
    {
        try
        {
            // creates new server socket
            serverSocket = new ServerSocket(PortServerIn);
            
            try
            {
            	view.addMessage("You have to wait for opponent.\n");
            	// wait for connection with client for 20 seconds
                serverSocket.setSoTimeout(20000);
                incoming = serverSocket.accept();
                view.addMessage("Opponent is connected.\n");
            }
            catch(SocketTimeoutException e)
            {
            	view.InformAndCloseFrame("Opponent was not connected for \n20 seconds!\n" +
            			"It is better to finish :(", "Message");
                serverSocket.close();
                return;
            }
  
            try
            {
                InputStream inputStream = incoming.getInputStream();
                inStream = new ObjectInputStream(inputStream);
                sendInformation();
            }
            finally
            {
                inStream.close();
                incoming.close();
                serverSocket.close();
            }
        }  
        catch(IOException e)
        {
            e.printStackTrace();
        }
        System.out.println("ServerInClosed\n");
    }
    
    /**
     * Send information from client to controller. 
     *
     */
    private void sendInformation()
    {
        MessageView message = null;
        
        while(true)
        {
            try
            {
                message = (MessageView)inStream.readObject();
                
                try
                {
                    queueForController.put(message);
                }
                catch(InterruptedException e)
                {
                    return;
                }
             
            }
            catch(ClassNotFoundException e)
            {
            	e.printStackTrace();
            }
            catch(IOException e)
            {
            	e.printStackTrace();
            }
            // frame of opponent was closed - the server should be closed too
            if(message.getClass() == MainFrameClosed.class)
            {
                return;
            }
        }
    }
}
