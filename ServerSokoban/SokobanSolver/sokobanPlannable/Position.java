package sokobanPlannable;

public class Position
{
	// Local Variables
	int row;
	int col;

	// C'tor
	public Position(int row, int col)
	{
		this.row = row;
		this.col = col;
	}


	// Getters and Setters
	public int getRow()
	{
		return row;
	}

	public void setRow(int row)
	{
		this.row = row;
	}

	public int getCol()
	{
		return col;
	}

	public void setCol(int col)
	{
		this.col = col;
	}

	// Override functions
	@Override
	public String toString()
	{
		return "("+row+","+col+")";
	}
	@Override
	public int hashCode()
	{
		return toString().hashCode();
	}
	@Override
	public boolean equals(Object obj)
	{
		if(!(obj instanceof Position))
			return false;

		Position p = (Position)obj;

		return ((row == p.row) && (col == p.col));
	}
}
