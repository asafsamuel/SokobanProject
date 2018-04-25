package model.data;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import common.Level;

/** This function gets OutputStearm and a Level and saves the Level as Object file **/
public class ObjectLevelSaver implements LevelSaver
{
	@Override
	public void savelevel(OutputStream output, Level l) throws IOException,ClassNotFoundException
	{
		ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(output));
		oos.writeObject(l);

		oos.flush();
		oos.close();
	}
}
