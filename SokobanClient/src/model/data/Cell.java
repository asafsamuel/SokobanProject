package model.data;

import java.io.Serializable;

@SuppressWarnings("serial")
public abstract class Cell implements Serializable
{
	// Local Variable
	 int _flag;

	 // C'tor
	 public Cell()
	 {
		 this._flag=0;
	 }

	 // Abstracts Function
	public abstract String GetType();
	public abstract boolean CanMove();

	// Getters and Setters
	public int getFlag()
	{
		return this._flag;
	}
	public void setFlag(int x)
	{
		this._flag = x;
	}

}
