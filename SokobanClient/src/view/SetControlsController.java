package view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/** SetControlsController - this class is in charge of all the controls of the game (movement keyboards-keys) **/
public class SetControlsController extends BorderPane implements Initializable
{
	// Local Variables
	@FXML
	private Button closeButton;
	@FXML
	private TextField UpLabel;
	@FXML
	private TextField DownLabel;
	@FXML
	private TextField LeftLabel;
	@FXML
	private TextField RightLabel;

	private StringProperty goUp;
	private StringProperty DefaultUp;
	private StringProperty goDown;
	private StringProperty DefaultDown;
	private StringProperty goLeft;
	private StringProperty DefaultLeft;
	private StringProperty goRight;
	private StringProperty DefaultRight;

	// C'tor
	public SetControlsController()
	{
		goUp = new SimpleStringProperty();
		goDown = new SimpleStringProperty();
		goLeft = new SimpleStringProperty();
		goRight = new SimpleStringProperty();
		DefaultUp = new SimpleStringProperty();
		DefaultDown = new SimpleStringProperty();
		DefaultLeft = new SimpleStringProperty();
		DefaultRight = new SimpleStringProperty();
	}

	// Function "closeWindow" - hide control's stage
	public void closeWindow()
	{
		Stage stage = (Stage) closeButton.getScene().getWindow();
		stage.hide();
	}

	// Function "updateLabels" - updates controls GUI labels
	private void updateLabels()
	{
		Platform.runLater(() -> UpLabel.setText(this.goUp.get()));
		Platform.runLater(() -> DownLabel.setText(this.goDown.get()));
		Platform.runLater(() -> LeftLabel.setText(this.goLeft.get()));
		Platform.runLater(() -> RightLabel.setText(this.goRight.get()));
	}

	// Function "ChangeControls" - disable or enable to change Text
	public void ChangeUpControl()
	{
		if(UpLabel.isDisable())
			UpLabel.setDisable(false);

		else
		{
			UpLabel.setDisable(true);
			setGoUp(UpLabel.getText());
			updateLabels();
		}
	}

	public void ChangeDownControl()
	{
		if(DownLabel.isDisable())
			DownLabel.setDisable(false);

		else
		{
			DownLabel.setDisable(true);
			setGoDown(DownLabel.getText());
			updateLabels();
		}
	}

	public void ChangeLeftControl()
	{
		if(LeftLabel.isDisable())
			LeftLabel.setDisable(false);

		else
		{
			LeftLabel.setDisable(true);
			setGoLeft(LeftLabel.getText());
			updateLabels();
		}
	}

	public void ChangeRightControl()
	{
		if(RightLabel.isDisable())
			RightLabel.setDisable(false);

		else
		{
			RightLabel.setDisable(true);
			setGoRight(RightLabel.getText());
			updateLabels();
		}
	}

	// Function "DefaultControls" - reset controls
	public void DefaultControls()
	{
		this.goUp.set(DefaultUp.get());
		this.goDown.set(DefaultDown.get());
		this.goLeft.set(DefaultLeft.get());
		this.goRight.set(DefaultRight.get());
		updateLabels();
	}

	// Getters and Setters
	public String getGoUp()
	{
		return goUp.get();
	}

	public void setGoUp(String goUp)
	{
		this.goUp.set(goUp);
	}

	public String getGoDown()
	{
		return goDown.get();
	}

	public void setGoDown(String goDown)
	{
		this.goDown.set(goDown);
	}

	public String getGoLeft()
	{
		return goLeft.get();
	}

	public void setGoLeft(String goLeft)
	{
		this.goLeft.set(goLeft);
	}

	public String getGoRight()
	{
		return goRight.get();
	}

	public void setGoRight(String goRight)
	{
		this.goRight.set(goRight);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		goUp.set(UpLabel.getText());
		DefaultUp.set(UpLabel.getText());
		goDown.set(DownLabel.getText());
		DefaultDown.set(DownLabel.getText());
		goLeft.set(LeftLabel.getText());
		DefaultLeft.set(LeftLabel.getText());
		goRight.set(RightLabel.getText());
		DefaultRight.set(RightLabel.getText());
	}
}
