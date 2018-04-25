package model.db;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@SuppressWarnings("serial")
@Entity(name="Players")
public class Player implements Serializable
{
	// Local Variables
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	int PlayerId;
	@Column(name="PlayerName")
	String PlayerName;

	@OneToMany
	@JoinColumn(name="PlayerId")
	List<PlayersRecords> records;

	// C'tors
	public Player()
	{
		this.PlayerName = null;
		records = new ArrayList<PlayersRecords>();
	}
	public Player(String playerName)
	{
		PlayerName = playerName;
		records = new ArrayList<PlayersRecords>();
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
	public String getPlayerName()
	{
		return PlayerName;
	}
	public void setPlayerName(String playerName)
	{
		PlayerName = playerName;
	}
	public List<PlayersRecords> getCourses()
	{
		return records;
	}
	public void setCourses(List<PlayersRecords> records)
	{
		this.records = records;
	}

	// Overrides Function
	@Override
	public String toString()
	{
		return "Player [PlayerId=" + PlayerId + ", PlayerName=" + PlayerName + "]";
	}
}
