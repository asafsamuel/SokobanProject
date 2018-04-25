package model.data;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import common.Level;

/** MyXmlLevelLoader - this class gets InputStream and load a Level from it.
 * 	The InputStream is a xml file.
 * **/
public class MyXmlLevelLoader implements LevelLoader
{
	@Override
	public Level loadlevel(InputStream input) throws IOException,ClassNotFoundException
	{
		XMLDecoder x = new XMLDecoder(new BufferedInputStream(input));
		Level level = (Level) x.readObject();

		x.close();

		return level;
	}
}
