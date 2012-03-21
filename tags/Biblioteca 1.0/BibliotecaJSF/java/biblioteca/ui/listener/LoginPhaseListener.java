package biblioteca.ui.listener;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import biblioteca.ui.mb.MenuMB;

public class LoginPhaseListener implements PhaseListener {
	private static final long serialVersionUID = 1L;

	public void afterPhase(PhaseEvent event) {
		System.out.println("After...");
		if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().containsKey("menuMB")) {
			MenuMB menuMB = (MenuMB) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("menuMB");
			if (menuMB.getUsuario()!=null) return;
		}
		if (event.getFacesContext().getViewRoot()!=null && event.getFacesContext().getViewRoot().getViewId().indexOf("index")<=0) {
			event.getFacesContext().getViewRoot().setViewId("/public/index.xhtml");
			FacesContext.getCurrentInstance().addMessage("error", new FacesMessage("Efetue o login!"));
		}
	}

	public void beforePhase(PhaseEvent event) {
		System.out.println("Before...");
	}

	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}
	
}
