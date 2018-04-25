package model;

import java.util.List;

import common.Level;
import model.db.Player;
import model.db.PlayersRecords;
import viewmodel.ClientHandler;

public interface Model 
{
	public void addPlayerDB(Player p);
	public void addScoreDB(PlayersRecords pr);
	public void addLevelDB(Level l);
	public Level LoadLevelDB(String levelName);
	public List<PlayersRecords> sortLevelScoresSteps(String levelName);
	public List<PlayersRecords> sortLevelScoresTime(String levelName);
	public List<PlayersRecords> getSpecificPlayerScores(String playerId);
	public List<Player> getPlayers();
	public List<Level> getLevels();
	
	public String getLevelHint(Level level);
	public List<String> getLevelSolution(String levelname);
	public void closeClient(String clientId);
	
	public void addClient(ClientHandler ch);
	public void removeClient(String id);
	
	public void close();
}
