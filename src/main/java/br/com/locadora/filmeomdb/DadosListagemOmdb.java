package br.com.locadora.filmeomdb;

public record DadosListagemOmdb(
	
	String titulo,
	String ano,
	String imdbID,
	String poster
	) {
	public DadosListagemOmdb (FilmeOmdb filmeOmdb) {
	    this(filmeOmdb.getImdbID(), 
	    		filmeOmdb.getAno(), 
	    		filmeOmdb.getTitulo(),
	    		filmeOmdb.getPoster()
	    	); 
	}
}
