package controller.commands;

import java.io.FileNotFoundException;
import java.io.IOException;

import model.Model;
import view.View;

/** DisplayMessage Command - this class tells View to display a message to the screen **/
public class DisplayMessage extends AbsCommand implements Command
{
	// C'tor
	public DisplayMessage(Model m, View v)
	{
		super(m, v);
	}

	/** This function tells View to display a message to the screen **/
	@Override
	public void execute() throws ClassNotFoundException, FileNotFoundException, IOException, Exception
	{
		this.view.displayMessage(params.remove(0));
	}
}
