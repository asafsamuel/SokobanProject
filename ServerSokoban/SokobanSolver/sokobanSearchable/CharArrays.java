package sokobanSearchable;

public class CharArrays
{
	// Local Variables
	char[][] arrays;

	// C'tor
	public CharArrays(char[][] arrays)
	{
		this.arrays = arrays;
	}

	// Getters and Setters
	public char[][] getArrays()
	{
		return arrays;
	}
	public void setArrays(char[][] arrays)
	{
		this.arrays = arrays;
	}
	public void setIndex(int x, int y, char c)
	{
		arrays[x][y] = c;
	}

	// Override function
	@Override
	public int hashCode()
	{
		return toString().hashCode();
	}
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();

		for (char[] array : arrays)
		{
			for (char character : array)
			{
				sb.append(character);
			}

			sb.append("\n");
		}

		return sb.toString();
	}
	@Override
	public boolean equals(Object o)
	{
		CharArrays ls = (CharArrays)o;

		if(arrays.length != ls.arrays.length)
			return false;

		for (int i = 0; i < arrays.length; i++)
		{
			if(arrays[i].length != ls.arrays[i].length)
				return false;

			for (int j = 0; j < arrays[i].length; j++)
			{
				if(arrays[i][j] != ls.arrays[i][j])
					return false;
			}
		}

		return true;
	}

}
