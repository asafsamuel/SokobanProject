package controller.commands;

import java.io.FileNotFoundException;
import java.io.IOException;

import model.Model;
import view.View;

/** EndGameCommand - this class sets the View's variable MoveFlag to false.
 *  It tells View not to get Player movement any more.
 */
public class EndGameCommand extends AbsCommand 
{
	// C'tor
	public EndGameCommand(Model m, View v) 
	{
		super(m, v);
	}

	/** This function tells View not to get Player movement any more **/
	@Override
	public void execute() throws ClassNotFoundException, FileNotFoundException, IOException, Exception 
	{
		this.view.setMoveFlag(false);
	}
}
