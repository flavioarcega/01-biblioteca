package biblioteca.persistence.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import biblioteca.util.JpaUtil;

@SuppressWarnings("serial")
public abstract class AbstractDAO<T> implements Serializable {
	protected String entityClassName;
	
	@SuppressWarnings("rawtypes")
	public AbstractDAO(Class entityClass) {
		entityClassName = entityClass.getName();
	}
	
	public boolean insertOrUpdate(T obj){
		EntityManager em = JpaUtil.getEntityManager();
		try {
			em.getTransaction().begin();
			if (em.contains(obj))
				obj = em.merge(obj);
			else 
				em.persist(obj);
			em.getTransaction().commit();
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			em.getTransaction().rollback();
			return false;
		}
	}
	
	public boolean delete(T obj){
		EntityManager em = JpaUtil.getEntityManager();
		try {
			em.getTransaction().begin();
			obj = em.merge(obj);
			em.remove(obj);
			em.getTransaction().commit();
			return true;
		} catch (Exception ex){
			ex.printStackTrace();
			em.getTransaction().rollback();
			return false;
		}
	}	

	@SuppressWarnings("unchecked")
	public T findById(Integer id){
		EntityManager em = JpaUtil.getEntityManager();
		try {
			Query byIdQuery = em.createQuery("select object(c) from " + entityClassName + " as c where c.id = ?1" );
			byIdQuery.setParameter(1, id);
			return (T) byIdQuery.getSingleResult();
		} catch(Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public T findById(String id){
		EntityManager em = JpaUtil.getEntityManager();
		try {
			Query byIdQuery = em.createQuery("select object(c) from " + entityClassName + " as c where c.id = ?1" );
			byIdQuery.setParameter(1, id);
			return (T) byIdQuery.getSingleResult();
		} catch(Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<T> getList() {
		EntityManager em = JpaUtil.getEntityManager();
		try {
			Query listQuery = em.createQuery("select object(c) from "+ entityClassName +" as c");
			return listQuery.getResultList();
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
