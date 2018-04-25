package sokobanPlannable;

import plannable.AND;
import plannable.NOT;
import plannable.Predicate;

public class KnowledgeBase extends AND
{
	// C'tor
	public KnowledgeBase(Predicate... predicates)
	{
		super(predicates);
	}

	@Override
	public void updatePredicate(Predicate p)
	{
		super.updatePredicate(p);

		if(p.getType().equals("Not"))
		{
			NOT n = (NOT)p;
			
			if(!n.getPredicate().getType().equals("ClearAt"))
				super.removePredicate(p);
		}
	}
}
