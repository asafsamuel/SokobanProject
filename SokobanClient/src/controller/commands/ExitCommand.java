package controller.commands;

import model.Model;
import view.View;

/** ExitCommand - this class closes all thread and services in the View and the Model **/
public class ExitCommand extends AbsCommand
{
	// C'tor
	public ExitCommand(Model m, View v)
	{
		super(m, v);
	}

	/** This function closes all thread and services in the View and the Model **/
	@Override
	public void execute()
	{
		model.close();
		view.close();
	}
}
