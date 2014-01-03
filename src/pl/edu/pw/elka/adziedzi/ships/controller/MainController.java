/**
 * 
 */
package pl.edu.pw.elka.adziedzi.ships.controller;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;

import pl.edu.pw.elka.adziedzi.ships.common.PLAYER;
import pl.edu.pw.elka.adziedzi.ships.network.ClientIn;
import pl.edu.pw.elka.adziedzi.ships.network.ClientOut;
import pl.edu.pw.elka.adziedzi.ships.network.SERVER_CLIENT;
import pl.edu.pw.elka.adziedzi.ships.network.ServerIn;
import pl.edu.pw.elka.adziedzi.ships.network.ServerOut;
import pl.edu.pw.elka.adziedzi.ships.network.messages.NetMessage;
import pl.edu.pw.elka.adziedzi.ships.network.messages.ServerMessage;
import pl.edu.pw.elka.adziedzi.ships.view.ServerClientChooserView;
import pl.edu.pw.elka.adziedzi.ships.view.View;
import pl.edu.pw.elka.adziedzi.ships.view.messages.MessageView;

/**
 * The main class of program - the beginning.
 * 
 * @author Adam Dziedzic grupa: K1I1 May 1, 2009 controller
 */
public class MainController {
	/** Information about choice server or client, and address of server. */
	private static NetMessage netMessage = new NetMessage();

	/**
	 * To get information from player - it exchanges only one information
	 * beetwen threads.
	 */
	private static SynchronousQueue<NetMessage> queueSynchronous = new SynchronousQueue<NetMessage>();

	/**
	 * To get information from player - it stores information from view and
	 * serverIn.
	 */
	private static BlockingQueue<MessageView> queue = new LinkedBlockingQueue<MessageView>();

	/** To send information from controller to client by serverOut */
	private static BlockingQueue<ServerMessage> queueForServerOut = new LinkedBlockingQueue<ServerMessage>();

	/** The main view of game. */
	private static View view = null;

	/**
	 * 
	 * @param args
	 *            the arguments of program
	 */
	public static void main(String[] args) {

		ServerClientChooserView.instance(queueSynchronous);
		try {
			netMessage = (NetMessage) queueSynchronous.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		SERVER_CLIENT netOption = netMessage.getServerClientOption();

		// if player has not chosen any option - end of game
		if (netOption == null) {
			return;
		}

		if (netOption == SERVER_CLIENT.SERVER) {
			serverInitialization();
		} else {
			clientInitialization();
		}
		

	}

	/**
	 * Initialize of server.
	 * 
	 */
	private static void serverInitialization() {
		while (view == null)
			view = View.instance(queue, PLAYER.FIRST);

		// initialization of server which send information
		// from controller to client
		ServerOut serverOut = new ServerOut(queueForServerOut);
		Thread serverOutThread = new Thread(serverOut);
		serverOutThread.start();

		// initialization of server which receive information
		// from client and send them to controller
		ServerIn serverIn = new ServerIn(queue, view);
		Thread serverInThread = new Thread(serverIn);
		serverInThread.start();

		// creates controller of game
		new Controller(view, queue, queueForServerOut);

	}

	/**
	 * Initialization of client.
	 * 
	 */
	private static void clientInitialization() {
		// initialization of client socket which send information
		// from client's view to controller
		ClientOut clientOut = new ClientOut(netMessage.getServerAddress(),
				queue);
		Thread clientOutThread = new Thread(clientOut);
		clientOutThread.start();

		while (view == null)
			view = View.instance(queue, PLAYER.SECOND);

		// initialization of client socket which receive information
		// from controller and send them to view of client
		ClientIn clientIn = new ClientIn(netMessage.getServerAddress(), view);
		Thread clientInThread = new Thread(clientIn);
		clientInThread.start();
	}

}
