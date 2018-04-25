package view;

import java.util.Optional;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.MyModel;
import viewmodel.MyServer;

public class Run extends Application
{
	// Local variables
	FXMLLoader loader;
	BorderPane root;
	Scene scene;

	/** This function starts the whole project **/
	public static void main(String[] args)
	{
		launch(args);
	}

	/** This function creates admin window **/
	@Override
	public void start(Stage primaryStage) throws Exception
	{
		loader = new FXMLLoader(getClass().getResource("AdministratorGUI.fxml"));
		root = (BorderPane)loader.load();
		AdministratorViewController view = loader.getController();
		scene = new Scene(root);
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
				view.close();
				
			}
		});

		primaryStage.setResizable(false);
		primaryStage.show();
	}

	// Function "init" - create MVC model
	private void init(AdministratorViewController view)
	{
		MyModel model = new MyModel();
		MyServer server = new MyServer(1003, model);
		
		view.setVariables(server);
		server.addObserver(view);
		model.addObserver(server);
	}
}
