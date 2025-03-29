package br.com.locadora.ator;

import jakarta.validation.constraints.NotBlank;

public record DadosCadastroAtor(
		
		@NotBlank
		String nome,
		String paisOrigem) {

}
