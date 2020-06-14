package br.com.modelo.service;

import java.util.List;
import java.util.Optional;

import javax.ejb.Remote;

import br.com.exemplo.modelo.FilmesModel;

@Remote
public interface FilmesServiceRemote {
	
	public List<FilmesModel> buscarTodosFilmes();
	public Optional<FilmesModel> buscarFilmePorId();

}
