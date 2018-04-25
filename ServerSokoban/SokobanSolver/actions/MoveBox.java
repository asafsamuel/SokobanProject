package actions;

import plannable.AND;
import plannable.Action;
import plannable.NOT;
import plannable.Predicate;
import run.SokobanPredicate;
import sokobanPlannable.Position;

public class MoveBox extends Action
{
	public MoveBox(Position start, Position end, String boxId)
	{
		super("MoveBox", new AND(new SokobanPredicate("ClearAt","",end), new SokobanPredicate("BoxAt", boxId, start), new Predicate("ClearPathBox", start, end)), new AND(new SokobanPredicate("BoxAt", boxId, end), new NOT(new SokobanPredicate("ClearAt", "", end)), new NOT(new SokobanPredicate("BoxAt", boxId, start))), start, end);
	}
}
