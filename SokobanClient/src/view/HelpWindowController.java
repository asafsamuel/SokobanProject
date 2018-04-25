package view;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/** HelpWindowController - this class displays the help picture **/
public class HelpWindowController implements Initializable
{
	@FXML
	private ImageView mv;
	private Image m;

	public HelpWindowController() throws URISyntaxException
	{
		m = new Image(getClass().getResource("/image/HelpImage.png").toURI().toString());
	}

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		if(m != null)
			mv.setImage(m);
	}
}
