package biblioteca.util;

import java.util.Map;

import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

public class FacesUtil {
	private static final String REQUEST_ENTITY_MANAGER_KEY = "ENTITY_MANAGER";
	
	public static EntityManager getRequestEntityManager() {
		Map<String, Object> requestMap = FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
		EntityManager em = (EntityManager) requestMap.get(REQUEST_ENTITY_MANAGER_KEY);
		if (em!=null && em.getTransaction().isActive() && em.getTransaction().getRollbackOnly()) em.close();
		if (em==null || !em.isOpen()) {
			em = JpaUtil.createEntityManager();
			requestMap.put(REQUEST_ENTITY_MANAGER_KEY, em);
		}
		return em;
	}
}
