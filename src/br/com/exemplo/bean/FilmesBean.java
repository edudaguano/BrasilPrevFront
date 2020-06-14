package br.com.exemplo.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;

import org.primefaces.context.RequestContext;

import br.com.exemplo.modelo.FilmesModel;
import br.com.modelo.service.FilmesServiceRemote;
import br.com.modelo.service.impl.FilmesServiceImpl;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@SessionScoped
@ManagedBean(name = "filmesBean")
public class FilmesBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Logger logger;
	
	private FilmesServiceRemote filmesService;
	private List<FilmesModel> listaFilmes;
	private FilmesModel selectedFilme;
	private FilmesModel filme;
	
	private boolean mostrarGrid;
	private boolean mock;
/*--------------------------- INICIALIZADORES --------------------------------*/
	
	public FilmesBean() {
		super();
		inicializarVariaveisLocais();
		inicializarConsultasExternas();
	}
	
	private void inicializarVariaveisLocais() {
		setMostrarGrid(false);
		setMock(false);
	}
	
	private void inicializarConsultasExternas() {
		selectedFilme = null;
	}
	

/*--------------------------- MÉTODOS DA TELA --------------------------------*/

	
	public void pesquisar() {
		try {
			if(mock) {
				setMostrarGrid(true);
				listaFilmes = new ArrayList<FilmesModel>();
				for (int i = 0; i < 10; i++) {
					selectedFilme = new FilmesModel();
					selectedFilme.setId(i);
					selectedFilme.setNome(String.valueOf(Math.random()));
					selectedFilme.setAno(String.valueOf(Math.random()));
					selectedFilme.setDescricao(String.valueOf(Math.random()));
					listaFilmes.add(selectedFilme);
				}
				selectedFilme = null;
			} else {
				filmesService = new FilmesServiceImpl();
				listaFilmes = filmesService.buscarTodosFilmes();
				if(listaFilmes != null && !listaFilmes.isEmpty()) {
					setMostrarGrid(true);
				}
			}
		} catch (Exception e) {
			RequestContext.getCurrentInstance().execute("alert('Erro ao realizar pesquisa:"+e.getCause()+"')");
			logger.log(Level.SEVERE, "filmesBean >> pesquisar", e.getMessage());
		}
	}
	
	public void limpar() {
		try {
			setMostrarGrid(mostrarGrid);
			listaFilmes = new ArrayList<FilmesModel>();
			filme = new FilmesModel();
			selectedFilme = null;
		} catch (Exception e) {
			RequestContext.getCurrentInstance().execute("alert('Erro ao realizar pesquisa:"+e.getCause()+"')");
			logger.log(Level.SEVERE, "filmesBean >> limpar", e.getMessage());
		}
	}
	
	
}
