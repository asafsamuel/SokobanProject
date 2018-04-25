package model.data;

@SuppressWarnings("serial")
public class Box extends Cell
{
	// C'tor
	public Box() 
	{
		this.setFlag(0);
	}
	
	/** This function checks if the Box is placed on a Target **/
	public boolean InPosition()
	{
		if(this.getFlag() == 1)
			return true;
		
		else
			return false;
	}
	
	/** Returns Box class name **/
	@Override
	public String GetType() 
	{
		return "Box";
	}
	
	@Override
	public boolean CanMove() 
	{
		return true;
	}
}
