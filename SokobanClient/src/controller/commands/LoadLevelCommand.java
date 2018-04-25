package controller.commands;

import java.io.FileNotFoundException;
import java.io.IOException;

import model.Model;
import view.View;

/** LoadLevelCommand - this class gets file's location from View and load it into Model **/
public class LoadLevelCommand extends AbsCommand
{
	// C'tor
	public LoadLevelCommand(Model m, View v)
	{
		super(m, v);
	}

	/** This function gets file's location from View and load it into Model **/
	@Override
	public void execute() throws ClassNotFoundException, FileNotFoundException, IOException, Exception
	{
		model.LoadLevel(params.get(0),params.get(1));
	}
}
