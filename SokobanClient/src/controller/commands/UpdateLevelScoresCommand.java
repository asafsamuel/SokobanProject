package controller.commands;

import java.io.FileNotFoundException;
import java.io.IOException;

import model.Model;
import view.View;

/** UpdateLevelScoresCommand - this class tells View to display Level's Scores **/
public class UpdateLevelScoresCommand extends AbsCommand
{
	// C'tor
	public UpdateLevelScoresCommand(Model m, View v)
	{
		super(m, v);
	}

	/** This function tells View to display Level's Scores **/
	@Override
	public void execute() throws ClassNotFoundException, FileNotFoundException, IOException, Exception
	{
		view.setLevelScores(model.getLevelScores());
	}
}
