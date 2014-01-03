/**
 * 
 */
package pl.edu.pw.elka.adziedzi.ships.network;

import static pl.edu.pw.elka.adziedzi.ships.network.NumberOfPorts.PortServerOut;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.BlockingQueue;

import pl.edu.pw.elka.adziedzi.ships.network.messages.ClientClosedMessage;
import pl.edu.pw.elka.adziedzi.ships.network.messages.ServerClosedMessage;
import pl.edu.pw.elka.adziedzi.ships.network.messages.ServerMessage;


/**
 * Server for sending information to client.
 *
 * @author Adam Dziedzic grupa K1I1
 * May 28, 2009
 * network
 */
public class ServerOut implements Runnable
{
    /** creates new sever socket to send information to clinet */
    private ServerSocket serverSocket;
    
    /** incoming -socket */
    private Socket outcoming;
    
    /** Stream to send information from controller to client by serverOut and network. */
    private ObjectOutputStream outStream;
    
    /** To send information from controller to client by serverOut */
    private BlockingQueue<ServerMessage> queue;
    
    /**
     * Creates new sever to send information from server to client.
     * 
     * @param queue Queue for imformation from controller to client.
     */
    public ServerOut(BlockingQueue<ServerMessage> queue)
    {
        this.queue = queue;
    }
    
    /**
     * Begin new thread which serves sending information from controller to client 
     * by network.
     * 
     */
    public void run()
    {
        try
        {
            // creates new server socket
            serverSocket = new ServerSocket(PortServerOut);
            try
            {
                // wait for connection with client for 20 seconds
                serverSocket.setSoTimeout(20000);
                outcoming = serverSocket.accept();
            }
            catch(SocketTimeoutException e)
            {
            	serverSocket.close();
                return;
            }
            
            try
            {
                OutputStream outputStream = outcoming.getOutputStream();
                outStream = new ObjectOutputStream(outputStream);
                sendObjectsFromQueue();
            }
            finally
            {
                outStream.close();
                outcoming.close();
                serverSocket.close();
            }
        }  
        
        catch(IOException e)
        {
            e.printStackTrace();
        }
        System.out.println("ServerOut\n");
    }
    
    private void sendObjectsFromQueue()
    {
        /* Object to send. */
        ServerMessage message = null;
        
        while(true)
        {
            // take message from messageQueue
            try
            {
                message = queue.take();
            }
            catch(InterruptedException e)
            { 
                return; 
            }
            
            try
            {
                outStream.writeObject(message);
            }
            catch(IOException e)
            {
            	e.printStackTrace();
            }
            
            if(message.getClass() == ServerClosedMessage.class 
                    || message.getClass() == ClientClosedMessage.class)
            {
                return;
            }
            
        }
    }
}
