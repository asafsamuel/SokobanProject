package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import common.Level;
import model.data.LevelLoader;
import model.data.LevelSaver;
import model.data.MyTextLevelLoader;
import model.data.TextLevelSaver;
import model.db.MySokobanDbManager;
import model.db.Player;
import model.db.PlayersRecords;
import run.Run;
import viewmodel.ClientHandler;

/** MyModel - this class is prat of the MVVM pattern. This class connect with database and webserver (SolutionServer) **/
public class MyModel extends Observable implements Model
{
	// Local Variables
	MySokobanDbManager manager;
	HashMap<String, ClientHandler> clients;
	List<Player> listPlayers;
	LevelLoader load;
	LevelSaver save;
	Client client;
	WebTarget webTarget;
	String url;

	// C'tor
	public MyModel()
	{
		manager = MySokobanDbManager.getInstance();
		load = new MyTextLevelLoader();
		save = new TextLevelSaver();
		clients = new HashMap<>();
		listPlayers = manager.getAllPlayers();
		client = ClientBuilder.newClient();
		url = "http://localhost:8080/SolutionService/webapi/solutions/";
		webTarget = null;
	}
	
	/** This function adds new Client to list and notify Admin **/
	@Override
	public synchronized void addClient(ClientHandler ch)
	{
		clients.put(ch.toString(), ch);
		
		this.setChanged();
		this.notifyObservers(getClients()); // notify
	}
	
	/** This function removes client from list and notify admin (client disconnected or removed by admin) **/
	@Override
	public synchronized void removeClient(String id)
	{
		clients.remove(id);
		
		this.setChanged();
		this.notifyObservers(getClients());	// notify
	}
	
	// Returns list of clients
	private List<ClientHandler> getClients()
	{
		List<ClientHandler> list = new ArrayList<>();
		
		for (ClientHandler ch : clients.values()) 
			list.add(ch);
		
		return list;
	}

	/** This function adds new Player (Acoount) to db **/
	@Override
	public synchronized void addPlayerDB(Player p)
	{
		manager.addPlayer(p);
		listPlayers = manager.getAllPlayers();
	}

	/** This function adds new Score to db **/
	@Override
	public synchronized void addScoreDB(PlayersRecords pr)
	{
		manager.addRecord(pr);
	}

	/** This function adds new Level to db **/
	@Override
	public synchronized void addLevelDB(Level l)
	{
		if(!manager.LevelisExist(l.getLevelName()))
		{
			String location = "C:/Users/amir/Desktop/Eclipse Workspace/SokobanServer/Levels/"+l.getLevelName()+".txt";

			try
			{
				save.savelevel((new FileOutputStream(new File(location))), l);
			}

			catch (Exception e)
			{
				System.out.println("Cannot save new level file");
			}

			l.setLevelLocation(location);
			manager.addLevel(l);
		}
	}

	/** This function returns sorted Level Scores from db (Scores sort by steps) **/
	@Override
	public synchronized List<PlayersRecords> sortLevelScoresSteps(String levelName)
	{
		return manager.sortLevelScoresBySteps(levelName);
	}

	/** This function returns sorted Level Scores from db (Scores sort by time) **/
	@Override
	public synchronized List<PlayersRecords> sortLevelScoresTime(String levelName)
	{
		return manager.sortLevelScoresByTime(levelName);
	}

	/** This function returns chosen Player scores **/
	@Override
	public synchronized List<PlayersRecords> getSpecificPlayerScores(String playerId)
	{
		return manager.getPlayerScores(playerId);
	}

	/** This function returns all Players (Accounts) from db **/
	@Override
	public synchronized List<Player> getPlayers()
	{
		return this.listPlayers;
	}

	/** This function closes connection with sql server (db) **/
	@Override
	public void close()
	{
		manager.close();
	}

	/** This function returns all Levels from db **/
	@Override
	public synchronized List<Level> getLevels()
	{
		
		return manager.getAllLevels();
	}

	/** This function returns chosen Level from db **/
	@Override
	public synchronized Level LoadLevelDB(String levelName)
	{
		Level l = manager.getLevel(levelName);
		Level newL = null;
		try
		{
			newL = load.loadlevel(new FileInputStream(l.getLevelLocation()));
		}

		catch (ClassNotFoundException | IOException e)
		{
			System.out.println("Cannot load level from db");
		}

		return newL;
	}

	/** This function calculates Solution - using SokobanSolver jar **/
	public synchronized List<String> calcSolution(Level level)
	{
		return Run.runAlgorithem(level);
	}

	/** This function returns Level Solution (from webserver or after using calcSolution function **/
	@SuppressWarnings("unused")
	@Override
	public synchronized List<String> getLevelSolution(String levelname)
	{
		String webS = getSolutionFromService(levelname);
		List<String> solution = null;

		if(webS == null)
		{
			solution = calcSolution(LoadLevelDB(levelname));
			
			webTarget = client.target(url);
		    MultivaluedMap<String, String> formData = new MultivaluedHashMap<String, String>();
		    formData.add("name", levelname);
		    formData.add("solution", solution.toString());
		    Response response = webTarget.request().post(Entity.form(formData));
		}
		
		else
			solution = new LinkedList<String>(Arrays.asList(webS.split("\n|\\)")));

		solution.removeIf(s -> s.length() < 4);
		return solution;
	}
	
	// This function connects with webserver (Solution Server) and asks for Solution **/
	private String getSolutionFromService(String name) 
	{
		webTarget = client.target(url+name);	
		Response response = webTarget.request(MediaType.TEXT_PLAIN).get(Response.class);
		
		if (response.getStatus() == 200) // has solution
		{
			String solution = response.readEntity(new GenericType<String>() {});       
			return solution;
		} 
		
		else // has not solution
			return null;
	}

	/** This function returns first direction from solution **/
	@Override
	public synchronized String getLevelHint(Level level)
	{
		List<String> solution = calcSolution(level);
		String[] arr = solution.get(0).split("\n|\\)");

		return arr[0];
	}

	/** This function close specific client connection **/
	@Override
	public synchronized void closeClient(String clientId) 
	{
		clients.get(clientId).close();
	}
}
