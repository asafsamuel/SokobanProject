package view;

import java.util.List;

import common.Level;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import model.db.Player;
import model.db.PlayersRecords;

/** Interface View **/
public interface View
{
	public void displayLevel(Level level, String direction);
	public void displayMessage(String msg);

	public void setPlayersList(List<Player> p);
	public void setLevelScores(List<PlayersRecords> pr);
	public void setPlayerScores(List<PlayersRecords> pr);
	public void setLevelsList(List<Level> levels);
	
	public void bindStepText(IntegerProperty r);
	public void bindSteps(StringProperty s);
	public void setMoveFlag(boolean b);
	
	public void close();
}
