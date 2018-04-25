package com.asafsamuel.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/** LevelSolution - this class represent the Solution in the db **/
@Entity(name="LevelSolution")
public class LevelSolution 
{
	// Local Variables
	@Id
	String LevelName;
	
	@Column(name="Solution")
	String solution;
	
	// C'tors
	public LevelSolution() {
	}
	public LevelSolution(String levelName, String solution) 
	{
		this.LevelName = levelName;
		this.solution = solution;
	}

	// Getters and Setters
	public String getLevelName() 
	{
		return LevelName;
	}
	public void setLevelName(String levelName) 
	{
		this.LevelName = levelName;
	}
	public String getSolution() 
	{
		return solution;
	}
	public void setSolution(String solution) 
	{
		this.solution = solution;
	}
}
