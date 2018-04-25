package controller.commands;

import java.io.FileNotFoundException;
import java.io.IOException;

import model.Model;
import view.View;

/*** MoveCharacterCommand - this class gets direction from View and changes Model's Level if needed **/
public class MoveCharacterCommand extends AbsCommand
{
	// C'tor
	public MoveCharacterCommand(Model m, View v)
	{
		super(m, v);
	}

	/** This function gets direction from View and changes Model's Level if needed **/
	@Override
	public void execute() throws ClassNotFoundException, FileNotFoundException, IOException, Exception
	{
		String direction = params.get(0);
		model.MoveCharacter(direction);
	}
}

