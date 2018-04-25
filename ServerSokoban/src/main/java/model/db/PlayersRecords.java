package model.db;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@SuppressWarnings("serial")
@Entity(name="PlayersRecords")
public class PlayersRecords implements Serializable
{
	// Local Variables
	@EmbeddedId
	PlayersRecordsKey key;
	@Column(name="Steps")
	int PlayerSteps;
	@Column(name="Timer")
	int PlayerTime;
	@Column(name="PlayerName")
	String PlayerName;

	// C'tors
	public PlayersRecords()
	{
		key = new PlayersRecordsKey();
	}
	public PlayersRecords(String LevelName, int PlayerId, int PlayerSteps, int PlayerTime, String PlayerName)
	{
		key = new PlayersRecordsKey(PlayerId, LevelName);
		this.PlayerSteps = PlayerSteps;
		this.PlayerTime = PlayerTime;
		this.PlayerName = PlayerName;
	}

	// Getters and Setters
	public int getPlayerId()
	{
		return key.getPlayerId();
	}
	public void setPlayrId(int PlayerId)
	{
		key.setPlayerId(PlayerId);
	}
	public String getLevelName()
	{
		return key.getLevelName();
	}
	public void setLevelName(String LevelName)
	{
		key.setLevelName(LevelName);
	}
	public int getPlayerSteps()
	{
		return PlayerSteps;
	}
	public void setPlayerSteps(int playerSteps)
	{
		PlayerSteps = playerSteps;
	}
	public int getPlayerTime()
	{
		return PlayerTime;
	}
	public void setPlayerTime(int playerTime)
	{
		PlayerTime = playerTime;
	}
	public String getPlayerName()
	{
		return PlayerName;
	}
	public void setPlayerName(String playerName)
	{
		PlayerName = playerName;
	}
}
