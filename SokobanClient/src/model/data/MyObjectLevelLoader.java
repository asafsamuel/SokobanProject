package model.data;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

import common.Level;

/** MyObjectLevelLoader - this class gets InputStream and load a Level from it.
 * 	The InputStream is a object file.
 * **/
public class MyObjectLevelLoader implements LevelLoader
{
	@Override
	public Level loadlevel(InputStream is) throws IOException, ClassNotFoundException
	{
		ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(is));

		Level level = (Level) ois.readObject();
		ois.close();

		return level;
	}
}
