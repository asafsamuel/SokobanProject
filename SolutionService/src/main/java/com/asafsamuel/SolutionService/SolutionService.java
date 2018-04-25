package com.asafsamuel.SolutionService;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.asafsamuel.db.DbHandler;
import com.asafsamuel.db.LevelSolution;

/** SolutionService - this class gets request from clients and act 
 *  (GET for getting a Solution, or POST for adding a Solution) **/
@Path("solutions")
public class SolutionService 
{
	// Local Variable
	DbHandler handler = DbHandler.getInstance();
	
	/** This functions gets Level name and returns it's Solution from db (GET function) **/
	@GET
    @Produces(MediaType.TEXT_PLAIN)
	@Path("{name}")
    public String getSolution(@PathParam("name") String name) 
	{
        return handler.getSolution(name);
    }
	
	/** This functions gets Level name and a Solution, and adds them to db (POST function) **/
	@POST  
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void addSolution(@FormParam("name") String name, @FormParam("solution") String solution) 
	{
		LevelSolution ls = new LevelSolution(name, solution);
		handler.addSolution(ls);
	}
}
