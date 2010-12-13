package biblioteca.ui.listener;

import javax.el.MethodExpression;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.component.ActionSource2;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.persistence.NoResultException;

import org.hibernate.exception.ConstraintViolationException;

public class ExceptionActionListener implements ActionListener {

	public void processAction(ActionEvent event) {
		UIComponent source = event.getComponent();
		ActionSource2 actionSource = (ActionSource2) source;
		FacesContext context = FacesContext.getCurrentInstance();

		Application application = context.getApplication();

		Object invokeResult;
		String outcome = null;
		MethodExpression binding;

		binding = actionSource.getActionExpression();
		if( binding != null ) {
			try {
				if( null != (invokeResult = binding.invoke(context.getELContext(), null)) ) {
					outcome = invokeResult.toString();
				}
			} catch(Throwable e ) {
				/*
				 * Pega o contexto de mensagem e deixa apenas as mensagens do tipo ErroMessage. As demais são removidas 
				 * para não serem exibidas
				 * 
				 */
				if (recursiveNoResultExceptionAnaliser(e))
					FacesContext.getCurrentInstance().addMessage("error", new FacesMessage("Nenhum registro encontrado no banco de dados!"));

				if (recursiveConstraintViolationExceptionAnaliser(e))
					FacesContext.getCurrentInstance().addMessage("error", new FacesMessage("Violação de relacionamento entre tabelas do banco!"));
				
				e.getCause().printStackTrace();
			}
		}

		// Retrieve the NavigationHandler instance..
		NavigationHandler navHandler = application.getNavigationHandler();

		// Invoke nav handling..
		navHandler.handleNavigation(context, (null != binding) ? binding.getExpressionString() : null, outcome);

		// Trigger a switch to Render Response if needed
		context.renderResponse();
	}

	/**
	 * Verifica se a pesquisa não encontrou resultados.
	 * @param e
	 * @return boolean
	 */
	private boolean recursiveNoResultExceptionAnaliser(Throwable e) {
		if (e == null)
			return false;
		else if (e instanceof NoResultException)
			return true;
		else
			return recursiveNoResultExceptionAnaliser(e.getCause());
	}

	/**
	 * Verifica se houve violação de contraint.
	 * @param e
	 * @return boolean
	 */
	private boolean recursiveConstraintViolationExceptionAnaliser(Throwable e) {
		if (e == null)
			return false;
		else if (e instanceof ConstraintViolationException)
			return true;
		else
			return recursiveConstraintViolationExceptionAnaliser(e.getCause());
	}
}
