package biblioteca.aspect;

public aspect ExceptionAspect {
	pointcut printStackTrace(): execution(* *.*(..));
	
	//advice que pega a Exception no retorno da chamada do método printStackTrace dessa Exception  
	after(Exception e) returning: call(* *.printStackTrace(..)) && target(e) {         
		//auqluer coisa depois
	}  
}
