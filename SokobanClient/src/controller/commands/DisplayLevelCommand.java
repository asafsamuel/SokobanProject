package controller.commands;

import java.io.FileNotFoundException;
import java.io.IOException;

import model.Model;
import view.View;

/** DisplayLevelCommand - this class tells View to display Model Level variable **/
public class DisplayLevelCommand extends AbsCommand
{
	// C'tor
	public DisplayLevelCommand(Model m, View v)
	{
		super(m, v);
	}

	/** This function tells View to display Model Level variable **/
	@Override
	public void execute() throws ClassNotFoundException, FileNotFoundException, IOException, Exception
	{
		view.displayLevel(this.model.getLevel(),this.model.getDirection());
	}
}