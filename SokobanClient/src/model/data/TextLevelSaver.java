package model.data;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import common.Level;

/** This function gets OutputStearm and a Level and saves the Level as text file **/
public class TextLevelSaver implements LevelSaver
{
	@Override
	public void savelevel(OutputStream output, Level l) throws IOException
	{
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(output));

		// For- For loop
		for (ArrayList<Cell> arr : l.get_gameBoard())
		{
			for (Cell cell : arr)
			{
				if(cell.getFlag() != 1)
				{
					switch(cell.GetType())
					{
					case "Wall":
						out.write("#");
						break;

					case "Floor":
						out.write(" ");
						break;

					case "Character":
						out.write("A");
						break;

					case "Box":
						out.write("@");
						break;
					}
				}

				else
				{
					switch(cell.GetType())
					{
					case "Character":
						out.write("&");
						break;

					case "Target":
						out.write("o");
						break;

					case "Box":
						out.write("$");
						break;
					}
				}
			}

			out.newLine();
		}

		out.close();
	}
}
