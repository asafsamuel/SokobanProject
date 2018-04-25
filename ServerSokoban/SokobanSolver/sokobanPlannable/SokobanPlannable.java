package sokobanPlannable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;

import actions.Place;
import model.data.Cell;
import plannable.AND;
import plannable.Action;
import plannable.Plannable;
import plannable.Predicate;
import run.SokobanPredicate;
import sokobanSearchable.SearchableController;

public class SokobanPlannable implements Plannable
{
	// Local Variables
	KnowledgeBase knowledgebase;
	AND goal;
	HashSet<Action> actions;
	ArrayList<Position> targets;
	HashMap<String, Position> boxes;
	SearchableController controller;
	int sizeRow;
	int sizeCol;

	// C'tor
	public SokobanPlannable(ArrayList<ArrayList<Cell>> gameBoard)
	{
		knowledgebase = new KnowledgeBase();
		goal = new AND();
		actions = new HashSet<Action>();
		controller = new SearchableController();

		targets = new ArrayList<Position>();
		boxes = new HashMap<String, Position>();

		sizeRow = gameBoard.size();
		sizeCol = 0;

		for (ArrayList<Cell> arrayList : gameBoard)
		{
			if(sizeCol < arrayList.size())
				sizeCol = arrayList.size();
		}

		initKB(gameBoard);
		initGoal();
		initAction();
	}

	// InitFunctions
	private void initKB(ArrayList<ArrayList<Cell>> gameBoard)
	{
		int countBox = 1;
		int countTar = 1;

		for (int i = 0; i < gameBoard.size(); i++)
		{
			for (int j = 0; j < gameBoard.get(i).size(); j++)
			{
				if(gameBoard.get(i).get(j).GetType().equals("Wall"))
				{
					knowledgebase.updatePredicate(new SokobanPredicate("WallAt", "", new Position(i,j)));
				}

				else if(gameBoard.get(i).get(j).GetType().equals("Character"))
				{
					if(gameBoard.get(i).get(j).getFlag() == 1)
					{
						knowledgebase.updatePredicate(new SokobanPredicate("TargetAt", "t"+countTar, new Position(i,j)));
						targets.add(new Position(i,j));
						countTar++;
					}
					
					knowledgebase.updatePredicate(new SokobanPredicate("PlayerAt", "", new Position(i,j)));
					knowledgebase.updatePredicate(new SokobanPredicate("ClearAt", "", new Position(i,j)));
				}

				else if(gameBoard.get(i).get(j).GetType().equals("Box"))
				{
					knowledgebase.updatePredicate(new SokobanPredicate("BoxAt", "b"+countBox, new Position(i,j)));
					boxes.put("b"+countBox, new Position(i,j));
					countBox++;
				}

				else if(gameBoard.get(i).get(j).GetType().equals("Target"))
				{
					knowledgebase.updatePredicate(new SokobanPredicate("TargetAt", "t"+countTar, new Position(i,j)));
					knowledgebase.updatePredicate(new SokobanPredicate("ClearAt", "", new Position(i,j)));
					targets.add(new Position(i,j));
					countTar++;
				}

				else
					knowledgebase.updatePredicate(new SokobanPredicate("ClearAt", "", new Position(i,j)));
			}
		}
	}
	private void initGoal()
	{
		for (Predicate predicate : knowledgebase.getPredicates())
		{
			if(predicate.getType().equals("TargetAt"))
			{
				goal.updatePredicate(new SokobanPredicate("BoxAt", "?", (Position)predicate.getArgs()[1]));
			}
		}
	}
	private void initAction()
	{
		for (Position target : targets)
			for (String boxId : boxes.keySet())
				actions.add(new Place(boxes.get(boxId), target, boxId));
	}

	@Override
	public AND getKnowledgeBase()
	{
		return this.knowledgebase;
	}
	@Override
	public AND getGoal()
	{
		return this.goal;
	}

	@Override
	public List<Action> getSatisfiesActions(Predicate predicate, AND knowledgeBase)
	{
		return null;
	}
	@Override
	public Action getSatisfiesAction(Predicate predicate, AND knowledgeBase)
	{
		if(predicate.getType().equals("ClearPathBox"))
		{
			controller.setSearchable(knowledgebase, sizeRow, sizeCol, (Position)predicate.getArgs()[0], (Position)predicate.getArgs()[1], "Box", null ,null);
			return controller.getActions((Position)predicate.getArgs()[0], (Position)predicate.getArgs()[1], "Box", findPlayer(knowledgeBase), null);
		}

		else if(predicate.getType().equals("ClearPathPlayer"))
		{
			controller.setSearchable(knowledgebase, sizeRow, sizeCol, (Position)predicate.getArgs()[0], (Position)predicate.getArgs()[1], "Player", (Position)predicate.getArgs()[2], (Position)predicate.getArgs()[3]);
			return controller.getActions((Position)predicate.getArgs()[0], (Position)predicate.getArgs()[1], "Player", findPlayer(knowledgeBase), predicate);
		}

		else
		{
			ArrayList<Action> possibleA = new ArrayList<Action>();

			for (Action action : actions)
			{
				if(action.getEffects().isSatisfied(predicate))
					possibleA.add(action);
			}

			if(possibleA.size() == 0 )
				return null;

			Action bestA = possibleA.get(0);
			int count = 0;
			int min = 0;

			for (Action action : possibleA)
			{
				for (Predicate predicate2 : knowledgeBase.getPredicates())
				{
					if(action.getPreConditions().isSatisfied(predicate2))
						count++;
				}

				if(min < count)
				{
					bestA = action;
					min = count;
					count = 0;
				}
			}

			return bestA;
		}
	}

	@Override
	public Stack<Predicate> getStack()
	{
		return new Stack<>();
	}

	private Position findPlayer(AND kb)
	{
		for (Predicate predicate : kb.getPredicates())
		{
			if(predicate.getType().equals("PlayerAt"))
				return (Position)((SokobanPredicate)predicate).getArgs()[1];
		}

		return null;
	}
}
