# Sistema de Cadastros para Biblioteca #

Tarefa 01 da matéria Melhores Práticas de Programação Orientada à Objetos do curso de Pós-graduação em Engenharia de Software da UFRGS.

<br>

<h2>Preparando o ambiente</h2>

<ol><li>Criar uma pasta para o projeto e descompactar o arquivo <a href='http://01-biblioteca.googlecode.com/files/database.zip'>database.zip</a> localizado na área de downloads dentro da pasta criada.<br>
</li><li>Baixar e descompactar o <a href='http://tomcat.apache.org/'>Apache Tomcat 7.0</a> na pasta do projeto.<br>
</li><li>Baixar e instalar o <a href='http://www.eclipse.org/'>Eclipse Helios J2EE</a>
</li><li>Instalar o plugin "AspectJ Development Tools" no Eclipse atraves do Update Site <a href='http://download.eclipse.org/tools/ajdt/36/update'>http://download.eclipse.org/tools/ajdt/36/update</a>. Marque apenas esta opção!<br>
</li><li>Instalar o servidor Tomcat 7 no Eclipse.<br>
</li><li>Instalar o plugin do Subversive através do próprio Update Site do Indigo(<a href='http://download.eclipse.org/releases/helios'>http://download.eclipse.org/releases/helios</a>), pesquisar "Subversive" e marcar a opção "Subversive SVN Team Provider".<br>
</li><li>Após reiniciar o Eclipse, acessando a perspectiva "SVN Repository Exploring" a instalação do Subversive continuará. Marcar a opção "SVN Kit 1.3.5".<br>
</li><li>Adicionar o novo repositório com a URL <a href='https://01-biblioteca.googlecode.com/svn/'>https://01-biblioteca.googlecode.com/svn/</a>
</li><li>Clicar com o botão direito do mouse no projeto BibliotecaJSF e executar o checkout.<br>
</li><li>Executar o banco de dados localizado na pasta "database" através das rotinas "start.bat" ou "start.sh"<br>
</li><li>Alterar para a view J2EE e clicando com o botão direito do mouse no projeto escolher a opção "Run As..." -> "Run on Server"