package controller.commands;

import java.io.FileNotFoundException;
import java.io.IOException;

import model.Model;
import view.View;

/** AddPlayerScoreDBCommand - this class adds new Score(PlayerRecord) to the db **/
public class AddPlayerScoreDBCommand extends AbsCommand
{
	//C'tor
	public AddPlayerScoreDBCommand(Model m, View v)
	{
		super(m, v);
	}

	/** This function adds new Score(PlayerRecord) to the db **/
	@Override
	public void execute() throws ClassNotFoundException, FileNotFoundException, IOException, Exception
	{
		model.addScoreDB(params);
	}
}
