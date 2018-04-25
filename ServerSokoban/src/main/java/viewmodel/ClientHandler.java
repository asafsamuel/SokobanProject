package viewmodel;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

import common.Level;
import javafx.application.Platform;
import model.Model;
import model.db.Player;
import model.db.PlayersRecords;

/** ClientHandler - this class connects with the client, gets his\her request and response **/
public class ClientHandler implements Runnable
{
	// Local Variables
	Model model;
	Socket clientSocket;
	ObjectInputStream reader;
	ObjectOutputStream writer;
	int id;
	boolean stop;
	
	List<PlayersRecords> levelScores;
	List<PlayersRecords> playerScores;
	
	// C'tor
	public ClientHandler(Socket clientSocket, Model model, int id) 
	{
		this.model = model;
		this.clientSocket = clientSocket;
		levelScores = null;
		playerScores = null;
		stop = false;
		this.id = id;
	}

	/** This function gets request from the client and answer **/
	@Override
	public void run() 
	{
		try
		{
			this.reader = new ObjectInputStream(clientSocket.getInputStream());
			this.writer = new ObjectOutputStream(clientSocket.getOutputStream());
			
			while (!stop)
			{
				try
				{
					String command = (String) reader.readObject();	// Get command from client
					
					// Switch-case
					switch (command) 
					{
					case "getPlayers":
						sendPlayers(model.getPlayers());
						break;
						
					case "getLevels":
						sendLevels(model.getLevels());
						break;
						
					case "getHint":
						Level l = (Level)reader.readObject();
						sendDirection(model.getLevelHint(l));
						break;
						
					case "getSolution":
						sendSolution(model.getLevelSolution((String)reader.readObject()));
						break;
						
					case "addPlayerDB":
						model.addPlayerDB((Player) reader.readObject());
						break;
						
					case "addScoreDB":
						model.addScoreDB((PlayersRecords) reader.readObject());
						break;
						
					case "addLevelDB":
						Level l2 = (Level) reader.readObject();
						model.addLevelDB(l2);
						break;
						
					case "sortLevelScoresSteps":
						levelScores = model.sortLevelScoresSteps((String)reader.readObject());
						break;
						
					case "sortLevelScoresTime":
						levelScores = model.sortLevelScoresTime((String)reader.readObject());
						break;
						
					case "setSpecificPlayerScores":
						playerScores = model.getSpecificPlayerScores((String)reader.readObject());
						break;
						
					case "getSpecificPlayerScores":
						sendScores(playerScores);
						break;
						
					case "getLevelScores":
						sendScores(levelScores);
						break;
						
					case "loadLevel":
						sendLevelLoad((String)reader.readObject());
						break;
						
					case "Exit":
						System.out.println("Client disconnected!");
						close();
						return;

					default:
						System.out.println("Don't recognize command");
						break;
					}
				}

				catch (IOException | ClassNotFoundException e)
				{
					System.out.println("Cannot get command from client");
					close();
				}
			}
		}
		
		catch (IOException e) 
		{
            e.printStackTrace();
        }
	}
	
	// Sends a solution to client
	private void sendSolution(List<String> solution) 
	{
		try
		{
			writer.writeObject(solution);
		}
		
		catch (Exception e) 
		{
			System.out.println("Cannot send solution to client");
		}
	}

	// Sends an hint (direction) to client
	private void sendDirection(String hint) 
	{
		try 
		{
			writer.writeObject(hint);
		} 
		
		catch (IOException e) 
		{
			System.out.println("Cannot send hint to client");
		}
	}

	// Loads a level from db and send it to client
	private void sendLevelLoad(String LevelName) 
	{
		try 
		{
			writer.writeObject(model.LoadLevelDB(LevelName));
		} 
		
		catch (IOException e) 
		{
			System.out.println("Cannot load level and send it to client");
		}
	}

	// Sends all Levels from db to client
	private void sendLevels(List<Level> levels) 
	{
		try
		{
			writer.writeObject(levels);
			writer.flush();
		}
		
		catch (Exception e) 
		{
			System.out.println("Cannot send level-scores to client");
		}
	}

	// Sends all Scores from db to client
	private void sendScores(List<PlayersRecords> scores) 
	{
		try
		{
			writer.writeObject(scores);
			writer.flush();
		}
		
		catch (Exception e) 
		{
			System.out.println("Cannot send level-scores to client");
		}
	}

	// Sends all players-accounts from db to client
	private void sendPlayers(List<Player> players) 
	{
		try
		{
			writer.writeObject(players);
			writer.flush();
		}
		
		catch (Exception e) 
		{
			System.out.println("Cannot send players-account to client");
		}
	}

	/** This function close thread and the connection with the client **/
	public synchronized void close()
	{
		stop = true;
		
		try
		{
			writer.close();
			reader.close();
			String name = this.toString();
			
			Platform.runLater(new Runnable() 
			{
				@Override
				public void run() 
				{
					model.removeClient(name);
				}
			});
		}
		
		catch (Exception e) 
		{
			System.out.println("Cannot close socket property!");
		}
	}
	
	// Equals, hashcode, tostring
	@Override
	public String toString() 
	{
		return "client "+id;
	}
	@Override
	public int hashCode() 
	{
		return toString().hashCode();
	}
	@Override
	public boolean equals(Object obj) 
	{
		if(!(obj instanceof ClientHandler))
			return false;
		
		ClientHandler ch = (ClientHandler) obj;
		return this.id==ch.id;
	}
}
