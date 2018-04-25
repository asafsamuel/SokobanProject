package model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import common.Level;
import model.db.Player;
import model.db.PlayersRecords;

/*** Interface Model ***/
public interface Model 
{
	public Level getLevel();
	public void LoadLevel(String location, String levelName) throws ClassNotFoundException, FileNotFoundException, IOException;
	public void SaveLevel(String location) throws ClassNotFoundException, FileNotFoundException, IOException, Exception;
	public void LoadLevelFromServer(String LevelName);
	public void MoveCharacter(String direction);
	public String getDirection();
	public void getResetLevel();
	public void close();
	
	public void addPlayerDB(List<String> params);
	public void addScoreDB(List<String> params);
	public void sortLevelScoresSteps(String levelName);
	public void sortLevelScoresTime(String levelName);
	public void setSpecificPlayerScores(String playerId);
	public List<PlayersRecords> getSpecificPlayerScores();
	public List<PlayersRecords> getLevelScores();
	public List<Player> getPlayers();
	public List<Level> getLevels();
	
	public void sendHintRequest();
	public void sendSolutionRequest(String levelName);
}	
