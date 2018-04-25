package controller.commands;

import java.io.FileNotFoundException;
import java.io.IOException;

import model.Model;
import view.View;

/** SaveLevelCommand - this class gets location from View, creates a new file and saves Level **/
public class SaveLevelCommand extends AbsCommand
{
	// C'tor
	public SaveLevelCommand(Model m, View v)
	{
		super(m, v);
	}

	/** This function gets location from View, creates a new file and saves Level **/
	@Override
	public void execute() throws ClassNotFoundException, FileNotFoundException, IOException, Exception
	{
		String direction = params.get(0);
		model.SaveLevel(direction);
	}
}


