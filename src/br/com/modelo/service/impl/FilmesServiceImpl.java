package br.com.modelo.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;

import org.json.JSONArray;
import org.json.JSONObject;

import br.com.exemplo.modelo.FilmesModel;
import br.com.modelo.service.FilmesServiceRemote;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Stateless
public class FilmesServiceImpl implements FilmesServiceRemote, Serializable {

	private static final long serialVersionUID = 1L;

	Logger logger;
	
	List<FilmesModel> listaFilmes;
	FilmesModel filmes;
	
	String url;
	String resquetGET;
	String resquetPOST;
	String contentTypeLabel;
	String contentTypeValue;
	String applicationFormatLabel;
	String applicationFormatValue;
	
	
	public FilmesServiceImpl() {
		super();
		setUrl("http://127.0.0.1:8090/filme");
		setResquetGET("GET");
		setResquetPOST("POST");
		setContentTypeLabel("Content-Type");
		setContentTypeValue("application/json; utf-8");
		setApplicationFormatLabel("Accept");
		setApplicationFormatValue("application/json");
	}
	
	
	@Override
	public List<FilmesModel> buscarTodosFilmes() {
		String retorno = "";
		try {
			URL endpoint = new URL(getUrl()); 
			HttpURLConnection con = (HttpURLConnection) endpoint.openConnection();
			con.setRequestMethod(getResquetGET());
			con.setRequestProperty(getContentTypeLabel(), getContentTypeValue());
			con.setRequestProperty(getApplicationFormatLabel(), getApplicationFormatValue());
			con.setDoOutput(true);
			
//			JSONObject js = new JSONObject();
			OutputStream os = con.getOutputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
			String responseLine = null;
			StringBuilder res = new StringBuilder();
			while((responseLine=br.readLine())!=null) {
				res.append(responseLine.trim());
			}
			
			listaFilmes = new ArrayList<FilmesModel>();
			if (!res.toString().trim().equals("") || !res.toString().trim().equals("[]")) {
				JSONArray response = new JSONArray(res.toString());
				if(response != null && response.length()>0) {
					for (int i = 0; i < response.length(); i++) {
						FilmesModel f = new FilmesModel();
						f.setId(Long.valueOf(String.valueOf(response.getJSONObject(i).get("id"))));
						f.setNome(String.valueOf(response.getJSONObject(i).get("nome")));
						f.setAno(String.valueOf(response.getJSONObject(i).get("ano")));
						f.setDescricao(String.valueOf(response.getJSONObject(i).get("descricao")));
						listaFilmes.add(f);
					}
				}
				
			}
			
			retorno = res.toString();
			
			return listaFilmes;
		} catch (Exception e) {
			logger.log(Level.SEVERE, "FilmesServiceImpl >> buscarTodosFilmes()", e.getMessage());
			return null;
		}
	}

	@Override
	public Optional<FilmesModel> buscarFilmePorId() {
		try {
			
			return null;
		} catch (Exception e) {
			logger.log(Level.SEVERE, "FilmesServiceImpl >> buscarFilmesPorId()", e.getMessage());
			return null;
		}
	}


}
