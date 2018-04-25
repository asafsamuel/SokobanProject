package com.asafsamuel.db;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/** DbHandler - this class with hibernate connect with the db and add/remove solutions when needed **/
public class DbHandler 
{
	// Local Variable
	static DbHandler instance = new DbHandler();
	SessionFactory factory;
	
	// C'tor - Singleton Pattern
	private DbHandler()
	{
		Configuration configuration = new Configuration();
		configuration.configure();
		factory = configuration.buildSessionFactory();
	}
	
	/** Singleton Pattern - returns DbHandler **/
	public static DbHandler getInstance()
	{
		return instance;
	}
	
	/** This function add Solution to db **/
	public void addSolution(LevelSolution solution)
	{
		Session session = null;
		Transaction tx = null;
		
		try
		{
			session = factory.openSession();
			tx = session.beginTransaction();
			
			session.save(solution);
			tx.commit();
		}
		
		catch (HibernateException e) 
		{
			if(tx != null)
				tx.rollback();
			
			System.out.println(e.getMessage());
		}
		
		finally 
		{
			if(session != null)
				session.close();
		}
	}
	
	/** This function returns Solution from db **/
	public String getSolution(String levelName)
	{
		Session session = null;
		
		try
		{
			session = factory.openSession();
			LevelSolution ls = session.get(LevelSolution.class, levelName);
			
			if(ls != null)
				return ls.getSolution();
		}
		
		catch (HibernateException e) 
		{
			System.out.println(e.getMessage());
		}
		
		finally 
		{
			if(session != null)
				session.close();
		}
		
		return null;
	}
	
	/** This function close factory and service **/
	public void close()
	{
		factory.close();
	}
}
