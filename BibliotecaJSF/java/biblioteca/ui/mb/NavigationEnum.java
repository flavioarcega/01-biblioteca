package biblioteca.ui.mb;

/**
 * Em JSF 2.0 nao eh necessario adicionar os navigations ao faces-config.xml.
 * Para navegar de uma pagina a outra basta dizer o nome do arquivo sem a extensao.
 * Desta forma, foi criado o Enum para centralizar a manutencao destes retornos de navegacao.
 */
public enum NavigationEnum {
	SELF			(null),					// Permanecer na mesma pagina
	INDEX			("index"),				// Pagina inicial
	MANTER_AUTOR	("manterAutor"),		// Manutencao de autores
	MANTER_EDITORA	("manterEditora"),		// Manutencao de editoras
	MANTER_LIVRO	("manterLivro"),		// Manutencao de livros
	MANTER_USUARIO	("manterUsuario");		// Manutencao de usuarios
	
	private NavigationEnum(String navigationRule) {
		this.navigationRule = navigationRule;
	}

	private String navigationRule;

	public String getNavigationRule() {
		return navigationRule;
	}

	public void setNavigationRule(String navigationRule) {
		this.navigationRule = navigationRule;
	}
	
	public String toString() {
		return this.getNavigationRule();
	}
}
