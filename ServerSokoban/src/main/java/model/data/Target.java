package model.data;

@SuppressWarnings("serial")
public class Target extends Cell
{
	//C'tor
	public Target() 
	{
		super.setFlag(1);
	}
	
	/** Returns Target class name **/
	@Override
	public String GetType() 
	{
		return "Target";
	}
	
	@Override
	public void setFlag(int x)
	{
		return; // Do nothing!!!
	}

	@Override
	public boolean CanMove() 
	{
		return false;
	}
}
