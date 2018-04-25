package controller.commands;

import java.io.FileNotFoundException;
import java.io.IOException;

/*** Interface Command - has function execute for changing View, Model or passing parameters when needed**/
public interface Command
{
	public void execute() throws ClassNotFoundException, FileNotFoundException, IOException, Exception;
}
