package br.com.locadora.ator;

import java.io.Serializable;
import java.util.List;

import br.com.locadora.filme.Filme;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity
public class Ator implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String nome;

	private String paisOrigem;

	@ManyToMany
	@JoinTable(name = "ator_filme", joinColumns = @JoinColumn("ator_id"), inverseJoinColumns = @JoinColumn(name = "filme_id"))
	private List<Filme> filmes;

	public Ator() {

	}

	public Ator(DadosCadastroAtor dados) {
		this.nome = dados.nome();
		this.paisOrigem = dados.paisOrigem();
	}

	public void atualizarInformacoes(DadosAtualizacaoAtor dados) {
		if (dados.nome() != null) {
			this.nome = dados.nome();
		}
		if (dados.paisOrigem() != null) {
			this.paisOrigem = dados.paisOrigem();
		}

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getPaisOrigem() {
		return paisOrigem;
	}

	public void setPaisOrigem(String paisOrigem) {
		this.paisOrigem = paisOrigem;
	}

}
