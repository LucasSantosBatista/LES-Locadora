package br.com.locadora.filme;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;


@Controller
@RequestMapping("/filme")
public class FilmeController {
	
	@Autowired
	private FilmeRepository repository;
	
	@GetMapping ("/formulario")                  
	public String carregaPaginaFormulario (Long id, Model model){ 
		//System.out.println("Id" + id);
		if(id != null) {
	        var filme = repository.getReferenceById(id);
	        model.addAttribute("filme", filme);
	    }
	    return "filme/formulario";              
	}     
	@GetMapping                                           
	public String carregaPaginaListagem (Model model){    
	    model.addAttribute("lista", repository.findAll(Sort.by("titulo").ascending()));
	    return "filme/listagem";                         
	} 

	@PostMapping
	@Transactional
	public String cadastrar ( @Valid DadosCadastroFilme dados) {
		repository.save(new Filme(dados));
		return   "redirect:filme";      
	}
	
	@PutMapping
	@Transactional
	public String atualizar (DadosAtualizacaoFilme dados) {
		var filme = repository.getReferenceById(dados.id());
		filme.atualizarInformacoes(dados);
		return "redirect:filme";  
	}
	
	@DeleteMapping
	@Transactional
	public String removeFilme (Long id) {
		repository.deleteById (id);
		return "redirect:filme";  
	}
	
}
