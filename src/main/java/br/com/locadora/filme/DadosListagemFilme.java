package br.com.locadora.filme;

public record DadosListagemFilme(
	Long id,
	String titulo,
	String nomeDiretor) {
	public DadosListagemFilme (Filme filme) {
	    this(filme.getId(),
	    filme.getTitulo(),
	    filme.getNomeDiretor()); 
	}
}
