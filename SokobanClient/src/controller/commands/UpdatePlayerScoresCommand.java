package controller.commands;

import java.io.FileNotFoundException;
import java.io.IOException;

import model.Model;
import view.View;

/** UpdatePlayerScores - this class tells View to display Player's Scores **/
public class UpdatePlayerScoresCommand extends AbsCommand
{
	// C'tor
	public UpdatePlayerScoresCommand(Model m, View v)
	{
		super(m, v);
	}

	/** This function tells View to display Player's Scores **/
	@Override
	public void execute() throws ClassNotFoundException, FileNotFoundException, IOException, Exception
	{
		view.setPlayerScores(model.getSpecificPlayerScores());
	}
}
