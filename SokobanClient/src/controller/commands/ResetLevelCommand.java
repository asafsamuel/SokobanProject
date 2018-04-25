package controller.commands;

import java.io.FileNotFoundException;
import java.io.IOException;

import model.Model;
import view.View;

/** ResetLevelCommand - this class reset the Level variable and update both Model and View (Model's notify) **/
public class ResetLevelCommand extends AbsCommand
{
	// C'tor
	public ResetLevelCommand(Model m, View v)
	{
			super(m, v);
	}

	/** this class reset the Level variable and update both Model and View (Model's notify) **/
	@Override
	public void execute() throws ClassNotFoundException, FileNotFoundException, IOException, Exception
	{
		model.getResetLevel();
		view.setMoveFlag(true);
	}

}
