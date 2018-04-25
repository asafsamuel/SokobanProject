package view;

import java.util.Optional;

import controller.SokobanController;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.MyModel;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;

/** Main - this class starts the View, Model and the Controller and connects between them.
 *  This class is the former Run for this project.
 * @author asaf samuel
 */
public class Main extends Application
{
	// Local Variables
	FXMLLoader loader;
	BorderPane root;
	Scene scene;
	MyModel model;

	/** This function opens Intro Window and after tha the Game window **/
	@Override
	public void start(Stage primaryStage)
	{
		startIntro(primaryStage);

		PauseTransition delay = new PauseTransition(Duration.seconds(21));
		delay.setOnFinished((e) -> startGame(primaryStage));
		delay.play();
		
		//startGame(primaryStage);
	}

	// This function creates the MVC model
	private void init(MainWindowController view)
	{
		model = new MyModel();
		SokobanController controller = new SokobanController(model,view);

		model.addObserver(controller);
		view.addObserver(controller);
	}

	/** This function creates the Intro window and display it **/
	public void startIntro(Stage primaryStage)
	{
		try
		{
			loader = new FXMLLoader(getClass().getResource("IntroVidWindow.fxml"));
			root = (BorderPane)loader.load();

			scene = new Scene(root,744,414);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);

			primaryStage.setResizable(false);
			primaryStage.show();
		}

		catch(Exception e)
		{
			e.printStackTrace();
		}

	}

	/** This function creates the Game window and display it **/
	public void startGame(Stage primaryStage)
	{
		try
		{
			loader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
			root = (BorderPane)loader.load();
			MainWindowController view = loader.getController();

			scene = new Scene(root,550,600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);

			init(view);

			// Click on X (exit)
			primaryStage.setOnCloseRequest(e ->
			{
				e.consume();
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Confirmation Dialog");
				alert.setHeaderText(null);
				alert.setContentText("Are you sure you want to exit?");

				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK)
				{
					model.close();
					view.close();
				}
			});

			primaryStage.setResizable(false);
			primaryStage.show();
		}

		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/** This fucntion starts the whole project! **/
	public static void main(String[] args)
	{
			launch(args);
	}
}
