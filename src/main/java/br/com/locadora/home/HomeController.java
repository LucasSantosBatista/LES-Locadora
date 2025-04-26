package br.com.locadora.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	 // Página pública (opcional)
    @GetMapping("/home")
    public String homePublica() {
        return "home/publica"; // Página genérica (sem login necessário)
    }


}
