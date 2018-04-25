package controller.commands;

import java.io.FileNotFoundException;
import java.io.IOException;

import model.Model;
import view.View;

/** GetAllPlayersDBCommand - this class sends List of Players to View and tells it to display it.
 *  The List is taken from the Model. **/
public class GetAllPlayersDBCommand extends AbsCommand
{
	// C'tor
	public GetAllPlayersDBCommand(Model m, View v)
	{
		super(m, v);
	}

	/** This function sends List of Players to View and tells it to display it.
	 *  The List is taken from the Model. **/
	@Override
	public void execute() throws ClassNotFoundException, FileNotFoundException, IOException, Exception
	{
		view.setPlayersList(model.getPlayers());
	}
}
