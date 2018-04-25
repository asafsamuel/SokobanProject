package common;

import java.io.Serializable;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import model.data.Box;
import model.data.Cell;
import model.data.Character;
import model.data.Floor;
import model.data.Target;
import model.data.Wall;

@SuppressWarnings("serial")
@Entity(name = "Levels")
public class Level implements Serializable 
{
	// Local Variables
	@Transient
	ArrayList<ArrayList<Cell>> _gameBoard;
	@Transient
	int _numOfSteps;
	@Transient
	int _BoxesNotOnTargets;
	@Transient
	int _score;
	@Transient
	int _characterX;
	@Transient
	int _characterY;

	@Id
	String LevelName;
	
	@Column(name = "LevelLocation")
	String LevelLocation;

	// C'tors
	public Level() 
	{
		this._score = 0;
		this._gameBoard = null;
		this._BoxesNotOnTargets = 0;
		this._numOfSteps = 0;
		this._characterX = 0;
		this._characterY = 0;
	}
	public Level(String LevelName) 
	{
		this._score = 0;
		this._gameBoard = null;
		this._BoxesNotOnTargets = 0;
		this._numOfSteps = 0;
		this._characterX = 0;
		this._characterY = 0;
		this.LevelName = LevelName;
		this.LevelLocation = "";
	}
	// Copy C'tor
	public Level(Level l) 
	{
		this._score = l.get_score();
		this._gameBoard = new ArrayList<ArrayList<Cell>>();
		this._BoxesNotOnTargets = l.get_numOfBoxes();
		this._numOfSteps = l.get_numOfSteps();
		this._characterX = l.get_characterX();
		this._characterY = l.get_characterY();
		this.LevelLocation = l.getLevelLocation();

		for (ArrayList<Cell> c : l.get_gameBoard()) 
		{
			ArrayList<Cell> newArrc = new ArrayList<Cell>();
			this._gameBoard.add(newArrc);

			for (Cell cell : c) {
				switch (cell.GetType()) {
				case "Floor":
					newArrc.add(new Floor());
					break;

				case "Wall":
					newArrc.add(new Wall());
					break;

				case "Target":
					newArrc.add(new Target());
					break;

				case "Character":
					Character a = new Character();
					a.setFlag(cell.getFlag());
					newArrc.add(a);
					break;

				case "Box":
					Box b = new Box();
					b.setFlag(cell.getFlag());
					newArrc.add(b);
					break;
				}
			}
		}

		this.LevelName = l.LevelName;
	}

	// Getters and Setters
	public ArrayList<ArrayList<Cell>> get_gameBoard() 
	{
		return _gameBoard;
	}
	public void set_gameBoard(ArrayList<ArrayList<Cell>> _gameBoard) 
	{
		this._gameBoard = _gameBoard;
	}
	public String getLevelLocation()
	{
		return LevelLocation;
	}
	public void setLevelLocation(String location)
	{
		this.LevelLocation = location;
	}
	public int get_numOfSteps() 
	{
		return _numOfSteps;
	}
	public void set_numOfSteps(int _numOfSteps) 
	{
		this._numOfSteps = _numOfSteps;
	}
	public int get_numOfBoxes() 
	{
		return _BoxesNotOnTargets;
	}
	public void set_numOfBoxes(int _numOfBoxes) 
	{
		this._BoxesNotOnTargets = _numOfBoxes;
	}
	public int get_characterX() 
	{
		return _characterX;
	}
	public void set_characterX(int _characterX) 
	{
		this._characterX = _characterX;
	}
	public int get_characterY() 
	{
		return _characterY;
	}
	public void set_characterY(int _characterY) 
	{
		this._characterY = _characterY;
	}
	public int get_score() 
	{
		return _score;
	}
	public void set_score(int _score) 
	{
		this._score = _score;
	}
	public String getLevelName() 
	{
		return LevelName;
	}
	public void setLevelName(String levelName) 
	{
		LevelName = levelName;
	}

	/** gets 2 positions and changes the position of them **/
	public void SwapCells(int index1X, int index1Y, int index2X, int index2Y) 
	{
		Cell temp = this._gameBoard.get(index2Y).get(index2X);
		temp.setFlag(this._gameBoard.get(index2Y).get(index2X).getFlag());

		// Swap with 2 Target position
		if (temp.getFlag() == 1 && this._gameBoard.get(index1Y).get(index1X).getFlag() == 1) 
		{
			this._gameBoard.get(index2Y).set(index2X, this._gameBoard.get(index1Y).get(index1X));
			this._gameBoard.get(index2Y).get(index2X).setFlag(1);
			this._gameBoard.get(index1Y).set(index1X, new Target());
		}

		// Swap with Target position (Second Cell)
		else if (temp.getFlag() == 1) 
		{
			if (this._gameBoard.get(index1Y).get(index1X).GetType() == "Box")
				this._BoxesNotOnTargets--;

			this._gameBoard.get(index2Y).set(index2X, this._gameBoard.get(index1Y).get(index1X));
			this._gameBoard.get(index2Y).get(index2X).setFlag(1);
			this._gameBoard.get(index1Y).set(index1X, new Floor());
		}

		// Swap with Target position (first Cell)
		else if (this._gameBoard.get(index1Y).get(index1X).getFlag() == 1) 
		{
			if (this._gameBoard.get(index1Y).get(index1X).GetType() == "Box")
				this._BoxesNotOnTargets++;

			this._gameBoard.get(index2Y).set(index2X, this._gameBoard.get(index1Y).get(index1X));
			this._gameBoard.get(index2Y).get(index2X).setFlag(0);
			this._gameBoard.get(index1Y).set(index1X, new Target());
		}

		// Swap 2 non-Target position
		else 
		{
			this._gameBoard.get(index2Y).set(index2X, this._gameBoard.get(index1Y).get(index1X));
			this._gameBoard.get(index1Y).set(index1X, temp);
		}
	}

	/** Returns string of level description **/
	@Override
	public String toString() 
	{
		return "Level [LevelName=" + LevelName + "]";
	}
}
