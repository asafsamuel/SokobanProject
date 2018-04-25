package controller.commands;

import java.io.FileNotFoundException;
import java.io.IOException;

import model.Model;
import view.View;

/** GetHintCommand - this class sends to server a request for hint (String of the next move) **/
public class GetHintCommand extends AbsCommand 
{
	// C'tor
	public GetHintCommand(Model m, View v) 
	{
		super(m, v);
	}

	/** This function sends to server a request for hint (String of the next move) **/
	@Override
	public void execute() throws ClassNotFoundException, FileNotFoundException, IOException, Exception 
	{
		model.sendHintRequest();
	}
}
