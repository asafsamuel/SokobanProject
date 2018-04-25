package sokobanSearchable;

import java.util.ArrayList;

import plannable.AND;
import plannable.Action;
import plannable.NOT;
import plannable.Predicate;
import run.PrioryPredicate;
import run.SokobanPredicate;
import searchables.Searchable;
import searchers.BFS;
import searchers.Searcher;
import searchers.Solution;
import sokobanPlannable.KnowledgeBase;
import sokobanPlannable.Position;

public class SearchableController
{
	// Local Variables
	Searchable<CharArrays> searchable;
	Searcher<CharArrays> searcher;

	// C'tor
	public SearchableController()
	{
		searcher = new BFS<>();
	}

	// Set function
	public void setSearchable(KnowledgeBase kb, int sizeA, int sizeB, Position start, Position end, String moveable, Position hideBox, Position showBox)
	{
		char[][] state = new char[sizeA][sizeB];
		ArrayList<Position> notClearP = new ArrayList<>();

		// Init array
		for (int i = 0; i < sizeA; i++)
		{
			for (int j = 0; j < sizeB; j++)
			{
				state[i][j] = ' ';
			}
		}

		for (Predicate predicate : kb.getPredicates())
		{
			if(!predicate.getType().startsWith("ClearPath"))
			{
				if(predicate.getType().equals("Not"))
				{
					NOT n = (NOT)predicate;
					notClearP.add((Position) n.getPredicate().getArgs()[1]);
				}

				else
				{
					SokobanPredicate sokobanPredicate = (SokobanPredicate) predicate;
					Position p;

					switch (sokobanPredicate.getType())
					{
					case "WallAt":
						p = (Position) sokobanPredicate.getArgs()[1];
						state[p.getRow()][p.getCol()] = '#';
						break;

					case "BoxAt":
						p = (Position) sokobanPredicate.getArgs()[1];
						state[p.getRow()][p.getCol()] = '#';
						break;
					}
				}
			}
		}

		for (Position position : notClearP)
		{
			state[position.getRow()][position.getCol()] = '#';
		}

		if(moveable.equals("Player"))
		{
			state[hideBox.getRow()][hideBox.getCol()] = ' ';
			state[showBox.getRow()][showBox.getCol()] = '#';
		}

		searchable = new SokobanSearchable(new CharArrays(state), start, end, moveable);
	}

	public Action getActions(Position start, Position end, String moveable, Position playerStart, Predicate SourceP)
	{
		Solution s = searcher.search(searchable);
		int count = 0;

		if(s == null)
			return null;

		if(moveable.equals("Box"))
		{
			Position pEnd = null;
			Position temp = new Position(playerStart.getRow(),playerStart.getRow());

			switch (s.getActions().get(0).getName())
			{
			case "Move up":
				pEnd = new Position(start.getRow()+1, start.getCol());
				break;

			case "Move down":
				pEnd = new Position(start.getRow()-1, start.getCol());
				break;

			case "Move left":
				pEnd = new Position(start.getRow(), start.getCol()+1);
				break;

			case "Move right":
				pEnd = new Position(start.getRow(), start.getCol()-1);
				break;
			}

			ArrayList<Predicate> predicates = new ArrayList<Predicate>();
			predicates.add(new PrioryPredicate("ClearPathPlayer", count, playerStart ,pEnd, start,start));
			count++;

			String first = s.getActions().get(0).getName();

			Position st = new Position(pEnd.getRow(), pEnd.getCol());
			Position e = new Position(start.getRow(), start.getCol());

			for (searchables.Action action : s.getActions())
			{
				if(!action.getName().equals(first))
				{
					switch (action.getName())
					{
					case "Move up":
					{
						Position newp = new Position(e.getRow()+1, e.getCol());
						predicates.add(new PrioryPredicate("ClearPathPlayer", count, new Position(st.getRow(),st.getCol()) ,newp, start, new Position(e.getRow(), e.getCol())));
						count++;

						first = "Move up";
						st.setRow(e.getRow());
						st.setCol(e.getCol());

						temp.setRow(e.getRow());
						temp.setCol(e.getCol());

						e.setRow(e.getRow()-1);
						break;
					}

					case "Move down":
					{
						Position newp = new Position(e.getRow()-1, e.getCol());
						predicates.add(new PrioryPredicate("ClearPathPlayer", count, new Position(st.getRow(),st.getCol()) ,newp, start, new Position(e.getRow(), e.getCol())));
						count++;

						first = "Move down";
						st.setRow(e.getRow());
						st.setCol(e.getCol());

						temp.setRow(e.getRow());
						temp.setCol(e.getCol());

						e.setRow(e.getRow()+1);
						break;
					}

					case "Move right":
					{
						Position newp = new Position(e.getRow(), e.getCol()-1);
						predicates.add(new PrioryPredicate("ClearPathPlayer", count, new Position(st.getRow(),st.getCol()) ,newp, start, new Position(e.getRow(), e.getCol())));
						count++;

						first = "Move right";
						st.setRow(e.getRow());
						st.setCol(e.getCol());

						temp.setRow(e.getRow());
						temp.setCol(e.getCol());

						e.setCol(e.getCol()+1);
						break;
					}

					case "Move left":
					{
						Position newp = new Position(e.getRow(), e.getCol()+1);
						predicates.add(new PrioryPredicate("ClearPathPlayer", count, new Position(st.getRow(),st.getCol()) ,newp, start, new Position(e.getRow(), e.getCol())));
						count++;

						first = "Move left";
						st.setRow(e.getRow());
						st.setCol(e.getCol());

						temp.setRow(e.getRow());
						temp.setCol(e.getCol());

						e.setCol(e.getCol()+1);
						break;
					}
					}
				}

				else
				{
					switch (action.getName())
					{
					case "Move up":
					{
						temp.setRow(e.getRow());
						temp.setCol(e.getCol());
						st.setRow(st.getRow()-1);
						e.setRow(e.getRow()-1);
						break;
					}

					case "Move down":
					{
						temp.setRow(e.getRow());
						temp.setCol(e.getCol());
						st.setRow(st.getRow()+1);
						e.setRow(e.getRow()+1);
						break;
					}

					case "Move right":
					{
						temp.setRow(e.getRow());
						temp.setCol(e.getCol());
						st.setCol(st.getCol()+1);
						e.setCol(e.getCol()+1);
						break;
					}

					case "Move left":
					{
						temp.setRow(e.getRow());
						temp.setCol(e.getCol());
						st.setCol(st.getCol()-1);
						e.setCol(e.getCol()-1);
						break;
					}
					}
				}

			}

			AND preC = new AND();
			AND effects = new AND();
			effects.updatePredicate(new Predicate("ClearPathBox", start, end));

			while(!predicates.isEmpty())
			{
				//Predicate p = predicates.remove(predicates.size()-1);
				PrioryPredicate p = (PrioryPredicate) predicates.remove(0);
				preC.updatePredicate(p);
				effects.updatePredicate(p);
			}

			effects.updatePredicate(new NOT(new SokobanPredicate("PlayerAt", "", playerStart)));
			effects.updatePredicate(new SokobanPredicate("PlayerAt", "", temp));

			return new SolutionAction(s,preC, effects);
		}

		else	// player
		{
			AND precon = new AND();
			AND effects = new AND(SourceP);

			return new SolutionAction(s,precon, effects);
		}
	}
}
