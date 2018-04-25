package model.data;

import java.io.IOException;
import java.io.OutputStream;

import common.Level;

public interface LevelSaver 
{
	public void savelevel(OutputStream output, Level l) throws IOException, ClassNotFoundException, Exception;
}
