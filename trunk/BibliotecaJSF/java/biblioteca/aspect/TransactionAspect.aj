package biblioteca.aspect;

import biblioteca.persistence.dao.AbstractDAO;
import biblioteca.util.FacesUtil;

public aspect TransactionAspect {
	pointcut executeTransaction() : execution(* biblioteca.bc.*BC.*(..));
	before() : executeTransaction() {
		FacesUtil.getRequestEntityManager().getTransaction().begin();
	}
	after() : executeTransaction() {
		FacesUtil.getRequestEntityManager().getTransaction().commit();
	}

	// Somente para excessao
	pointcut getDataOperation() : execution(* biblioteca.persistence.dao.*DAO.get*(..));
	before() : getDataOperation() { }
	after()  : getDataOperation() { }
	
	// Somente para excessao
	pointcut setDataOperation() : execution(* biblioteca.persistence.dao.*DAO.set*(..));
	before() : setDataOperation() { }
	after()  : setDataOperation() { }

	// Regra aplicada aos demais metodos que nao atendem as excessoes
	pointcut executeDataOperation() : execution(* biblioteca.persistence.dao.*DAO.*(..));
	before() : executeDataOperation() {
		((AbstractDAO) thisJoinPoint.getTarget()).setEntityManager(FacesUtil.getRequestEntityManager());
	}
	after() : executeDataOperation() {}
}
