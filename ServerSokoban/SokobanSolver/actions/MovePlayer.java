package actions;

import plannable.AND;
import plannable.Action;
import plannable.NOT;
import plannable.Predicate;
import run.SokobanPredicate;
import sokobanPlannable.Position;

public class MovePlayer extends Action
{
	// C'tor
	public MovePlayer(Position start, Position end)
	{
		super("MovePlayer", new AND(new SokobanPredicate("ClearAt","",end),new SokobanPredicate("PlayerAt", "", start), new Predicate("clearSokoPath", start, end)), new AND(new SokobanPredicate("PlayerAt","",end), new NOT(new SokobanPredicate("PlayerAt", "", start))), start, end);
	}
}
