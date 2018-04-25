package sokobanSearchable;

import plannable.AND;
import plannable.Action;
import searchers.Solution;

public class SolutionAction extends Action
{
	// C'tor
	public SolutionAction(Solution s, AND preC, AND effect)
	{
		super("Solution", preC, effect, s);
	}

	@Override
	public String toString()
	{
		Solution s = (Solution) args[0];
		return s.toString();
	}
}
