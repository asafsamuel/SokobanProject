package model.data;

@SuppressWarnings("serial")
public class Wall extends Cell
{
	// C'tors
	public Wall() 
	{
		this.setFlag(0);
	}
	
	/** Returns Wall class name **/
	@Override
	public String GetType()
	{
 		return "Wall";
	}
	
	@Override
	public boolean CanMove() 
	{
		return false;
	}
}
