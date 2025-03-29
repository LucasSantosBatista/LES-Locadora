package br.com.locadora.ator;

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
@RequestMapping("/ator")
public class AtorController {
	
	@Autowired
	private AtorRepository repository;
	
	@GetMapping ("/formulario")                  
	public String carregaPaginaFormulario (Long id, Model model){ 
		//System.out.println("Id" + id);
		if(id != null) {
	        var ator = repository.getReferenceById(id);
	        model.addAttribute("ator", ator);
	    }
	    return "ator/formulario";              
	}     
	@GetMapping                                           
	public String carregaPaginaListagem (Model model){    
	    model.addAttribute("lista", repository.findAll(Sort.by("nome").ascending()));
	    return "ator/listagem";                         
	} 

	@PostMapping
	@Transactional
	public String cadastrar ( @Valid DadosCadastroAtor dados) {
		repository.save(new Ator(dados));
		return   "redirect:filme";      
	}
	
	@PutMapping
	@Transactional
	public String atualizar (DadosAtualizacaoAtor dados) {
		var filme = repository.getReferenceById(dados.id());
		filme.atualizarInformacoes(dados);
		return "redirect:filme";  
	}
	
	@DeleteMapping
	@Transactional
	public String removeAtor (Long id) {
		repository.deleteById (id);
		return "redirect:filme";  
	}
	
}
