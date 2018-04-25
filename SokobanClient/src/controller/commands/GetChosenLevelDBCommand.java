package controller.commands;

import java.io.FileNotFoundException;
import java.io.IOException;

import model.Model;
import view.View;

/** GetChosenLevelDBCommand - this class gets a Level from the server and save it **/
public class GetChosenLevelDBCommand extends AbsCommand 
{
	// C'tor
	public GetChosenLevelDBCommand(Model m, View v) 
	{
		super(m, v);
	}
	
	/** This function gets a Level from the server and save it **/
	@Override
	public void execute() throws ClassNotFoundException, FileNotFoundException, IOException, Exception 
	{
		model.LoadLevelFromServer(params.remove(0));
	}
}
