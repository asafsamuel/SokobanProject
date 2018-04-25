package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

import common.Level;
import model.data.LevelLoader;
import model.data.LevelSaver;
import model.data.MyObjectLevelLoader;
import model.data.MyTextLevelLoader;
import model.data.MyXmlLevelLoader;
import model.data.ObjectLevelSaver;
import model.data.TextLevelSaver;
import model.data.XmlLevelSaver;
import model.db.Player;
import model.db.PlayersRecords;
import model.policy.MySokobanPolicy;

/** MyModel - part of the MVC pattern.
 *  This class connects with the server keep and save all necessary data.
 */
public class MyModel extends Observable implements Model 
{
	// Local Variables
	Level _level;
	Level _ResetLevel;
	String direction;
	List<Player> playersList;
	List<Level> levelList;
	
	HashMap<String, LevelLoader> _hashMap1;
	HashMap<String, LevelSaver> _hashMap2;
	
	Socket theServer;
	ObjectInputStream reader;
	ObjectOutputStream writer;
	
	// C'tor
	public MyModel() 
	{
		connectToServer();	// Connecting to server
		
		direction = "Up";
		this._level = null;
		
		// Initialization Load-Type HashMap
		this._hashMap1 = new HashMap<String, LevelLoader>();
		this._hashMap1.put("txt", new MyTextLevelLoader());
		this._hashMap1.put("obj", new MyObjectLevelLoader());
		this._hashMap1.put("xml", new MyXmlLevelLoader());

		// Initialization Save-Type HashMap
		this._hashMap2 = new HashMap<String, LevelSaver>();
		this._hashMap2.put("obj", new ObjectLevelSaver());
		this._hashMap2.put("xml",new XmlLevelSaver());
		this._hashMap2.put("txt", new TextLevelSaver());
	}
	
	// Connect to server function
	private void connectToServer()
	{
		String ip = "192.168.1.105";
		int port= Integer.parseInt("1003");
		
		try
		{
			theServer = new Socket(ip, port);
			System.out.println("connected to server...");
			
			writer = new ObjectOutputStream(theServer.getOutputStream());
			reader = new ObjectInputStream(theServer.getInputStream());
		}
		
		catch(Exception e)
		{
			System.out.println("Cannot connecting to server!");
		}
	}
	
	/** This fucntion returns Level variable **/
	@Override
	public Level getLevel() 
	{
		return this._level;
	}

	/** This function loads a Level from a file and saves it as a variable **/
	@Override
	public void LoadLevel(String location, String levelName) throws ClassNotFoundException, FileNotFoundException, IOException 
	{
		String _extension = location.substring(location.indexOf(".") + 1);

		// Know extension
		if(this._hashMap1.containsKey(_extension.toLowerCase()))
		{
			this._level = this._hashMap1.get(_extension.toLowerCase()).loadlevel(new FileInputStream(location));
			this._level.setLevelName(levelName);
			
			this._ResetLevel = new Level(_level);

			this.setChanged();
			List<String> params = new LinkedList<String>();
			params.add("DisplayLevel");
			this.notifyObservers(params); // notify Controller to display the level
			
			addLevelDB(params);
		}

		else
		{
			this.setChanged();
			List<String> params = new LinkedList<String>();
			params.add("DisplayMessage");
			params.add("Invalid file extension");
			this.notifyObservers(params);
		}
	}

	/** This function saves the Level to a file **/
	@Override
	public void SaveLevel(String location) throws ClassNotFoundException, FileNotFoundException, IOException, Exception 
	{
		String _extension = location.substring(location.indexOf(".") + 1);

		// Know extension and Level isn't null
		if(this._hashMap2.containsKey(_extension.toLowerCase()) && this._level != null)
		{
			this._hashMap2.get(_extension.toLowerCase()).savelevel(new FileOutputStream(new File(location)), this._level);

			this.setChanged();
			List<String> params = new LinkedList<String>();
			params.add("DisplayMessage");
			params.add("Save successfully");
			this.notifyObservers(params);
		}

		else
		{
			this.setChanged();
			List<String> params = new LinkedList<String>();
			params.add("DisplayMessage");
			params.add("Level is empty - load a level first!");
			this.notifyObservers(params);
		}
	}

	/** This function changes and swaps between positions in Level's board **/
	@Override
	public void MoveCharacter(String direction) 
	{
		this.direction = direction;

		if(this._level == null)
		{
			this.setChanged();
			List<String> params = new LinkedList<String>();
			params.add("DisplayMessage");
			params.add("Level is empty - load a level first!");
			this.notifyObservers(params);
		}

		else
		{
			if(new MySokobanPolicy().CheckPolicy(this._level, direction))
			{
				// Go up
				if(direction.equals("Up"))
				{
					// Swap Character with Floor\Target
					if(!this._level.get_gameBoard().get(this._level.get_characterY() - 1).get(this._level.get_characterX()).CanMove())
						this._level.SwapCells(this._level.get_characterX(), this._level.get_characterY(), this._level.get_characterX(), this._level.get_characterY() - 1);

					// Push Box
					else
					{
						this._level.SwapCells(this._level.get_characterX(), this._level.get_characterY() - 1, this._level.get_characterX(), this._level.get_characterY() - 2);
						this._level.SwapCells(this._level.get_characterX(), this._level.get_characterY(), this._level.get_characterX(), this._level.get_characterY() - 1);
					}

					this._level.set_characterX(this._level.get_characterX());
					this._level.set_characterY(this._level.get_characterY() - 1);
				}

				// Go down
				else if(direction.equals("Down"))
				{
					// Swap Character with Floor\Target
					if(!this._level.get_gameBoard().get(this._level.get_characterY() + 1).get(this._level.get_characterX()).CanMove())
						this._level.SwapCells(this._level.get_characterX(), this._level.get_characterY(), this._level.get_characterX(), this._level.get_characterY() + 1);

					// Push Box
					else
					{
						this._level.SwapCells(this._level.get_characterX(), this._level.get_characterY() + 1, this._level.get_characterX(), this._level.get_characterY() + 2);
						this._level.SwapCells(this._level.get_characterX(), this._level.get_characterY(), this._level.get_characterX(), this._level.get_characterY() + 1);
					}

					this._level.set_characterX(this._level.get_characterX());
					this._level.set_characterY(this._level.get_characterY() + 1);
				}

				// Go right
				else if(direction.equals("Right"))
				{
					// Swap Character with Floor\Target
					if(!this._level.get_gameBoard().get(this._level.get_characterY()).get(this._level.get_characterX() + 1).CanMove())
						this._level.SwapCells(this._level.get_characterX(), this._level.get_characterY(), this._level.get_characterX() + 1, this._level.get_characterY());

					// Push Box
					else
					{
						this._level.SwapCells(this._level.get_characterX() + 1, this._level.get_characterY(), this._level.get_characterX() + 2, this._level.get_characterY());
						this._level.SwapCells(this._level.get_characterX(), this._level.get_characterY(), this._level.get_characterX() + 1, this._level.get_characterY());
					}

					this._level.set_characterX(this._level.get_characterX() + 1);
					this._level.set_characterY(this._level.get_characterY());
				}

				// Go left
				else if(direction.equals("Left"))
				{
					// Swap Character with Floor\Target
					if(!this._level.get_gameBoard().get(this._level.get_characterY()).get(this._level.get_characterX() - 1).CanMove())
						this._level.SwapCells(this._level.get_characterX(), this._level.get_characterY(), this._level.get_characterX() - 1, this._level.get_characterY());

					// Push Box
					else
					{
						this._level.SwapCells(this._level.get_characterX() - 1, this._level.get_characterY(), this._level.get_characterX() - 2, this._level.get_characterY());
						this._level.SwapCells(this._level.get_characterX(), this._level.get_characterY(), this._level.get_characterX() - 1, this._level.get_characterY());
					}

					this._level.set_characterX(this._level.get_characterX() - 1);
					this._level.set_characterY(this._level.get_characterY());
				}

				this._level.set_numOfSteps(this._level.get_numOfSteps() + 1);

				this.setChanged();
				List<String> params = new LinkedList<String>();
				params.add("DisplayLevel");
				this.notifyObservers(params); // notify Controller to display the level
			}
		}
	}

	/** This function returns Player last movement direction **/
	@Override
	public String getDirection() 
	{
		return this.direction;
	}

	/** This function reset the Level (like it was when it was loaded from a file or from the server) **/
	@Override
	public void getResetLevel() 
	{
		if(this._ResetLevel == null)
			return;

		this._level = new Level(this._ResetLevel);
		this.direction = "Up";

		this.setChanged();
		List<String> params = new LinkedList<String>();
		params.add("DisplayLevel");
		this.notifyObservers(params); // notify Controller to display the level
	}

	/** This function closes the connection with the server **/
	@Override
	public void close() 
	{	
		try
		{
			writer.writeObject("Exit");
			writer.flush();
			writer.close();
			reader.close();
		}
		
		catch (Exception e) 
		{
			System.out.println("Cannot close writer/reader");
		}
	}

	/** This function adds a new Player to server's db **/
	@Override
	public void addPlayerDB(List<String> params) 
	{
		try
		{
			Player p = new Player(params.get(0));
			writer.writeObject("addPlayerDB");
			writer.writeObject(p);
			writer.flush();
		}
		
		catch (Exception e) 
		{
			this.setChanged();
			params = new LinkedList<String>();
			params.add("DisplayMessage");
			params.add("Cannot add new Player to server's db");
			this.notifyObservers(params);
		}
		
		this.setChanged();
		params = new LinkedList<String>();
		params.add("GetAllPlayersDB");
		this.notifyObservers(params); // notify Controller to update View's Players-List
	}

	/** This function adds a new Score to server's db **/
	@Override
	public void addScoreDB(List<String> params) 
	{
		try
		{
			PlayersRecords pr = new PlayersRecords(params.remove(0), Integer.parseInt(params.remove(0)), Integer.parseInt(params.remove(0)), Integer.parseInt(params.remove(0)), params.remove(0));
			writer.writeObject("addScoreDB");
			writer.writeObject(pr);
			writer.flush();
		}
		
		catch (Exception e) 
		{
			this.setChanged();
			params = new LinkedList<String>();
			params.add("DisplayMessage");
			params.add("Cannot add new Score to server' db");
			this.notifyObservers(params);
		}	
	}

	// Add new level to server db
	private void addLevelDB(List<String> params) 
	{
		try
		{
			writer.writeObject("addLevelDB");
			writer.writeObject(_level);
			writer.flush();
		}
		
		catch (Exception e) 
		{
			this.setChanged();
			params = new LinkedList<String>();
			params.add("DisplayMessage");
			params.add("Cannot add new level to db");
			this.notifyObservers(params);
		}	
	}

	/** This function sorts Level's Scores by steps **/
	@Override
	public void sortLevelScoresSteps(String levelName) 
	{
		try
		{
			writer.writeObject("sortLevelScoresSteps");
			writer.writeObject(levelName);
			writer.flush();
		}
		
		catch (Exception e) 
		{
			this.setChanged();
			LinkedList<String> params = new LinkedList<String>();
			params.add("DisplayMessage");
			params.add("Cannot sort level scores");
			this.notifyObservers(params);
		}
		
		this.setChanged();
		List<String> params = new LinkedList<String>();
		params.add("UpdateLevelScores");
		this.notifyObservers(params); // notify Controller to set View's Level Scores
	}

	/** This function sorts Level's Scores by time **/
	@Override
	public void sortLevelScoresTime(String levelName) 
	{
		try
		{
			writer.writeObject("sortLevelScoresTime");
			writer.writeObject(levelName);
			writer.flush();
		}
		
		catch (Exception e) 
		{
			this.setChanged();
			LinkedList<String> params = new LinkedList<String>();
			params.add("DisplayMessage");
			params.add("Cannot sort level scores");
			this.notifyObservers(params);
		}
		
		this.setChanged();
		List<String> params = new LinkedList<String>();
		params.add("UpdateLevelScores");
		this.notifyObservers(params); // notify Controller to set View's Level Scores
	}

	/** This function gets Player's Scores from the server **/
	@Override
	public void setSpecificPlayerScores(String playerId) 
	{
		try
		{
			writer.writeObject("setSpecificPlayerScores");
			writer.writeObject(playerId);
			writer.flush();
		}
		
		catch (Exception e) 
		{
			this.setChanged();
			LinkedList<String> params = new LinkedList<String>();
			params.add("DisplayMessage");
			params.add("Cannot set specific player id");
			this.notifyObservers(params);
		}
		
		this.setChanged();
		List<String> params = new LinkedList<String>();
		params.add("UpdateChosenPlayerScores");
		this.notifyObservers(params); // notify Controller to set View's Chosen Player Scores
	}

	/** This function returns all Player's Scores **/
	@SuppressWarnings("unchecked")
	@Override
	public List<PlayersRecords> getSpecificPlayerScores() 
	{
		List<PlayersRecords> list = null;
		try
		{
			writer.writeObject("getSpecificPlayerScores");
			list = (List<PlayersRecords>) reader.readObject();
			writer.flush();
		}
		
		catch (Exception e) 
		{
			this.setChanged();
			LinkedList<String> params = new LinkedList<String>();
			params.add("DisplayMessage");
			params.add("Cannot load player scores");
			this.notifyObservers(params);
		}
		
		return list;
	}

	/** This function returns all Level's Scores **/
	@SuppressWarnings("unchecked")
	@Override
	public List<PlayersRecords> getLevelScores() 
	{
		List<PlayersRecords> list = null;
		
		try
		{
			writer.writeObject("getLevelScores");
			list = (List<PlayersRecords>) reader.readObject();
			writer.flush();
		}
		
		catch (Exception e) 
		{
			this.setChanged();
			LinkedList<String> params = new LinkedList<String>();
			params.add("DisplayMessage");
			params.add("Cannot load level score");
			this.notifyObservers(params);
		}
		
		return list;
	}

	/** This function returns all Players that exist in the db **/
	@SuppressWarnings("unchecked")
	@Override
	public List<Player> getPlayers() 
	{
		try
		{
			writer.writeObject("getPlayers");
			playersList = (ArrayList<Player>) reader.readObject();
		}
		
		catch (Exception e) 
		{
			this.setChanged();
			LinkedList<String> params = new LinkedList<String>();
			params.add("DisplayMessage");
			params.add("Cannot display players");
			this.notifyObservers(params);
		}
		
		return playersList;
	}

	/** This function returns all Levels that exist in the db **/
	@SuppressWarnings("unchecked")
	@Override
	public List<Level> getLevels() 
	{
		try
		{
			writer.writeObject("getLevels");
			levelList = (List<Level>) reader.readObject();
			writer.flush();
		}
		
		catch (Exception e) 
		{
			this.setChanged();
			LinkedList<String> params = new LinkedList<String>();
			params.add("DisplayMessage");
			params.add("Cannot load levels");
			this.notifyObservers(params);
		}
		
		return levelList;
	}

	/** This function gets a Level from a server by it's name **/
	@Override
	public void LoadLevelFromServer(String LevelName) 
	{
		try
		{
			writer.writeObject("loadLevel");
			writer.writeObject(LevelName);
			
			this._level = (Level) reader.readObject();
			this._ResetLevel = new Level(_level);
			
			this.setChanged();
			List<String> params = new LinkedList<String>();
			params.add("DisplayLevel");
			this.notifyObservers(params); // notify Controller to display the level
		}
		
		catch (Exception e) 
		{
			e.printStackTrace();
			this.setChanged();
			LinkedList<String> params = new LinkedList<String>();
			params.add("DisplayMessage");
			params.add("Cannot load level from server");
			this.notifyObservers(params);
		}
	}

	/** This function sends hint request to the server **/
	@Override
	public void sendHintRequest() 
	{
		try
		{
			writer.writeObject("getHint");
			writer.writeObject(new Level(this._level));
			
			String move = (String) reader.readObject();
			
			if(move.contains("up"))
				MoveCharacter("Up");
			
			else if(move.contains("down"))
				MoveCharacter("Down");
			
			else if(move.contains("left"))
				MoveCharacter("Left");
			
			else	// right
				MoveCharacter("Right");
		}
		
		catch (Exception e) 
		{
			this.setChanged();
			LinkedList<String> params = new LinkedList<String>();
			params.add("DisplayMessage");
			params.add("Cannot get hint from the server");
			this.notifyObservers(params);
		}
	}

	/** This function sends solution request to the server **/
	@SuppressWarnings("unchecked")
	@Override
	public void sendSolutionRequest(String levelName) 
	{
		this.setChanged();
		LinkedList<String> params = new LinkedList<String>();
		params.add("EndGame");
		this.notifyObservers(params);
		
		Thread t = new Thread(new Runnable() 
		{
			@Override
			public void run() 
			{
				try
				{
					writer.writeObject("getSolution");
					writer.writeObject(levelName);

					List<String> solution = (List<String>) reader.readObject();
					Thread.sleep(500);

					if(solution != null)
					{
						getResetLevel();	
						for (String string : solution) 
						{
							String[] array = string.split("\n");
							for (String dir : array) 
							{
								if(dir.contains("up"))
									MoveCharacter("Up");

								else if(dir.contains("down"))
									MoveCharacter("Down");

								else if(dir.contains("left"))
									MoveCharacter("Left");

								else	// right
									MoveCharacter("Right");

								Thread.sleep(500);
							}
						}
					}
				}

				catch (Exception e) 
				{
					// do nothing!
				}
			}
		});
		t.start();
	}
}
