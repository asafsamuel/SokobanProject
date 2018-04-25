package viewmodel;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.application.Platform;
import model.Model;

/** MyServer - this class is part of the MVVM pattern.
 *  This class is lisening with socket to clients and open for them ClientHandler **/
public class MyServer extends Observable implements Observer
{
	// Local Variables
	Model model;
	ServerSocket serverSocket;
	ExecutorService threadPool;
	boolean stop;
	int port;
	int count;

	// C'tor
	public MyServer(int port, Model model)
	{
		this.model = model;
		this.port = port;
		this.stop = false;
		count = 1;
		threadPool = Executors.newFixedThreadPool(10);
	}

	/** This function creates server socket and listens to users' sockets **/
	public void start()
	{
		stop = false;
		System.out.println("Server is online!");

		try
		{
			serverSocket = new ServerSocket(port);
			lookingForClient(); // call function "lookForClient"

			serverSocket.close();
			this.threadPool.shutdown();
	        System.out.println("Server Stopped...") ;
		}

		catch (IOException e)
		{
			System.out.println("Cannot open/close server socket!");
		}
	}

	/** This function sets flag to true and waits for all clients' sockets to finish **/
	public void stopServer()
	{
		stop = true;
		System.out.println("Socket is closed");
	}

	/** This function closes server socket and stop all threads immediately **/
	public synchronized void close()
	{
		stop = true;

		try
		{
			this.threadPool.shutdown();
			serverSocket.close();
			System.out.println("server is closed! ");
			System.exit(0);
		}

		catch (IOException e)
		{
			System.out.println("Error closing server");
		}
	}

	// Creates a ClientHandler and connect to client's socket
	private void lookingForClient()
	{
		while(!stop)
		{
			Socket clientSocket = null;

			try
			{
				clientSocket = this.serverSocket.accept();
				System.out.println("Client connected");	// Client connected!
			}

			catch (IOException e)
			{
				if(stop)
				{
					System.out.println("Server Stopped...") ;
                	break;
				}

				throw new RuntimeException("Error accepting client connection", e);
			}
			
			ClientHandler ch = new ClientHandler(clientSocket, model,count);
			count++;
			
			Platform.runLater(new Runnable() 
			{
				@Override
				public void run() 
				{
					model.addClient(ch);
				}
			});

			this.threadPool.execute(ch);
		}
	}
	
	/** This function changes Model and View client's list **/
	@SuppressWarnings("unchecked")
	@Override
	public void update(Observable o, Object arg) 
	{
		if(o == model)	// list of clients has changed
		{
			List<String> names = new ArrayList<>();
			List<ClientHandler> cli = (List<ClientHandler>) arg;

			for (ClientHandler clientHandler : cli) 
				names.add(clientHandler.toString());

			this.setChanged();
			this.notifyObservers(names);
		}

		else	// admin want to close connection with client
		{
			String clientName = (String) arg;
			model.closeClient(clientName);
		}	
	}
}
