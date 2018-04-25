package controller.commands;

import java.io.FileNotFoundException;
import java.io.IOException;

import model.Model;
import view.View;

/** GetSolutionCommand - this class sends to server a request for Level solution **/
public class GetSolutionCommand extends AbsCommand 
{
	// C'tor
	public GetSolutionCommand(Model m, View v) 
	{
		super(m, v);
	}

	/** this class sends to server a request for Level solution **/
	@Override
	public void execute() throws ClassNotFoundException, FileNotFoundException, IOException, Exception 
	{
		model.sendSolutionRequest(params.remove(0));
	}
}
