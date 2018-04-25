package view;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

/** IntroVidController - this class displays the Intro video **/
public class IntroVidController implements Initializable
{
	@FXML
	private MediaView mediaV = new MediaView();

	private Media media;
	private MediaPlayer player;

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		//media = new Media("file:///C://Users//amir//Desktop//s//MyIntro.wmv");
		try
		{
			media = new Media(getClass().getResource("/video/MyIntro.wmv").toURI().toString());
			player = new MediaPlayer(media);
			mediaV.setMediaPlayer(player);
			player.setAutoPlay(true);
		}

		catch (URISyntaxException e)
		{
			System.out.println("Cannot load intro!");
		}
	}
}
