package model.policy;

import common.Level;

/*** MySokobanPolicy - this class represents the policy of movement in the game:
 *   the Player can't push 2 boxes in the same time,
 *   can't push box that is bloacked by a Wall, and can't pull a Box. **/
public class MySokobanPolicy implements SokobanPolicy
{
	/** This function check if movement is ok consider the policy **/
	@Override
	public boolean CheckPolicy(Level level , String key)
	{
		// Up
		if (key.equals("Up"))
		{
			// There is a Wall
			if(level.get_gameBoard().get(level.get_characterY() - 1).get(level.get_characterX()).GetType() == "Wall")
				return false;

			// There is a Box and After that Wall\Box
			if(level.get_gameBoard().get(level.get_characterY() - 1).get(level.get_characterX()).GetType() == "Box")
			{
				if(level.get_gameBoard().get(level.get_characterY() - 2).get(level.get_characterX()).GetType() == "Wall")
					return false;

				else if(level.get_gameBoard().get(level.get_characterY() - 2).get(level.get_characterX()).GetType() == "Box")
					return false;
			}
		}

		// Down
		else if(key.equals("Down"))
		{
			// There is a Wall
			if(level.get_gameBoard().get(level.get_characterY() + 1).get(level.get_characterX()).GetType() == "Wall")
				return false;

			// There is a Box and After that Wall\Box
			if(level.get_gameBoard().get(level.get_characterY() + 1).get(level.get_characterX()).GetType() == "Box")
			{
				if(level.get_gameBoard().get(level.get_characterY() + 2).get(level.get_characterX()).GetType() == "Wall")
					return false;

				else if(level.get_gameBoard().get(level.get_characterY() + 2).get(level.get_characterX()).GetType() == "Box")
					return false;
			}
		}

		// Left
		else if (key.equals("Left"))
		{
			// There is a Wall
			if(level.get_gameBoard().get(level.get_characterY()).get(level.get_characterX() - 1).GetType() == "Wall")
				return false;

			// There is a Box and After that Wall\Box
			if(level.get_gameBoard().get(level.get_characterY()).get(level.get_characterX() - 1).GetType() == "Box")
			{
				if(level.get_gameBoard().get(level.get_characterY()).get(level.get_characterX() - 2).GetType() == "Wall")
					return false;

				else if(level.get_gameBoard().get(level.get_characterY()).get(level.get_characterX() - 2).GetType() == "Box")
					return false;
			}
		}

		// Right
		else if(key.equals("Right"))
		{
			// There is a Wall
			if(level.get_gameBoard().get(level.get_characterY()).get(level.get_characterX() + 1).GetType() == "Wall")
				return false;

			// There is a Box and After that Wall\Box
			if(level.get_gameBoard().get(level.get_characterY()).get(level.get_characterX() + 1).GetType() == "Box")
			{
				if(level.get_gameBoard().get(level.get_characterY()).get(level.get_characterX() + 2).GetType() == "Wall")
					return false;

				else if(level.get_gameBoard().get(level.get_characterY()).get(level.get_characterX() + 2).GetType() == "Box")
					return false;
			}
		}

		else
			return false;

		return true;
	}
}
