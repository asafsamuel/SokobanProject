package sokobanSearchable;

import searchables.State;

public class LevelState extends State<CharArrays>
{
	public LevelState(CharArrays state)
	{
		super(state);
	}

	@Override
	public int hashCode()
	{
		return toString().hashCode();
	}
	@Override
	public String toString()
	{
		return getState().toString();
	}
	@Override
	public boolean equals(Object o)
	{
		LevelState ls = (LevelState)o;
		return getState().equals(ls.getState());
	}
}
