package sokobanSearchable;

import java.util.HashMap;

import searchables.Action;
import searchables.Searchable;
import searchables.State;
import sokobanPlannable.Position;

public class SokobanSearchable implements Searchable<CharArrays>
{
	// Local Varaibles
	LevelState initState;
	LevelState goal;
	String type;

	// C'tor
	public SokobanSearchable(CharArrays board, Position start, Position end, String moveable)
	{
		CharArrays a1 = cloneArray(board);
		CharArrays a2 = cloneArray(board);

		a1.setIndex(start.getRow(), start.getCol(), 'x');

		a2.setIndex(start.getRow(), start.getCol(), ' ');
		a2.setIndex(end.getRow(), end.getCol(), 'x');

		initState = new LevelState(a1);
		goal = new LevelState(a2);
		type = moveable;
	}

	@Override
	public State<CharArrays> getInitialState()
	{
		return initState;
	}

	@Override
	public State<CharArrays> getGoalState()
	{
		return goal;
	}

	@Override
	public HashMap<Action, State<CharArrays>> getAllPossibleMoves(State<CharArrays> state)
	{
		HashMap<Action, State<CharArrays>> neighbors = new HashMap<>();

		// Find player\box location
		Position me = findMe(state);

		CharArrays possible1;
		CharArrays possible2;

		if(type.equals("Box"))
		{
			if((state.getState().getArrays()[me.getRow()+1][me.getCol()] == ' ') && (state.getState().getArrays()[me.getRow()-1][me.getCol()] == ' '))
			{
				possible1 = cloneArray(state.getState());
				possible2 = cloneArray(state.getState());

				possible1.setIndex(me.getRow(),me.getCol(),' ');
				possible1.setIndex(me.getRow()+1,me.getCol(),'x');

				possible2.setIndex(me.getRow(),me.getCol(),' ');
				possible2.setIndex(me.getRow()-1,me.getCol(),'x');

				neighbors.put(new Action("Move down"), new State<CharArrays>(possible1));
				neighbors.put(new Action("Move up"), new State<CharArrays>(possible2));
			}

			if((state.getState().getArrays()[me.getRow()][me.getCol()+1] == ' ') && (state.getState().getArrays()[me.getRow()][me.getCol()-1] == ' '))
			{
				possible1 = cloneArray(state.getState());
				possible2 = cloneArray(state.getState());

				possible1.setIndex(me.getRow(),me.getCol(),' ');
				possible1.setIndex(me.getRow(),me.getCol()+1,'x');

				possible2.setIndex(me.getRow(),me.getCol(),' ');
				possible2.setIndex(me.getRow(),me.getCol()-1,'x');

				neighbors.put(new Action("Move right"), new State<CharArrays>(possible1));
				neighbors.put(new Action("Move left"), new State<CharArrays>(possible2));
			}
		}

		else	// player
		{
			// Right
			if(state.getState().getArrays()[me.getRow()][me.getCol()+1] == ' ')
			{
				possible1 = cloneArray(state.getState());

				possible1.setIndex(me.getRow(),me.getCol(),' ');
				possible1.setIndex(me.getRow(),me.getCol()+1,'x');
				neighbors.put(new Action("Move right"), new State<CharArrays>(possible1));
			}

			// Left
			if(state.getState().getArrays()[me.getRow()][me.getCol()-1] == ' ')
			{
				possible1 = cloneArray(state.getState());

				possible1.setIndex(me.getRow(),me.getCol(),' ');
				possible1.setIndex(me.getRow(),me.getCol()-1,'x');
				neighbors.put(new Action("Move left"), new State<CharArrays>(possible1));
			}

			// Down
			if(state.getState().getArrays()[me.getRow()+1][me.getCol()] == ' ')
			{
				possible1 = cloneArray(state.getState());

				possible1.setIndex(me.getRow(),me.getCol(),' ');
				possible1.setIndex(me.getRow()+1,me.getCol(),'x');
				neighbors.put(new Action("Move down"), new State<CharArrays>(possible1));
			}

			// Up
			if(state.getState().getArrays()[me.getRow()-1][me.getCol()] == ' ')
			{
				possible1 = cloneArray(state.getState());

				possible1.setIndex(me.getRow(),me.getCol(),' ');
				possible1.setIndex(me.getRow()-1,me.getCol(),'x');
				neighbors.put(new Action("Move up"), new State<CharArrays>(possible1));
			}
		}

		return neighbors;
	}

	private Position findMe(State<CharArrays> state)
	{
		for (int i = 0; i < state.getState().getArrays().length; i++)
		{
			for (int j = 0; j < state.getState().getArrays()[i].length; j++)
			{
				if(state.getState().getArrays()[i][j] == 'x')
				{
					Position p = new Position(i, j);
					return p;
				}
			}
		}

		return null;
	}

	private CharArrays cloneArray(CharArrays source)
	{
		char[][] newA = new char[source.getArrays().length][source.getArrays()[0].length];

		for (int i = 0; i < source.getArrays().length; i++)
		{
			for (int j = 0; j < source.getArrays()[i].length; j++)
			{
				newA[i][j] = source.getArrays()[i][j];
			}
		}

		return new CharArrays(newA);
	}
}
