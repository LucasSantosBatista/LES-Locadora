package br.com.locadora.usuario;

import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.locadora.usuario.exception.ServiceExc;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class UsuarioController{
	
	private final UsuarioService serviceUsuario;
    
    // Injeção por construtor (recomendado em vez de @Autowired)
    public UsuarioController(UsuarioService serviceUsuario) {
        this.serviceUsuario = serviceUsuario;
    }
    // Rota raiz -> redireciona para login
    @GetMapping("/")
    public String home() {
        return "redirect:/login";
    }
    
    @GetMapping("/cadastro")
    public String mostrarFormularioCadastro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "login/cadastro";
    }
    
    @PostMapping("/cadastro")
    public String processarCadastro(@Valid Usuario usuario, 
                                  BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            return "login/cadastro";
        }
        
        serviceUsuario.salvarUsuario(usuario);
        return "redirect:/login";
    }
    
    @GetMapping("/login")
    public String mostrarFormularioLogin(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "login/login";
    }
    
    @PostMapping("/login")
    public String processarLogin(@Valid Usuario usuario,
    		BindingResult bindingResult,
    		HttpSession session, 
    		RedirectAttributes redirectAttributes) 
    				throws NoSuchAlgorithmException, ServiceExc {

    	if (bindingResult.hasErrors()) {
    		return "login/login";
    	}

    	Usuario userLogin = serviceUsuario.loginUser(
    			usuario.getUser(), 
    			Util.md5(usuario.getSenha())
    			);

    	if (userLogin == null) {
    		redirectAttributes.addFlashAttribute("mensagemErro", 
    				"Usuário não encontrado. Tente novamente");
    		return "redirect:/login";
    	}
    	
    	session.setAttribute("usuarioLogado", userLogin); // Adicione esta linha para identificar o usuário
    	return "redirect:/home/dashboard";
    }
    
    @GetMapping("/logout")
    public String processarLogout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}