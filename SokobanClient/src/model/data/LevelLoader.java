package model.data;

import java.io.IOException;
import java.io.InputStream;

import common.Level;

/** 
 * Answers to questions a-d:
 * 
 * a) The separation between objectives: LevelLoader interface creates the data (with function "loadlevel") 
 *    and Class Level saves the data (_gameBoard variable).
 *  
 * b) This way we keep the Open\Close Principle - if we want to load a different type of file we can do it 
 *    simply by creating a new class that implements LevelLoader.
 * 
 * c) This way we also keep the Liskov Substitution Principle - every Class that implements LevelLoader
 *    DOESN'T depend on other classes. It only implements LevelLoader's function and every class has
 *    different implements.
 *    
 *  d) InputStream is a superclass of all the of all classes representing an input stream of bytes (files,
 *     networks connection, etc). String file-name only represents a file. 
 *     If we will like to input another source that isn't a file, we only can do it with InputStream and
 *     not with String filename.
 **/

public interface LevelLoader 
{
	public Level loadlevel(InputStream input) throws IOException, ClassNotFoundException;
}
