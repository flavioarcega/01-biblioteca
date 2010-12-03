package biblioteca.ui.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import biblioteca.persistence.dao.EditoraDAO;

@FacesConverter(value="editoraConverter")
public class EditoraConverter implements Converter {
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String id) {
		return (new EditoraDAO()).findById(Integer.valueOf(id));
	}

	public String getAsString(FacesContext arg0, UIComponent arg1, Object id) {
		return String.valueOf(id);
	}

}
