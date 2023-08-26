package cl.spring.sprint.controllers;


import cl.spring.sprint.models.Usuario;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new Usuario());
        return "login";
    }
    @GetMapping("/admin")
    public String adminRedirect() {
        return "admin/admin";
    }
    @GetMapping("/user")
    public String userRedirect() {
        return "user/user";
    }
    @GetMapping("/visitante")
    public String visitanteRedirect() {
        return "visitante/visitante";
    }
}
