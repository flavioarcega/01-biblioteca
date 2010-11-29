package biblioteca.aspect;

import biblioteca.persistence.dao.AbstractDAO;
import biblioteca.util.FacesUtil;

public aspect TransactionAspect {
	pointcut executeTransaction() : execution(* biblioteca.bc.*BC.*(..));
	before() : executeTransaction() {
		System.out.println("BeforeBC - "+thisJoinPoint.getSignature().getName());
		FacesUtil.getRequestEntityManager().getTransaction().begin();
	}
	after() : executeTransaction() {
		System.out.println("AfterBC - "+thisJoinPoint.getSignature().getName());
		System.out.println("Transacao ativa? "+FacesUtil.getRequestEntityManager().getTransaction().isActive());
		System.out.println("Transacao rollbackOnly? "+FacesUtil.getRequestEntityManager().getTransaction().getRollbackOnly());
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
		System.out.println("BeforeDAO - "+thisJoinPoint.getSignature().getName());
		((AbstractDAO) thisJoinPoint.getTarget()).setEntityManager(FacesUtil.getRequestEntityManager());
	}
	after() : executeDataOperation() {}
}
