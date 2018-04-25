package actions;

import plannable.AND;
import plannable.Action;
import plannable.NOT;
import plannable.Predicate;
import run.SokobanPredicate;
import sokobanPlannable.Position;

public class Place extends Action
{
	public Place(Position box, Position target, String boxId)
	{
		super("Place", new AND(new SokobanPredicate("ClearAt","",target), new SokobanPredicate("BoxAt", boxId, box), new Predicate("ClearPathBox", box, target)), new AND(new SokobanPredicate("BoxAt", boxId, target), new NOT(new SokobanPredicate("ClearAt", "", target)), new NOT(new SokobanPredicate("BoxAt", boxId, box))), box, target, boxId);
	}

	@Override
	public String toString()
	{
		return "";
	}
}
