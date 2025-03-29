package br.com.locadora.ator;

public record DadosListagemAtor(Long id, String nome, String paisOrigem) {
	public DadosListagemAtor(Ator ator) {
		this(ator.getId(), ator.getNome(), ator.getPaisOrigem());
	}
}
