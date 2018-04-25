package view;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.db.PlayersRecords;

/** LevelScoreBoardController - this class displays all the Level's scores **/
public class LevelScoreBoardController implements Initializable
{
	// Local Variables
	private MainWindowController mainWindow;
	private ObservableList<PlayersRecords> data;

	private FXMLLoader fxmlLoader;
	private Stage stagePlayerScore;

	@FXML
	PlayerScoreBoardController playerScore; // Use to show this Player's scores

	@FXML
	private Button closeButton;

	@FXML
	private TableView<PlayersRecords> table;

	@FXML
	private TableColumn<PlayersRecords, Integer> playerId;

	@FXML
	private TableColumn<PlayersRecords, String> playerName;

	@FXML
	private TableColumn<PlayersRecords, Integer> playerSteps;

	@FXML
	private TableColumn<PlayersRecords, Integer> playerTime;

	// C'tor
	public LevelScoreBoardController(MainWindowController mainWindow)
	{
		this.mainWindow = mainWindow;
		stagePlayerScore = null;
		playerScore = new PlayerScoreBoardController();

		try
		{
			// LevelScoreBoard Window
			fxmlLoader = new FXMLLoader(getClass().getResource("PlayerScoreBoard.fxml"));
			fxmlLoader.setController(playerScore);
			Parent root5 = (Parent) fxmlLoader.load();
			stagePlayerScore = new Stage();
			stagePlayerScore.initStyle(StageStyle.UTILITY);
			stagePlayerScore.setResizable(false);
			stagePlayerScore.setScene(new Scene(root5));
		}

		catch(Exception e)
		{
			mainWindow.displayMessage(e.getMessage());
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		playerId.setCellValueFactory(new PropertyValueFactory<>("PlayerId"));
		playerName.setCellValueFactory(new PropertyValueFactory<>("PlayerName"));
		playerSteps.setCellValueFactory(new PropertyValueFactory<>("PlayerSteps"));
		playerTime.setCellValueFactory(new PropertyValueFactory<>("PlayerTime"));

		// OnMouseClick - when use click on row
		table.setOnMousePressed(new EventHandler<javafx.scene.input.MouseEvent>()
		{
			@Override
			public void handle(javafx.scene.input.MouseEvent event)
			{
				PlayersRecords p = table.getSelectionModel().getSelectedItem();

				mainWindow.getChosenPlayerScores(p.getPlayerId());

				if(stagePlayerScore != null)
					stagePlayerScore.show();
			}
		});
	}

	// Function "setScores" - set Table's data
	public void setScores(List<PlayersRecords> pr)
	{
		if(pr != null)
		{
			data = FXCollections.observableList(pr);
			table.setItems(data);
		}
	}

	// Function "showPlayerScores" - open PlayerScoreTable
	public void showPlayerScores()
	{
		if(stagePlayerScore != null)
			stagePlayerScore.show();
	}

	// Function "setPlayerScores" - set Chosen Player Table's data
	public void setPlayerScores(List<PlayersRecords> pr)
	{
		if(pr != null && playerScore!= null)
		{
			playerScore.setScores(pr);
		}
	}

	// Function "SortBySteps" - sort Level's scores by steps
	public void SortBySteps()
	{
		mainWindow.sortLevelScoresBySteps();
	}

	// Function "SortByTime" - sort Level's scores by time
	public void SortByTime()
	{
		mainWindow.sortLevelScoresByTime();
	}

	// Function "closeWindow" - hide control's stage
	public void closeWindow()
	{
		Stage stage = (Stage) closeButton.getScene().getWindow();
		stage.hide();
	}
}
