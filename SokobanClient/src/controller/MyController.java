package controller;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
//import java.util.concurrent.TimeUnit;

import controller.commands.Command;
/** SokobanController - this class manages all the Commands and execute them.
 * 	Every Command that was added is inserted to BlockingQueue and waiting for another thread to
 *  take and execute it.
 * **/
public class MyController
{
	// Local Variables
	BlockingQueue<Command> queue;
	boolean isStopped = false;

	// C'otr
	public MyController()
	{
		queue = new ArrayBlockingQueue<Command>(20);
	}

	/** This function inserts new Command into the BlockingQueue **/
	public void insertCommand(Command command)
	{
		try
		{
			queue.put(command);
		}

		catch (InterruptedException e)
		{
			System.out.println("Couldn't add command!");
		}
	}

	/** This function creates a new thread and pulls Commands out from the queue and execute them **/
	public void start()
	{
		Thread thread = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				while (!isStopped)
				{
					Command cmd;
					try
					{
						//cmd = queue.poll(1, TimeUnit.SECONDS);
						cmd = queue.take();

						if (cmd != null)
							cmd.execute(); // Execute Command (Use Model and View)
					}

					catch (Exception e)
					{
						System.out.println("Couldn't take out a command!");
						System.out.println(e.getMessage());
					}
				}
			}
		});
		thread.start();
	}

	/** Stop start function's thread and don't allow it to pull Commands from the queue **/
	public void stop()
	{
		isStopped = true;
	}
}
