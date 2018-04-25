package controller.commands;

import java.io.FileNotFoundException;
import java.io.IOException;

import model.Model;
import view.View;

/** ChosenPlayerScoresCommand - this class sets model's PlayerScores variable **/
public class ChosenPlayerScoresCommand extends AbsCommand
{
	// C'tor
	public ChosenPlayerScoresCommand(Model m, View v)
	{
		super(m, v);
	}

	/** This fucntion sets model's PlayerScores variable **/
	@Override
	public void execute() throws ClassNotFoundException, FileNotFoundException, IOException, Exception
	{
		model.setSpecificPlayerScores(params.remove(0));
	}
}
