package controller.commands;

import java.io.FileNotFoundException;
import java.io.IOException;

import model.Model;
import view.View;

/** AddPlayerDBCommand - this class adds new Player to the Database **/
public class AddPlayerDBCommand extends AbsCommand
{
	// C'tor
	public AddPlayerDBCommand(Model m, View v)
	{
		super(m, v);
	}

	/** This function adds new Player to the db**/
	@Override
	public void execute() throws ClassNotFoundException, FileNotFoundException, IOException, Exception
	{
		model.addPlayerDB(params);
	}
}
