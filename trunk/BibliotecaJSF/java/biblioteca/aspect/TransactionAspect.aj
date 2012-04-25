package biblioteca.aspect;

import biblioteca.util.FacesUtil;
  
public aspect TransactionAspect { 
	pointcut executeTransaction() : execution(* biblioteca.bc.*BC.*(..));
	before() : executeTransaction() {
		FacesUtil.getRequestEntityManager().getTransaction().begin();
	}
	after() : executeTransaction() {
		FacesUtil.getRequestEntityManager().getTransaction().commit();
	}
}
