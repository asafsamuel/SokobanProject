package model.db;

import java.io.Serializable;

@SuppressWarnings("serial")
public class PlayersRecordsKey implements Serializable
{
	// Local Variables
	private int PlayerId;
	private String LevelName;

	// C'tors
	public PlayersRecordsKey()
	{
	}
	public PlayersRecordsKey(int PlayerId, String LevelName)
	{
		this.LevelName = LevelName;
		this.PlayerId = PlayerId;
	}

	// Getters and Setters
	public int getPlayerId()
	{
		return PlayerId;
	}
	public void setPlayerId(int playerId)
	{
		PlayerId = playerId;
	}
	public String getLevelName()
	{
		return LevelName;
	}
	public void setLevelName(String levelName)
	{
		LevelName = levelName;
	}

	// Overrides Function
	@Override
	public int hashCode()
	{
		return LevelName.hashCode()*31 + PlayerId;
	}

	@Override
	public boolean equals(Object obj)
	{
		PlayersRecordsKey key = (PlayersRecordsKey)obj;

		return this.LevelName.equals(key.LevelName) && this.PlayerId == key.PlayerId;
	}
}
