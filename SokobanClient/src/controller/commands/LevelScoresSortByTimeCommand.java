package controller.commands;

import java.io.FileNotFoundException;
import java.io.IOException;

import model.Model;
import view.View;

/** LevelScoresSortByTimeCommand - this class sorts Level Scores by time **/
public class LevelScoresSortByTimeCommand extends AbsCommand
{
	// C'tor
	public LevelScoresSortByTimeCommand(Model m, View v)
	{
		super(m, v);
	}

	/** This function sorts Level Scores by time **/
	@Override
	public void execute() throws ClassNotFoundException, FileNotFoundException, IOException, Exception
	{
		model.sortLevelScoresTime(params.remove(0));
	}
}
