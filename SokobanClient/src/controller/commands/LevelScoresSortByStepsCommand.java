package controller.commands;

import java.io.FileNotFoundException;
import java.io.IOException;

import model.Model;
import view.View;

/** LevelScoresSortByStepsCommand - this class sorts Level Scores by steps **/
public class LevelScoresSortByStepsCommand extends AbsCommand
{
	// C'tor
	public LevelScoresSortByStepsCommand(Model m, View v)
	{
		super(m, v);
	}

	/** This functin sorts Level Scores by steps **/
	@Override
	public void execute() throws ClassNotFoundException, FileNotFoundException, IOException, Exception
	{
		model.sortLevelScoresSteps(params.remove(0));
	}
}
