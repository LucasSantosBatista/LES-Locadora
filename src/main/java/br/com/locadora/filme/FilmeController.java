package br.com.locadora.filme;

import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import br.com.locadora.usuario.Usuario;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;


@Controller
@RequestMapping("/filme")
public class FilmeController {

	@Autowired
	private FilmeRepository filmeRepository;

	// Para editar filme com ID
	@GetMapping ("/formulario/{id}")                  
	public String carregaPaginaFormulario (@PathVariable("id") Long id, 
			HttpSession session,
			RedirectAttributes redirectAttributes, 
			Model model){ 

		Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
		try {
			Filme filme = filmeRepository.findById(id)
					.orElseThrow(() -> new EntityNotFoundException("Filme não encontrado"));
			model.addAttribute("filme", filme);
			model.addAttribute("usuarioLogado", usuario.getUser()); // Adicione esta linha para identificar o usuário
			model.addAttribute("activePage", "filmes");
			return "filme/formulario";

		} catch (EntityNotFoundException e) {
			redirectAttributes.addFlashAttribute("error", e.getMessage());
			// Adicione as linhas para identificar o usuário
			
			session.setAttribute("usuarioLogado", usuario); 
			return "redirect:/filme";
		}
	}   

	// Para criação sem passar o ID
	@GetMapping("/formulario")
	public String novoFilme(Model model,  HttpSession session) {

		// Adicione as linhas para identificar o usuário
		Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
		model.addAttribute("usuarioLogado", usuario.getUser()); 
		model.addAttribute("activePage", "filmes");
		model.addAttribute("filme", new Filme());
		return "filme/formulario";
	}
	//listar todos os filmes 
	@GetMapping                                           
	public String carregaPaginaListagem (Model model, HttpSession session){  
		// Adicione as linhas para identificar o usuário
		if (session.getAttribute("usuarioLogado") == null) {
			return "redirect:/login";
		}
		Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");

		//System.out.println("Listagem usuário Logado " + usuario.getUser());
		model.addAttribute("usuarioLogado", usuario.getUser()); 
		model.addAttribute("activePage", "filmes");

		model.addAttribute("lista", filmeRepository.findAll(Sort.by("titulo").ascending()));
		return "filme/listagem";                         
	} 

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(LocalDate.class, 
				new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) throws IllegalArgumentException {
				setValue(LocalDate.parse(text, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
			}

			@Override
			public String getAsText() {
				LocalDate date = (LocalDate) getValue();
				return date != null ? date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : "";
			}
		});
	}

	@GetMapping("/{id}/desabilitado/{status}")
	public String updateFilmeDesabilitado(@PathVariable("id") Long id, 
			@PathVariable("status") boolean desabilitado,
			RedirectAttributes redirectAttributes, 
			HttpSession session) {

		try {
			filmeRepository.atualizaStatus(id, desabilitado);

			String status = desabilitado ? "habilitado" : "desabilitado";
			String message = "O filme id=" + id + " foi " + status;

			redirectAttributes.addFlashAttribute("message", message);
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
		}
		// Adicione as linhas para identificar o usuário
		Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
		session.setAttribute("usuarioLogado", usuario); 
		return "redirect:/filme";
	}

	//	@PostMapping("/salvar")
	//	@Transactional
	//	public String salvarFilme(@ModelAttribute Filme filme,
	//			RedirectAttributes redirectAttributes) {
	//		filmeRepository.save(filme);
	//		String mensagem = filme.getId() != null ? 
	//				"Filme atualizado com sucesso!" : "Filme criado com sucesso!";
	//
	//		redirectAttributes.addFlashAttribute("message", mensagem);
	//		return "redirect:/filme";
	//	}

	// Método para gravar/atualizar o formulário 
	@PostMapping("/salvar")
	public String salvarFilme(@Valid @ModelAttribute("filme") Filme filme,
			BindingResult result, HttpSession session, 
			RedirectAttributes redirectAttributes) {

		// Adicione as linhas para identificar o usuário
		Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
		session.setAttribute("usuarioLogado", usuario); 
		// Validação de erros
		if (result.hasErrors()) {
			return "filme/formulario";
		}

		try {
			filmeRepository.save(filme);

			String mensagem = filme.getId() != null ? 
					"Filme '" + filme.getTitulo() + "' atualizado com sucesso!" :
						"Filme '" + filme.getTitulo() + "' criado com sucesso!";

			redirectAttributes.addFlashAttribute("message", mensagem);
			return "redirect:/filme";

		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "Erro ao salvar filme: " + e.getMessage());
			return "redirect:/filme/formulario" + (filme.getId() != null ? "/" + filme.getId() : "");
		}
	}

	@GetMapping("/delete/{id}")
	@Transactional
	public String deleteTutorial(@PathVariable("id") Long id, Model model, HttpSession session,  RedirectAttributes redirectAttributes) {
		try {
			filmeRepository.deleteById(id);
			redirectAttributes.addFlashAttribute("message", "O filme " + id + " foi apagado!");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
		}
		Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
		session.setAttribute("usuarioLogado", usuario); 
		return "redirect:/filme";
	}
}