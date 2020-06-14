package br.com.exemplo.bean;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ManagedBean(name="indexBean")
@SessionScoped
public class IndexBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	Logger logger;
	public IndexBean() {}

	public void filmes() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("filmes.xhtml");
		} catch (Exception e) {
			logger.log(Level.SEVERE, "indexBean >> Filmes()", e.getCause());
		}
	}
	
	public void outros() {
		RequestContext.getCurrentInstance().execute("alert('Menu em construção, favor selecione o menu Filmes')");
	}

	
}
