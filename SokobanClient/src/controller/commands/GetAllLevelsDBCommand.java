package controller.commands;

import java.io.FileNotFoundException;
import java.io.IOException;

import model.Model;
import view.View;

/** GetAllLevelsDBCommand - this class sends List of Levels to View and tells it to display it.
 *  The List is taken from the Model. **/
public class GetAllLevelsDBCommand extends AbsCommand 
{
	// C'tor
	public GetAllLevelsDBCommand(Model m, View v) 
	{
		super(m, v);
	}

	/** this function sends List of Levels to View and tells it to display it.
	 *  The List is taken from the Model. **/
	@Override
	public void execute() throws ClassNotFoundException, FileNotFoundException, IOException, Exception 
	{
		view.setLevelsList(model.getLevels());
	}
}
