package view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Optional;
import java.util.ResourceBundle;

import common.Level;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.db.Player;
import model.db.PlayersRecords;

/** MainWindowController - this class is part of the MVC pattren.
 * 	With this class we can display and take commands from the user and sends them to the controller.
 */
public class MainWindowController extends Observable implements View, Initializable
{
	// Local Variables
	private FXMLLoader fxmlLoader;
	private Stage stageControls;
	private Stage stageHelp;
	private Stage stagePlayers;
	private Stage stageLevelScore;
	private Stage stageLevels;
	private boolean timerFlag;
	private MediaPlayer mediaPlayer;
	private boolean FinishGameFlag;
	private String levelName;

	@FXML
	private BorderPane broder;

	@FXML
	private MenuItem musicC;

	@FXML
	private TextField time;

	@FXML
	private TextField steps;

	@FXML
	SetControlsController controls; // Use to change Controls

	@FXML
	LevelScoreBoardController levelScore; // Use to show this Level's scores

	@FXML
	PlayersListController players; // Use to select Account
	
	@FXML
	LevelListController levels;

	@FXML
	LevelDispayer levelDis; // Use to display Level (canvas)

	// C'tor
	public MainWindowController()
	{
		stageControls = null;
		stageHelp = null;
		stagePlayers = null;
		stageLevelScore = null;
		stageLevels = null;
		levelName = "";

		// Start BackGround Music
		Media musicFile;
		try
		{
			musicFile = new Media(getClass().getResource("/music/MainMenuMusic.wav").toURI().toString());
			mediaPlayer = new MediaPlayer(musicFile);
			mediaPlayer.setAutoPlay(true);

			FinishGameFlag = false;
			timerFlag = true;
			
			levelDis = new LevelDispayer();
			controls = new SetControlsController();
			players = new PlayersListController(this);
			levelScore = new LevelScoreBoardController(this);
			levels = new LevelListController(this);

			// Controls Setting Window
			fxmlLoader = new FXMLLoader(getClass().getResource("SetControls.fxml"));
			fxmlLoader.setController(controls);
			Parent root1 = (Parent) fxmlLoader.load();
			stageControls = new Stage();
			stageControls.initStyle(StageStyle.UTILITY);
			stageControls.setResizable(false);
			stageControls.setScene(new Scene(root1));

		 	// Help Window
			fxmlLoader = new FXMLLoader(getClass().getResource("HelpWindow.fxml"));
			Parent root2 = (Parent) fxmlLoader.load();
			stageHelp = new Stage();
			stageHelp.initStyle(StageStyle.UTILITY);
			stageHelp.setResizable(false);
			stageHelp.setScene(new Scene(root2));

			// PlayersList Window
			fxmlLoader = new FXMLLoader(getClass().getResource("PlayersList.fxml"));
			fxmlLoader.setController(players);
			Parent root3 = (Parent) fxmlLoader.load();
			stagePlayers = new Stage();
			stagePlayers.initStyle(StageStyle.UTILITY);
			stagePlayers.setResizable(false);
			stagePlayers.setScene(new Scene(root3));
			
			// LevelsList Window
			fxmlLoader = new FXMLLoader(getClass().getResource("LevelList.fxml"));
			fxmlLoader.setController(levels);
			Parent root5 = (Parent) fxmlLoader.load();
			stageLevels = new Stage();
			stageLevels.initStyle(StageStyle.UTILITY);
			stageLevels.setResizable(false);
			stageLevels.setScene(new Scene(root5));

			// LevelScoreBoard Window
			fxmlLoader = new FXMLLoader(getClass().getResource("LevelScoreBoard.fxml"));
			fxmlLoader.setController(levelScore);
			Parent root4 = (Parent) fxmlLoader.load();
			stageLevelScore = new Stage();
			stageLevelScore.initStyle(StageStyle.UTILITY);
			stageLevelScore.setResizable(false);
			stageLevelScore.setScene(new Scene(root4));
		}

		catch (URISyntaxException | IOException e1)
		{
			displayMessage(e1.getMessage());
		}
	}

	// Function "ControlMusic" - turn on\off background music
	public void ControlMusic()
	{
		if(!mediaPlayer.getStatus().equals(Status.PLAYING))
		{
			mediaPlayer.play();
			Platform.runLater(()-> musicC.setText("Music OFF"));
		}

		else
		{
			mediaPlayer.stop();
			Platform.runLater(() -> musicC.setText("Music ON"));
		}
	}

	// Function "openFile" - get file's location from the user and notify Controller
	public void openFile()
	{
		FileChooser fc = new FileChooser();
		fc.setTitle("open level file");
		//fc.setInitialDirectory(new File("./Levels"));

		fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("TEXT File", "*.txt"));
		fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML File", "*.xml"));
		fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Object File", "*.obj"));

		File chosen = fc.showOpenDialog(null);

		if(chosen != null)
		{
			int i = 0;
			while(chosen.getName().charAt(i) != '.')
				i++;

			levelName = chosen.getName().substring(0, i);
			
			String command = "LoadLevel";
			LinkedList<String> params = new LinkedList<String>();
			params.add(command);
			params.add(chosen.getPath());
			params.add(levelName);

			this.setChanged();
			this.notifyObservers(params); // notify Controller - tell Model to load new Level

			//call timer thread
			timerFlag = false;
			FinishGameFlag = false;
			Platform.runLater(() -> time.setText("0"));
			startTimer();
		}
	}

	// Function "bindSteps" - bind steps text with Model's "numOfSteps"
	public void bindSteps(StringProperty s)
	{
		s.bind(steps.textProperty());
	}

	// Function "bindStepText" - bind steps text with Model's "numOfSteps"
	public void bindStepText(IntegerProperty r)
	{
		Platform.runLater(() -> steps.textProperty().bind(r.asString()));
	}

	// Function "startTimer" - create a new thread and start timer
	private void startTimer()
	{
		try
		{
			Thread.sleep(1000);
		}

		catch (InterruptedException e)
		{
			displayMessage(e.getMessage());
		}


		timerFlag = true;

		Thread t = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				while(timerFlag)
				{
					Platform.runLater(() ->time.setText(String.valueOf(Integer.parseInt(time.getText())+1)));
					try
					{
						Thread.sleep(1000);
					}

					catch (InterruptedException e)
					{
						displayMessage(e.getMessage());
					}
				}
			}
		});
		t.start();
	}

	// Function "closeGame" - stop Controller and View
	public void closeGame()
	{
		// Open Dialog Window
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation Dialog");
		alert.setHeaderText(null);
		alert.setContentText("Are you sure you want to exit?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK)
		{
			String command = "Exit";
			LinkedList<String> params = new LinkedList<String>();
			params.add(command);

			this.setChanged();
			this.notifyObservers(params); // notify Controller - "Exit"
			timerFlag = false; // stop Timer
		}

		else
		{
			return;
		}
	}

	// Override Function "displayLevel" - update levelDis
	@Override
	public void displayLevel(Level level, String direction)
	{
		Platform.runLater(() ->
		{
			try
			{
				levelDis.setLevel(level, direction);
			}

			catch(FileNotFoundException e)
			{
				displayMessage("Error - Cannot Display Level");
			}
		});

		if(level.get_numOfBoxes() == 0)
			WinGame();
	}

	// Override function "displayMessage" - print message to screen
	@Override
	public void displayMessage(String msg)
	{
		//System.out.println("Message: "+msg);
		Platform.runLater(
				() -> {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Error");
					alert.setHeaderText(null);
					alert.setContentText(msg);

					alert.showAndWait();
				});
	}

	// Override function "initialize" - get movement directions from the user
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		levelDis.setFocusTraversable(true);
		levelDis.setOnKeyPressed(new EventHandler<KeyEvent>()
		{
			@Override
			public void handle(KeyEvent event)
			{
				if(!FinishGameFlag)
				{
					String direction;

					if(event.getCode().getName().equals(controls.getGoUp()))
						direction = "Up";

					else if(event.getCode().getName().equals(controls.getGoDown()))
						direction = "Down";

					else if(event.getCode().getName().equals(controls.getGoLeft()))
						direction = "Left";

					else if(event.getCode().getName().equals(controls.getGoRight()))
						direction = "Right";

					else
						return;

					String command = "MoveCharacter";
					LinkedList<String> params = new LinkedList<String>();
					params.add(command);
					params.add(direction);

					setChanged();
					notifyObservers(params); // notify Controller - MoveCommand
				}
			}
		});
	}

	// Function "WinGam" - the user win the level (stop timer and counter)
	public void WinGame()
	{
		timerFlag = false;
		FinishGameFlag = true;

		Platform.runLater(
				() -> {
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Save Score");
					alert.setHeaderText(null);
					alert.setContentText("Do you want to save your score?");

					ButtonType YesButton = new ButtonType("Yes", ButtonData.YES);
					ButtonType NoButton = new ButtonType("No", ButtonData.NO);
					alert.getButtonTypes().setAll(YesButton,NoButton);

					Optional<ButtonType> result = alert.showAndWait();
					if (result.get() == YesButton)
					{
						if(players.getAcount() == null)
						{
							players.SetFlag(true);
							SelectPlayer();
						}

						else
							addPlayerScore();
					}

					else if(result.get() == NoButton)
						return;

					else
						return;
				});
	}

	// Function "addPlayerScore" - add new PlayerRecord to DB
	public void addPlayerScore()
	{
		String command = "AddPlayerScoreDB";
		LinkedList<String> params = new LinkedList<String>();
		params.add(command);
		params.add(this.levelName);
		params.add(""+players.getAcount().getPlayerId());
		params.add(this.steps.getText());
		params.add(this.time.getText());
		params.add(this.players.getAcount().getPlayerName());

		this.setChanged();
		this.notifyObservers(params); // notify Controller - tell Model to add new PlayerScore to database
	}

	// Function "addPlayer" - add new Player to DB
	public void addPlayer(String playerName)
	{
		String command = "AddPlayerDB";
		LinkedList<String> params = new LinkedList<String>();
		params.add(command);
		params.add(playerName);

		this.setChanged();
		this.notifyObservers(params); // notify Controller - tell Model to add new Player to database
	}

	// Function "getPlayersList" - send request to Controller to send Players list from the DB
	public void getPlayersList()
	{
		String command = "GetAllPlayersDB";
		LinkedList<String> params = new LinkedList<String>();
		params.add(command);

		this.setChanged();
		this.notifyObservers(params); // notify Controller - tell Controller to get all Players from database
	}

	// Function "setPlayersList" - update PlayersListController PlayersList
	@Override
	public void setPlayersList(List<Player> p)
	{
		players.setPlayersList(p);
	}

	// Function "setLevelScores" - update LevelScoreBoardController Scores
	@Override
	public void setLevelScores(List<PlayersRecords> pr)
	{
		levelScore.setScores(pr);
	}

	// Function "setPlayerScores" - update LevelScoreBoardController Scores
	@Override
	public void setPlayerScores(List<PlayersRecords> pr)
	{
		levelScore.setPlayerScores(pr);
	}

	// Function "getChosenPlayerScores" - get chosen player scores from DB
	public void getChosenPlayerScores(int playerId)
	{
		String command = "GetChosenPlayerScores";
		LinkedList<String> params = new LinkedList<String>();
		params.add(command);
		params.add(""+playerId);

		this.setChanged();
		this.notifyObservers(params); // notify Controller - send me this playerId all scores
	}

	// Function "sortLevelScoresBySteps" - send request to Controller to send PlayersRecrods list from the DB sorted by steps
	public void sortLevelScoresBySteps()
	{
		String command = "LevelScoreSortSteps";
		LinkedList<String> params = new LinkedList<String>();
		params.add(command);
		params.add(levelName);

		this.setChanged();
		this.notifyObservers(params); // notify Controller - get PlayersRecordsList sorted by steps
	}

	// Function "sortLevelScoresByTime" - send request to Controller to send PlayersRecrods list from the DB sorted by time
	public void sortLevelScoresByTime()
	{
		String command = "LevelScoreSortTime";
		LinkedList<String> params = new LinkedList<String>();
		params.add(command);
		params.add(levelName);

		this.setChanged();
		this.notifyObservers(params); // notify Controller - get PlayersRecordsList sorted by time
	}

	// Function "SaveLevel" - get a location from the user and notify Controller
	public void SaveLevel()
	{
		FileChooser fc = new FileChooser();
		fc.setTitle("Choose location to save level");
		//fc.setInitialDirectory(new File("./Levels"));

		fc.getExtensionFilters().add(new ExtensionFilter("TEXT File", ".txt"));
		fc.getExtensionFilters().add(new ExtensionFilter("XML File", ".xml"));
		fc.getExtensionFilters().add(new ExtensionFilter("Object File", ".obj"));

		File chosen = fc.showSaveDialog(null);
		if(chosen != null)
		{
			String command = "SaveLevel";
			LinkedList<String> params = new LinkedList<String>();
			params.add(command);
			params.add(chosen.getPath());

			this.setChanged();
			this.notifyObservers(params); // notify Controller - SaveLevelCommand
		}
	}

	// Function "setControls" - open controls window
	public void setControls()
	{
		if(stageControls != null)
			stageControls.show();
	}

	// Function "showHelp" - open help window
	public void showHelp()
	{
		if(stageHelp != null)
			stageHelp.show();
	}

	// Function "ResetLevel" - get the same new Level
	public void ResetLevel()
	{
		String command = "ResetLevel";
		LinkedList<String> params = new LinkedList<String>();
		params.add(command);

		this.setChanged();
		this.notifyObservers(params); // notify Controller - ResetLevelCommand

		//call timer thread
		timerFlag = false;
		Platform.runLater(() -> time.setText("0"));
		startTimer();
	}

	// Function "SelectPlayer" - get a player from the DataBase that the user selects
	public void SelectPlayer()
	{
		getPlayersList();

		if(stagePlayers != null)
			stagePlayers.show();
	}
	
	// Function "SelecetLevel" - get a level from the DataBase that the user selects
	public void SelectLevel()
	{
		getLevelsList();
		
		if(stageLevels != null)
			stageLevels.show();
	}
	
	// Function "getLevelsList" - send request to Controller to send Levels list from the DB
	public void getLevelsList()
	{
		String command = "GetAllLevelsDB";
		LinkedList<String> params = new LinkedList<String>();
		params.add(command);

		this.setChanged();
		this.notifyObservers(params); // notify Controller - tell Controller to get all Levels from database
	}

	// Function "ShowLevelScoreBoard" - show all scores for this Level
	public void ShowLevelScoreBoard()
	{
		if(this.levelName.length() == 0)
			displayMessage("You must load a Level first");

		else if(stageLevelScore != null)
		{
			String command = "LevelScoreSortSteps";
			LinkedList<String> params = new LinkedList<String>();
			params.add(command);
			params.add(levelName);

			this.setChanged();
			this.notifyObservers(params); // notify Controller - update scores

			stageLevelScore.show();
		}
	}

	// Function "SearchScoreBoard" - show Level or Player ScoreBoard by name
	public void SearchScoreBoard()
	{
		List<String> choices = new ArrayList<>();
		choices.add("Search Level ScoreBoard");
		choices.add("Search Player ScoreBoard");

		ChoiceDialog<String> dialog = new ChoiceDialog<>("Search Level ScoreBoard", choices);
		dialog.setTitle("Search ScoreBoard");
		dialog.setHeaderText(null);
		dialog.setContentText("Select your searcher:");

		Optional<String> result = dialog.showAndWait();
		if (result.isPresent())
		{
			dialog.hide();

			TextInputDialog dialog2 = new TextInputDialog("");
			dialog2.setTitle(result.get());
			dialog2.setHeaderText(null);
			dialog2.initStyle(StageStyle.UTILITY);

			if(result.get().equals("Search Level ScoreBoard"))
				dialog2.setContentText("Enter Level-Name:");

			else
				dialog2.setContentText("Enter Player-Id:");

			Optional<String> result2 = dialog2.showAndWait();
			if (result2.isPresent())
			{
				if(result.get().equals("Search Level ScoreBoard"))
				{
					String command = "LevelScoreSortSteps";
					LinkedList<String> params = new LinkedList<String>();
					params.add(command);
					params.add(result2.get());

					this.setChanged();
					this.notifyObservers(params); // notify Controller - update scores

					stageLevelScore.show();
				}

				else
				{
					getChosenPlayerScores(Integer.parseInt(result2.get()));
					levelScore.showPlayerScores();
				}
			}
		}
	}

	// Set finish flag - game over!
	@Override
	public void setMoveFlag(boolean b)
	{
		this.FinishGameFlag = !b;
	}

	// Get chosen level from the server and update model
	public void chooseLevel(String levelName2) 
	{
		this.levelName = levelName2;
		
		String command = "GetChosenLevelDB";
		LinkedList<String> params = new LinkedList<String>();
		params.add(command);
		params.add(levelName2);

		this.setChanged();
		this.notifyObservers(params); // notify Controller - update scores
		
		timerFlag = false;
		FinishGameFlag = false;
		Platform.runLater(() -> time.setText("0"));
		startTimer();
	}

	// Set LevelListController level-presentation list
	@Override
	public void setLevelsList(List<Level> levels) 
	{
		this.levels.setLevelsList(levels);
	}
	
	// Get full solution from the server
	public void getSolution()
	{
		if(this.levelName.length() == 0)
			displayMessage("You must load a Level first");
		
		else
		{
			String command = "GetSolution";
			LinkedList<String> params = new LinkedList<String>();
			params.add(command);
			params.add(levelName);

			this.setChanged();
			this.notifyObservers(params); // notify Controller - send solution
		}
	}
	
	// Get hint - what is next move
	public void getHint()
	{
		if(this.levelName.length() == 0)
			displayMessage("You must load a Level first");
		
		else
		{
			String command = "GetHint";
			LinkedList<String> params = new LinkedList<String>();
			params.add(command);

			this.setChanged();
			this.notifyObservers(params); // notify Controller - send hint
		}
	}

	// Close game
	@Override
	public void close() 
	{
		FinishGameFlag = true;
		System.exit(0);
	}
}
