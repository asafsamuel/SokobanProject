package controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import controller.commands.AbsCommand;
import controller.commands.AddPlayerDBCommand;
import controller.commands.AddPlayerScoreDBCommand;
import controller.commands.ChosenPlayerScoresCommand;
import controller.commands.DisplayLevelCommand;
import controller.commands.DisplayMessage;
import controller.commands.EndGameCommand;
import controller.commands.ExitCommand;
import controller.commands.GetAllLevelsDBCommand;
import controller.commands.GetAllPlayersDBCommand;
import controller.commands.GetChosenLevelDBCommand;
import controller.commands.GetHintCommand;
import controller.commands.GetSolutionCommand;
import controller.commands.LevelScoresSortByStepsCommand;
import controller.commands.LevelScoresSortByTimeCommand;
import controller.commands.LoadLevelCommand;
import controller.commands.MoveCharacterCommand;
import controller.commands.ResetLevelCommand;
import controller.commands.SaveLevelCommand;
import controller.commands.UpdateLevelScoresCommand;
import controller.commands.UpdatePlayerScoresCommand;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.Model;
import view.View;

/** SokobanController - The primary Controller
 * This class is part of the MVC pattern.
 * It's connected between the Model and the View.
 * It's binding variables between the View and Model (steps and timer), and gets Commands
 * both from the View and the Model and tell to MyController to keep them and execute them
 * when it can.
 */
public class SokobanController implements Observer
{
	// Local Variables
	Model model;
	View view;
	MyController controller;
	Map<String, AbsCommand> commands;
	StringProperty stepLabel;
	IntegerProperty numOfSteps;

	// C'tor
	public SokobanController(Model m, View v)
	{
		this.model = m;
		this.view = v;

		// Using bind to change immediately "numOfSteps" every move
		stepLabel = new SimpleStringProperty();
		numOfSteps = new SimpleIntegerProperty();

		view.bindSteps(stepLabel);
		view.bindStepText(numOfSteps);

		// Initialize the HashMap
		initCommands();

		// Start Controller
		controller = new MyController();
		controller.start();
	}

	// Initialize the map - insert all the Commands that exist
	private void initCommands()
	{
		commands = new HashMap<String, AbsCommand>();
		commands.put("MoveCharacter", new MoveCharacterCommand(model, view));
		commands.put("DisplayLevel", new DisplayLevelCommand(model, view));
		commands.put("SaveLevel", new SaveLevelCommand(model, view));
		commands.put("LoadLevel", new LoadLevelCommand(model, view));
		commands.put("DisplayMessage", new DisplayMessage(model, view));
		commands.put("ResetLevel", new ResetLevelCommand(model, view));
		commands.put("Exit", new ExitCommand(model, view));
		commands.put("AddPlayerDB", new AddPlayerDBCommand(model, view));
		commands.put("AddPlayerScoreDB", new AddPlayerScoreDBCommand(model, view));
		commands.put("GetAllPlayersDB", new GetAllPlayersDBCommand(model, view));
		commands.put("GetAllLevelsDB", new GetAllLevelsDBCommand(model,view));
		commands.put("GetChosenLevelDB", new GetChosenLevelDBCommand(model,view));
		commands.put("UpdateLevelScores", new UpdateLevelScoresCommand(model, view));
		commands.put("LevelScoreSortSteps", new LevelScoresSortByStepsCommand(model, view));
		commands.put("LevelScoreSortTime", new LevelScoresSortByTimeCommand(model, view));
		commands.put("GetChosenPlayerScores", new ChosenPlayerScoresCommand(model, view));
		commands.put("UpdateChosenPlayerScores", new UpdatePlayerScoresCommand(model, view));
		commands.put("GetHint", new GetHintCommand(model, view));
		commands.put("GetSolution", new GetSolutionCommand(model, view));
		commands.put("EndGame", new EndGameCommand(model, view));
	}

	/** gets notification from Model\View and send it to MyControllre **/
	@Override
	public void update(Observable o, Object arg)
	{
		// If Model was updated and using GUI update "numOfSteps" with binding
		if(o == this.model && model.getLevel() != null)
			Platform.runLater(() -> numOfSteps.set(model.getLevel().get_numOfSteps()));

		@SuppressWarnings("unchecked")
		List<String> params = (List<String>) arg;
		String commandKey = params.remove(0);

		// Check if user wants to exit
		if(commandKey.equals("Exit"))
		{
			AbsCommand c = commands.get(commandKey);
			c.setParams(params);
			controller.insertCommand(c);
			controller.stop();
		}

		// Insert Command to the queue
		else
		{
			AbsCommand c = commands.get(commandKey);

			if (c == null)
			{
				view.displayMessage("Command not found");
				return;
			}

			c.setParams(params);
			controller.insertCommand(c);
		}
	}
}
