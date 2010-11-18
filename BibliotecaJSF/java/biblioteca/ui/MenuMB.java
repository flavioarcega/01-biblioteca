package biblioteca.ui;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean
@ApplicationScoped
public class MenuMB {
	public String getNavigationRule(String navigationEnum) {
		return NavigationEnum.valueOf(navigationEnum).getNavigationRule();
	}
}
