package biblioteca.persistence.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

@SuppressWarnings("serial")
public abstract class AbstractDAO<T> implements Serializable {
	private String entityClassName;
	private EntityManager em;
	
	public AbstractDAO(Class<?> entityClass) {
		this.entityClassName = entityClass.getName();
	}
	
	public String getEntityClassName() {
		return this.entityClassName;
	}
	
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}
	
	public EntityManager getEntityManager() {
		return em;
	}
	
	public boolean insertOrUpdate(T obj){
		obj = em.merge(obj);
		em.persist(obj);
		return true;
	}
	
	public boolean delete(T obj) {
		obj = em.merge(obj);
		em.remove(obj);
		em.getTransaction().commit();
		return true;
	}	

	@SuppressWarnings("unchecked")
	public T findById(Integer id){
		Query byIdQuery = em.createQuery("select object(c) from " + entityClassName + " as c where c.id = ?1" );
		byIdQuery.setParameter(1, id);
		return (T) byIdQuery.getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	public T findById(String id){
		Query byIdQuery = em.createQuery("select object(c) from " + entityClassName + " as c where c.id = ?1" );
		byIdQuery.setParameter(1, id);
		return (T) byIdQuery.getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<T> listAll() {
		Query listQuery = em.createQuery("select object(c) from "+ entityClassName +" as c");
		return listQuery.getResultList();
	}
}
