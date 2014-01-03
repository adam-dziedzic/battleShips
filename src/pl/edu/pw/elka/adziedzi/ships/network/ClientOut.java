/**
 * 
 */
package pl.edu.pw.elka.adziedzi.ships.network;

import static pl.edu.pw.elka.adziedzi.ships.network.NumberOfPorts.PortServerIn;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.ConnectException;
import java.util.concurrent.BlockingQueue;

import pl.edu.pw.elka.adziedzi.ships.view.messages.MainFrameClosed;
import pl.edu.pw.elka.adziedzi.ships.view.messages.MessageView;


/**
 * Object of this class sends information from 
 * client to controller by serverIn and network.
 *
 * @author Adam Dziedzic grupa K1I1
 * May 28, 2009
 * network
 */
public class ClientOut implements Runnable
{
 
    /** address of server */
    private String address;
    
    /** out - socket */
    private Socket socket = null;
    
    /** Stream to send information from client's view to controller by clientOut and network. */
    private ObjectOutputStream outStream;
    
    /** To get information from client's view and send them to controller by ClientOut and network. */
    private BlockingQueue<MessageView> queue;
    
    /**
     * Constructor of client object.
     * 
     * @param queue Queue for information from client's view to controller.
     */
    public ClientOut(String address, BlockingQueue<MessageView> queue)
    {
        super();
        this.address = address;
        this.queue = queue;
    }
    
    /**
     * Begin new thread which serves sending information from 
     * client's view to controller by network.
     * 
     */
    public void run()
    {
        try
        {
            // creates new socket, wait for server 4 seconds
            socket = new Socket();
            try
            {
                socket.connect(new InetSocketAddress(address, PortServerIn), 4000);
            }
            catch( ConnectException e)
            {
                return;
            }
            
            try
            {
                OutputStream outputStream = socket.getOutputStream();
                outStream = new ObjectOutputStream(outputStream);
                sendObjectsFromQueue();
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
        System.out.println("ClientOut");
    }
    
    private void sendObjectsFromQueue()
    {
        MessageView message = null;
        
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
            if(message.getClass() == MainFrameClosed.class || socket.isConnected() == false)
            {
                return;
            }
        }
    }
}
