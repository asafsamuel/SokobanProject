package run;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import plannable.AND;
import plannable.Predicate;
import planner.HeuristicMethods;

public class HeuMethod implements HeuristicMethods
{
	@Override
	public List<Predicate> decomposeGoal(AND goal)
	{
		if(goal == null)
			return null;

		List<Predicate> lists = new ArrayList<Predicate>();
		PriorityQueue<PrioryPredicate> queue = new PriorityQueue<>();
		boolean flag = false;

		for (Predicate predicate : goal.getPredicates())
		{
			if(predicate instanceof PrioryPredicate)
			{
				queue.add((PrioryPredicate)predicate);
				flag = true;
			}

			else
				lists.add(predicate);
		}

		if(flag)
		{
			while(!queue.isEmpty())
				lists.add(queue.remove());
		}

		return lists;
	}
}
