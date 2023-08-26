package cl.spring.sprint.controllers;

import cl.spring.sprint.models.Usuario;
import cl.spring.sprint.repositories.UsuarioRepository;
import cl.spring.sprint.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/logout")
    public String logout() {
        return "/login";
    }

    @GetMapping("/all")
    public String listUser(Model model) {
        List<Usuario> usuario = usuarioService.findAll();
        model.addAttribute("usuario", usuario);
        return "/user/usuariosList";
    }
}
