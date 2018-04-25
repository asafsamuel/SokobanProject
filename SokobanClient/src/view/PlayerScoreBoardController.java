package view;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.db.PlayersRecords;

/** PlayerScoreBoardController - this class displays all the Player scores **/
public class PlayerScoreBoardController implements Initializable
{
	// Local Variables
	private ObservableList<PlayersRecords> data;

	@FXML
	private Button closeButton;

	@FXML
	private TableView<PlayersRecords> table;

	@FXML
	private TableColumn<PlayersRecords, String> levelName;

	@FXML
	private TableColumn<PlayersRecords, Integer> playerSteps;

	@FXML
	private TableColumn<PlayersRecords, Integer> playerTime;

	// C'tor
	public PlayerScoreBoardController()
	{
	}

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		levelName.setCellValueFactory(new PropertyValueFactory<>("LevelName"));
		playerSteps.setCellValueFactory(new PropertyValueFactory<>("PlayerSteps"));
		playerTime.setCellValueFactory(new PropertyValueFactory<>("PlayerTime"));
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

	// Function "closeWindow" - hide control's stage
	public void closeWindow()
	{
		Stage stage = (Stage) closeButton.getScene().getWindow();
		stage.hide();
	}
}
