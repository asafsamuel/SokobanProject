package model.db;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;

import common.Level;

/** MySokobanDbManager - this class is charge of connecting with db (using hibernate) **/
public class MySokobanDbManager 
{
	// Local Variables
	static MySokobanDbManager instance = new MySokobanDbManager();
	SessionFactory factory;

	// C'tor - Singleton Pattern
	private MySokobanDbManager() 
	{
		Configuration configuration = new Configuration();
		configuration.configure();
		factory = configuration.buildSessionFactory();
	}

	// Singleton Pattern
	public static MySokobanDbManager getInstance() 
	{
		return instance;
	}

	/** This function adds new Player to db **/
	public void addPlayer(Player p) 
	{
		Session session = null;
		Transaction tx = null;

		try {
			session = factory.openSession();
			tx = session.beginTransaction();

			session.save(p);
			tx.commit();
		}

		catch (HibernateException ex) 
		{
			if (tx != null)
				tx.rollback();
			System.out.println(ex.getMessage());
		}

		finally 
		{
			if (session != null)
				session.close();
		}
	}

	/** This function returns all Players from db **/
	@SuppressWarnings("unchecked")
	public List<Player> getAllPlayers() 
	{
		List<Player> players = null;
		Session session = null;
		Transaction tx = null;

		try 
		{
			session = factory.openSession();
			tx = session.beginTransaction();

			players = session.createQuery("from Players").list();
			tx.commit();
		}

		catch (HibernateException ex) 
		{
			if (tx != null)
				tx.rollback();
			System.out.println(ex.getMessage());
		}

		finally 
		{
			if (session != null)
				session.close();
		}

		return players;
	}

	/** This function returns last Player in list **/
	public Player getLastRowPlayer() 
	{
		List<Player> players = getAllPlayers();
		return players.get(players.size() - 1);
	}

	/** This function adds new Level to db **/
	public void addLevel(common.Level l) 
	{
		Session session = null;
		Transaction tx = null;

		try 
		{
			session = factory.openSession();
			tx = session.beginTransaction();

			session.save(l);
			tx.commit();
		}

		catch (HibernateException ex) 
		{
			if (tx != null)
				tx.rollback();
			System.out.println(ex.getMessage());
		}

		finally 
		{
			if (session != null)
				session.close();
		}
	}

	/** This function checks if Level is exist in db (if the id is already in used) **/
	@SuppressWarnings("deprecation")
	public boolean LevelisExist(String Id)
	{
		Session session = null;
		Transaction tx = null;

		try
		{
			session = factory.openSession();
			tx = session.beginTransaction();

			Criteria criteria = session.createCriteria(Level.class);
			criteria.add(Restrictions.eq("LevelName", Id));
			criteria.setProjection(Projections.rowCount());
			long count = (Long) criteria.uniqueResult();
			tx.commit();

			if(count != 0)
				return true;

			else
				return false;
		}

		catch (HibernateException ex)
		{
			if (tx != null)
				tx.rollback();
			System.out.println(ex.getMessage());
		}

		finally
		{
			if (session != null)
				session.close();
		}
		return true;
	}

	/** This function adds new Score to db **/
	public void addRecord(PlayersRecords pr) 
	{
		Session session = null;
		Transaction tx = null;

		try 
		{
			session = factory.openSession();
			tx = session.beginTransaction();
			session.saveOrUpdate(pr);
			tx.commit();
		}

		catch (HibernateException ex) 
		{
			if (tx != null)
				tx.rollback();
			System.out.println(ex.getMessage());
		}

		finally 
		{
			if (session != null)
				session.close();
		}
	}

	/** This function get sorted Scores from db (by steps) **/
	@SuppressWarnings("unchecked")
	public List<PlayersRecords> sortLevelScoresBySteps(String LevelName) 
	{
		Session session = null;
		Transaction tx = null;

		try 
		{
			session = factory.openSession();
			tx = session.beginTransaction();

			String hql = "FROM PlayersRecords pr WHERE pr.key.LevelName='" + LevelName
					+ "' ORDER BY pr.PlayerSteps ASC";
			Query<PlayersRecords> query = session.createQuery(hql);
			query.setMaxResults(10);

			return query.list();
		}

		catch (HibernateException ex) 
		{
			if (tx != null)
				tx.rollback();
			System.out.println(ex.getMessage());
		}

		finally 
		{
			if (session != null)
				session.close();
		}

		return null;
	}

	/** This function get sorted Scores from db (by time) **/
	@SuppressWarnings("unchecked")
	public List<PlayersRecords> sortLevelScoresByTime(String LevelName) 
	{
		Session session = null;
		Transaction tx = null;

		try 
		{
			session = factory.openSession();
			tx = session.beginTransaction();

			String hql = "FROM PlayersRecords pr WHERE pr.key.LevelName='" + LevelName + "' ORDER BY pr.PlayerTime ASC";
			Query<PlayersRecords> query = session.createQuery(hql);
			query.setMaxResults(10);

			return query.list();
		}

		catch (HibernateException ex) 
		{
			if (tx != null)
				tx.rollback();
			System.out.println(ex.getMessage());
		}

		finally 
		{
			if (session != null)
				session.close();
		}

		return null;
	}

	/** This function returns Player's Score **/
	@SuppressWarnings("unchecked")
	public List<PlayersRecords> getPlayerScores(String playerId) 
	{
		Session session = null;
		Transaction tx = null;

		try 
		{
			session = factory.openSession();
			tx = session.beginTransaction();

			String hql = "FROM PlayersRecords pr WHERE pr.key.PlayerId='" + playerId + "'";
			Query<PlayersRecords> query = session.createQuery(hql);

			return query.list();
		}

		catch (HibernateException ex) 
		{
			if (tx != null)
				tx.rollback();
			System.out.println(ex.getMessage());
		}

		finally 
		{
			if (session != null)
				session.close();
		}

		return null;
	}
	
	/** This function returns Level from db (name and loaction) **/
	public Level getLevel(String LevelId) 
	{
		Session session = null;
		Transaction tx = null;

		try 
		{
			session = factory.openSession();
			tx = session.beginTransaction();
			
			Level l = (Level) session.createQuery("FROM Levels levels WHERE levels.id.LevelName='" + LevelId + "'").getSingleResult();
			return l;
		}

		catch (HibernateException ex) 
		{
			if (tx != null)
				tx.rollback();
			System.out.println(ex.getMessage());
		}

		finally 
		{
			if (session != null)
				session.close();
		}

		return null;
	}

	/** This functino closes factory variable **/
	public void close() 
	{
		factory.close();
	}

	/** This functino returns all Levels from db **/
	@SuppressWarnings("unchecked")
	public List<Level> getAllLevels() 
	{
		List<Level> levels = null;
		Session session = null;
		Transaction tx = null;

		try 
		{
			session = factory.openSession();
			tx = session.beginTransaction();

			levels = session.createQuery("from Levels").list();
			tx.commit();
		}

		catch (HibernateException ex) 
		{
			if (tx != null)
				tx.rollback();
			System.out.println(ex.getMessage());
		}

		finally 
		{
			if (session != null)
				session.close();
		}

		return levels;
	}
}
