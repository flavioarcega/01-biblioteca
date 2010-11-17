package biblioteca.ui;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@SessionScoped
@ManagedBean(name="menuInicialMB")
public class MenuInicialMB {
	private static final String NAVIGATION_RULE = "menuInicial";

	/**
	 * Retorna o navigation rule do menu.
	 * @return
	 */
	public String menuInicial() {
		return NAVIGATION_RULE;
	}
}
