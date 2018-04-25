package view;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import model.db.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/** PlayersListController - this class displays all the Players **/
public class PlayersListController implements Initializable
{
	// Local Variables
	private Player myPlayer;
	private ObservableList<Player> data;
	private boolean Getflag;
	private MainWindowController mainWindow;

	@FXML
	private Button closeButton;

	@FXML
	private TextField nameText;

	@FXML
	private TableColumn<Player, Integer> playerId;

	@FXML
	private TableColumn<Player, String> playerName;

	@FXML
	private TableView<Player> table;

	// C'tor
	public PlayersListController(MainWindowController o)
	{
		Getflag = false;
		this.mainWindow = o;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		playerId.setCellValueFactory(new PropertyValueFactory<>("PlayerId"));
		playerName.setCellValueFactory(new PropertyValueFactory<>("PlayerName"));

		// OnMouseClick - when use click on row
		table.setOnMousePressed(new EventHandler<javafx.scene.input.MouseEvent>()
		{
			@Override
			public void handle(javafx.scene.input.MouseEvent event)
			{
				myPlayer = table.getSelectionModel().getSelectedItem();

				if(myPlayer != null)
				{
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Hello");
					alert.setHeaderText(null);
					alert.setContentText("Hello " +myPlayer.getPlayerName() + "!");

					Optional<ButtonType> result = alert.showAndWait();
					if (result.get() == ButtonType.OK)
					{
						if(Getflag == true)
						{
							mainWindow.addPlayerScore();
							Getflag = false;
						}

						Stage stage = (Stage) closeButton.getScene().getWindow();
						stage.hide();
					}
				}
			}
		});
	}

	// Getters and Setters
	public Player getAcount()
	{
		return myPlayer;
	}
	public void SetFlag(boolean flag)
	{
		this.Getflag = flag;
	}
	public void setPlayersList(List<Player> players)
	{
		data = FXCollections.observableList(players);
		table.setItems(data);
	}


	// Function "closeWindow" - hide control's stage
	public void closeWindow()
	{
		Stage stage = (Stage) closeButton.getScene().getWindow();
		stage.hide();
	}

	// Function "AddRow" - user wants to add a new Player
	public void AddRow()
	{
		TextInputDialog dialog = new TextInputDialog("");
		dialog.setTitle("Create new player");
		dialog.setHeaderText(null);
		dialog.setContentText("Enter your name:");
		dialog.initStyle(StageStyle.UTILITY);

		Optional<String> result = dialog.showAndWait();
		if (result.isPresent())
		{
			mainWindow.addPlayer(result.get());
		}
	}
}
