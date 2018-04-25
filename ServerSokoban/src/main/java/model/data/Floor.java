package model.data;

@SuppressWarnings("serial")
public class Floor extends Cell
{
	// C'tor
	public Floor() 
	{
		this.setFlag(0);
	}

	/** This function returns Floor class name **/
	@Override
	public String GetType() 
	{
		return "Floor";
	}

	@Override
	public boolean CanMove() 
	{
		return false;
	}
}
