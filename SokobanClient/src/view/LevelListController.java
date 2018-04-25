package view;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import common.Level;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/** LevelListController - this class displays all the Levels **/
public class LevelListController implements Initializable
{
	// Local Variables
	Level level;
	ObservableList<Level> data;
	boolean Getflag;
	MainWindowController mainWindow;

	@FXML
	private Button closeButton;

	@FXML
	private TextField nameText;

	@FXML
	private TableColumn<Level, String> LevelName;

	@FXML
	private TableView<Level> table;

	// C'tor
	public LevelListController(MainWindowController o)
	{
		Getflag = false;
		this.mainWindow = o;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		LevelName.setCellValueFactory(new PropertyValueFactory<>("LevelName"));

		// OnMouseClick - when use click on row
		table.setOnMousePressed(new EventHandler<javafx.scene.input.MouseEvent>()
		{
			@Override
			public void handle(javafx.scene.input.MouseEvent event)
			{
				level = table.getSelectionModel().getSelectedItem();

				if(level != null)
				{
					mainWindow.chooseLevel(level.getLevelName());
					Stage stage = (Stage) closeButton.getScene().getWindow();
					stage.hide();
				}
			}
		});
	}

	public void SetFlag(boolean flag)
	{
		this.Getflag = flag;
	}
	public void setLevelsList(List<Level> levels)
	{
		data = FXCollections.observableList(levels);
		table.setItems(data);
	}


	// Function "closeWindow" - hide control's stage
	public void closeWindow()
	{
		Stage stage = (Stage) closeButton.getScene().getWindow();
		stage.hide();
	}
}
