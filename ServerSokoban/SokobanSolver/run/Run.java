package run;

import java.util.ArrayList;
import java.util.List;

import common.Level;
import plannable.Action;
import plannable.Plannable;
import planner.HeuristicMethods;
import planner.Plan;
import planner.Planner;
import planner.Strips;
import searchers.Solution;
import sokobanPlannable.SokobanPlannable;
import sokobanSearchable.SolutionAction;

public class Run
{
	// Run function
	public static List<String> runAlgorithem(Level l)
	{
		if(l == null)
			return null;

		Planner planner = new Strips();
		Plannable plannable = new SokobanPlannable(l.get_gameBoard());
		HeuristicMethods he = new HeuMethod();

		Plan plan = planner.plane(plannable, he);
		plan = dividePlans(plan);
		
		List<String> directrions = new ArrayList<String>();
		
		for (Action ac : plan.getActions()) 
		{
			if(ac.toString().length() > 4)
				directrions.add(ac.toString());
		}

		return directrions;
	}

	// Divide the places-actions
	private static Plan dividePlans(Plan plan)
	{
		Plan p = new Plan();
		Plan finalPlan = new Plan();

		if(plan == null)
			return null;

		while(!plan.getActions().isEmpty())
		{
			Action a = plan.getActions().remove(0);
			p.addAction(a);

			if(a.getType().equals("Place"))
			{
				Plan temp = fixPlane(p);

				for (Action ac : temp.getActions())
					finalPlan.addAction(ac);

				p.getActions().clear();
			}
		}

		return finalPlan;
	}

	private static Plan fixPlane(Plan plan)
	{
		Plan fixP = new Plan();
		List<Action> action = plan.getActions();

		// Remove place action(=nothing)
		action.remove(action.size()-1);

		// Get first player steps (to box)
		Solution playerSteps = (Solution) action.remove(0).getArgs()[0];
		fixP.addAction(new SolutionAction(playerSteps, null, null));


		// Get box movement
		Solution boxMove = (Solution) action.remove(action.size()-1).getArgs()[0];
		String move = boxMove.getActions().get(0).getName();

		while(!boxMove.getActions().isEmpty())
		{
			searchables.Action mA = boxMove.getActions().remove(0);
			if(!mA.getName().equals(move))
			{
				fixP.addAction(action.remove(0));
				Action temp = new Action(mA.getName(), null, null, "Push");
				fixP.addAction(temp);
				move = mA.getName();
			}

			else
				fixP.addAction(new Action(mA.getName(), null, null, "Push"));
		}

		return fixP;
	}
}
