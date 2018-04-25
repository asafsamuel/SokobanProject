package controller.commands;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import model.Model;
import view.View;

/** 
 * Abstract Command - The basic Command class.
 * It's includes Model, View and List of String for passing paramenters 
 * **/
public abstract class AbsCommand implements Command
{
	// Local Variables
	Model model;
	View view;
	List<String> params;

	// C'tor
	public AbsCommand(Model m, View v)
	{
		this.model = m;
		this.view = v;
	}

	/** This function set the List of String variable **/
	public void setParams(List<String> params)
	{
		this.params = params;
	}

	/** This function execute the Command - change Model or View when needed **/
	public abstract void execute() throws ClassNotFoundException, FileNotFoundException, IOException, Exception;
}