package br.com.locadora.filme;

import java.time.Year;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import br.com.locadora.usuario.Usuario;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/home")
public class DashboardController {

	private final FilmeService filmeService;

	public DashboardController(FilmeService filmeService) {
		this.filmeService = filmeService;
	}

	// Dashboard (após login - requer autenticação)
	@GetMapping("/dashboard")
	public String dashboard(HttpSession session, Model model) {
		// Verifica se usuário está logado (simplificado )
		if (session.getAttribute("usuarioLogado") == null) {
			return "redirect:/login";
		}

		// Adiciona dados específicos do dashboard
		model.addAttribute("nomeUsuario", ((Usuario) session.getAttribute("usuarioLogado")).getUser());
		List<Filme> lancamentos = filmeService.buscarLancamentosDoAno();
		List<Filme> destaques = filmeService.buscarFilmesDestaque();
		
		
		model.addAttribute("lancamentos", lancamentos);
		model.addAttribute("destaques", destaques);
		model.addAttribute("anoAtual", Year.now().getValue());
		return "home/dashboard";
	}
}
