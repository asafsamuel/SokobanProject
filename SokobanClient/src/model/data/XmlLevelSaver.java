package model.data;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import common.Level;

/** This function gets OutputStearm and a Level and saves the Level as xml file **/
public class XmlLevelSaver implements LevelSaver
{
	@Override
	public void savelevel(OutputStream output, Level l) throws IOException,ClassNotFoundException
	{
		XMLEncoder x = new XMLEncoder(new BufferedOutputStream(output));
		x.writeObject(l);

		x.close();
	}
}
