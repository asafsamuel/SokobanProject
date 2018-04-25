package model.data;

@SuppressWarnings("serial")
public class Character extends Cell
{
	//C'tor
	public Character() 
	{
		this.setFlag(0);
	}
	
	/** This function returns the Character class name **/
	@Override
	public String GetType() 
	{
		return "Character";
	}

	@Override
	public boolean CanMove() 
	{
		return true;
	}
}
