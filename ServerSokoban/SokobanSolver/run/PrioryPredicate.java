package run;

import plannable.Predicate;

public class PrioryPredicate extends Predicate implements Comparable<PrioryPredicate>
{
	int priorityNum;

	public PrioryPredicate(String type, int x, Object... objects)
	{
		super(type, objects);
		priorityNum = x;
	}

	@Override
	public int compareTo(PrioryPredicate o)
	{
		return o.priorityNum-priorityNum;
	}


}
