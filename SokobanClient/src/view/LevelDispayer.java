package view;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import common.Level;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import model.data.Cell;

/** LevelDispayer - this class displays the Level's board to the user **/
public class LevelDispayer extends Canvas
{
	// Local Variables
	private ArrayList<ArrayList<Cell>> gameBoard;

	GraphicsContext gc;
	Image wall;
	Image box;
	Image floor;
	Image target;
	Image boxInPlace;
	Image characterFront;
	Image characterBack;
	Image characterLeft;
	Image characterRight;

	// C'tor
	public LevelDispayer() throws URISyntaxException
	{
		gameBoard = null;

		gc = getGraphicsContext2D();
		wall = null;
		box = null;
		characterFront = null;
		characterBack = null;
		characterRight = null;
		characterLeft = null;
		floor = null;
		target = null;
		boxInPlace = null;

		// get Objects images locations
		wall = new Image(getClass().getResource("/image/wall.jpg").toURI().toString());
		box = new Image(getClass().getResource("/image/box.jpg").toURI().toString());
		boxInPlace = new Image(getClass().getResource("/image/boxInPlace.jpg").toURI().toString());
		floor = new Image(getClass().getResource("/image/floor.png").toURI().toString());
		target = new Image(getClass().getResource("/image/target.png").toURI().toString());
		characterFront = new Image(getClass().getResource("/image/cFront.png").toURI().toString());
		characterBack = new Image(getClass().getResource("/image/cBack.png").toURI().toString());
		characterLeft = new Image(getClass().getResource("/image/cLeft.png").toURI().toString());
		characterRight = new Image(getClass().getResource("/image/cRight.png").toURI().toString());
	}

	// Function "setLevel" - update local gameBoard
	public void setLevel(Level level, String direction) throws FileNotFoundException
	{
		if(level != null)
		{
			this.gameBoard = level.get_gameBoard();
			redraw(direction);
		}
	}

	// Function "redraw" - presents the level
	public void redraw(String direction) throws FileNotFoundException
	{
		if(gameBoard != null)
		{
			double W = getWidth();
			double H = getHeight();

			/*
			wall = new Image(new FileInputStream(wallFileName.get()));
			characterFront = new Image(new FileInputStream(characterFileName1.get()));
			characterBack = new Image(new FileInputStream(characterFileName2.get()));
			characterLeft = new Image(new FileInputStream(characterFileName3.get()));
			characterRight = new Image(new FileInputStream(characterFileName4.get()));
			target = new Image(new FileInputStream(targetFileName.get()));
			box = new Image(new FileInputStream(boxFileName.get()));
			floor = new Image(new FileInputStream(floorFileName.get()));
			boxInPlace = new Image(new FileInputStream(boxInPlaceFilName.get()));
			*/

			// For loop get max
			int max = gameBoard.get(0).size();
			for (int i = 1; i < gameBoard.size(); i++)
			{
				max = Math.max(max, gameBoard.get(i).size());
			}

			double w = W/max;
			double h = H/gameBoard.size();
			gc.clearRect(0,0,W,H);

			// For - For loop
			for (int i = 0; i < gameBoard.size(); i++)
			{
				for (int j = 0; j < gameBoard.get(i).size(); j++)
				{
					// Cell = Floor
					if(gameBoard.get(i).get(j).GetType().equals("Floor"))
					{
						gc.setFill(Color.AZURE);

						if(floor == null)
							gc.fillRect(j*w, i*h, w, h);

						else
							gc.drawImage(floor, j*w, i*h, w, h);
					}

					// Cell = Target
					else if(gameBoard.get(i).get(j).GetType().equals("Target"))
					{
						gc.setFill(Color.CRIMSON);

						if(target == null)
							gc.fillOval(j*w, i*h, w, h);

						else
							gc.drawImage(target, j*w, i*h, w, h);
					}

					// Cell = Character
					else if(gameBoard.get(i).get(j).GetType().equals("Character"))
					{
						gc.setFill(Color.BLUE);

						switch(direction)
						{
						case "Down":
							if(characterFront == null)
								gc.fillOval(j*w, i*h, w, h);

							else
								gc.drawImage(characterFront, j*w, i*h, w, h);
							break;

						case "Up":
							if(characterBack == null)
								gc.fillOval(j*w, i*h, w, h);

							else
								gc.drawImage(characterBack, j*w, i*h, w, h);
							break;

						case "Left":
							if(characterLeft == null)
								gc.fillOval(j*w, i*h, w, h);

							else
								gc.drawImage(characterLeft, j*w, i*h, w, h);
							break;

						case "Right":
							if(characterRight == null)
								gc.fillOval(j*w, i*h, w, h);

							else
								gc.drawImage(characterRight, j*w, i*h, w, h);
							break;
						}
					}

					// Cell = Box
					else if(gameBoard.get(i).get(j).GetType().equals("Box"))
					{
						gc.setFill(Color.WHEAT);

						if(gameBoard.get(i).get(j).getFlag() == 1)
						{
							gc.setFill(Color.YELLOW);
							if(box == null)
								gc.fillRect(j*w, i*h, w, h);

							else
								gc.drawImage(boxInPlace, j*w, i*h, w, h);
						}

						else
						{
							if(box == null)
								gc.fillRect(j*w, i*h, w, h);

							else
								gc.drawImage(box, j*w, i*h, w, h);
						}
					}

					// Cell = Wall
					else if(gameBoard.get(i).get(j).GetType().equals("Wall"))
					{
						gc.setFill(Color.DARKGRAY);

						if(wall == null)
							gc.fillRect(j*w, i*h, w, h);

						else
							gc.drawImage(wall, j*w, i*h, w, h);
					}
				}
			}
		}
	}
}