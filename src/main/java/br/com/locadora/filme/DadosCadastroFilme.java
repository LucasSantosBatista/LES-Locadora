package br.com.locadora.filme;

import jakarta.validation.constraints.NotBlank;

public record DadosCadastroFilme(
		
		@NotBlank
		String titulo,
		String nomeDiretor) {

}
