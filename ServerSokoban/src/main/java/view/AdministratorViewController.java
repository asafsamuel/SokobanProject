package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javafx.application.Platform;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import viewmodel.MyServer;

/** AdministratorViewController - this class is part of the MVVM pattern.
 *  This class displays all the connected clients and able the admin to open\close server
 *  and even close specific client connection **/
public class AdministratorViewController implements View,Observer
{
	// Local Variable
	MyServer server;
	boolean serverOn;
	
	@FXML
	ListView<String> myListView;
	
	List<String> clientsNames = new ArrayList<>();
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	ListProperty<String> clients = new SimpleListProperty();

	// C'tor and set server variable
	public void setVariables(MyServer server)
	{
		this.server = server;
		serverOn = false;
	}

	/** This function starts the server (call to Server's start() fucntion) **/
	@Override
	public void start()
	{
		if(!serverOn)
		{
			serverOn = true;
			Thread t = new Thread(new Runnable() 
			{	
				@Override
				public void run() 
				{
					server.start();
				}
			});
			t.start();
			
			displayMessage("Server is on");
		}

		else
			displayMessage("Server is already running");
	}

	/** This functino closes all threads and server **/
	@Override
	public void close()
	{
		if(serverOn)
		{
			serverOn = false;
			server.close();
		}
		
		System.exit(0);
	}
	
	/** This function displays a message to the screen **/
	public void displayMessage(String msg)
	{
		//System.out.println("Message: "+msg);
		Platform.runLater(
				() -> {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Message");
					alert.setHeaderText(null);
					alert.setContentText(msg);

					alert.showAndWait();
				});
	}

	/** This function gets updates from Server (ViewModel) **/
	@SuppressWarnings("unchecked")
	@Override
	public void update(Observable server, Object params)
	{
		clientsNames =  (List<String>) params;		
		myListView.itemsProperty().bind(clients);
		clients.set(FXCollections.observableArrayList(clientsNames));
	}

	/** This function closes server socket - dont let any client to connect anymore **/
	@Override
	public void stopServerSocket() 
	{
		if(serverOn)
			server.stopServer();
	}

	/** This function closes chosen client connection **/
	@Override
	public void endClientConnection() 
	{
		String name = myListView.getSelectionModel().getSelectedItem();
		
		if(name != null)
		{
			server.update(new Observable(), name);
		}
		
		return;
	}
}
