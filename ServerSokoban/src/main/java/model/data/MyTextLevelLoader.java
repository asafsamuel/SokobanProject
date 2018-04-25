package model.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import common.Level;

public class MyTextLevelLoader implements LevelLoader
{
	// Variables
	int j;
	String line;

	@Override
	public Level loadlevel(InputStream is) throws IOException
	{
		Level l = new Level();
		this.j = 0;
		this.line = "";

		// Load the text-file
		BufferedReader in = new BufferedReader(new InputStreamReader(is));

		// Read first time from the file
		line = in.readLine();

		l.set_gameBoard(new ArrayList<ArrayList<Cell>>());

		// While-Loop
		while(line != null)
		{
			//cellsArray.add(new ArrayList<Cell>());
			l.get_gameBoard().add(new ArrayList<Cell>());

			// For-Loop
			for (int i = 0; i < line.length(); i++)
			{
				// Switch-Case
				switch(line.charAt(i))
				{
				case ' ':
					l.get_gameBoard().get(j).add(new Floor());
					break;

				case '#':
					l.get_gameBoard().get(j).add(new Wall());
					break;

				case '@':
					l.get_gameBoard().get(j).add(new Box());
					l.set_numOfBoxes(l.get_numOfBoxes() + 1);
					break;

				case 'A':
					Character c = new Character();
					l.get_gameBoard().get(j).add(c);
					l.set_characterX(i);
					l.set_characterY(j);
					break;

				case 'o':
					l.get_gameBoard().get(j).add(new Target());
					break;

				case '$':
					Box b = new Box();
					b.setFlag(1);
					l.get_gameBoard().get(j).add(b);
					break;

				case '&':
					Character c2 = new Character();
					c2.setFlag(1);
					l.get_gameBoard().get(j).add(c2);
					l.set_characterX(i);
					l.set_characterY(j);
					break;
				}
			}

			j++;

			// Read a line from the text-file
			line = in.readLine();
		}

		in.close();

		return l;
	}
}
