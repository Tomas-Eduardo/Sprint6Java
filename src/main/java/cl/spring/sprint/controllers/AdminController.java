package cl.spring.sprint.controllers;

import cl.spring.sprint.models.Usuario;
import cl.spring.sprint.repositories.UsuarioRepository;
import cl.spring.sprint.services.UsuarioService;
import cl.spring.sprint.models.RolEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private UsuarioService usuarioService;


    @GetMapping("/crear")
    public String crearUsuario() {
        return "/admin/crearUsuario";
    }

    @GetMapping("/logout")
    public String logout() {
        return "/login";
    }

    @GetMapping("/edit/{id}")
    public String editUsuario(@PathVariable Long id, Model model) {
        Usuario usuario = usuarioService.findById(id);
        model.addAttribute("usuario", usuario);
        return "/admin/editarUsuario";
    }

    @GetMapping("/all")
    public String listAdmin(Model model) {
        List<Usuario> usuario = usuarioService.findAll();
        model.addAttribute("usuario", usuario);
        return "/admin/usuariosList";
    }

    @GetMapping("/delete/{id}")
    public String deleteUsuarioById(@PathVariable Long id) {
        usuarioService.deleteUsuarioById(id);
        return "redirect:/admin/all";
    }


    @GetMapping("/edit")
    public String editUsuario(@ModelAttribute Usuario usuario,
                              @RequestParam Long id,
                              @RequestParam String firstName,
                              @RequestParam String lastName,
                              @RequestParam String email,
                              @RequestParam String password,
                              @RequestParam String perfil,
                              Model model) {

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            model.addAttribute("error", "Missing data");
            return "edit"; // Retorna a la página de creación con un mensaje de error
        }

        if (usuarioRepository.findByEmail(email) != null) {
            model.addAttribute("error", "Email already in use");
            return "edit"; // Retorna a la página de creación con un mensaje de error
        }

        RolEnum rol = RolEnum.valueOf(perfil);

        usuarioService.findById(id);

        Usuario newUser = usuarioService.editUser(id,firstName, lastName, email, password, rol);

        if (newUser != null) {
            return "redirect:/admin/all"; // Redirige a una página de éxito
        } else {
            model.addAttribute("error", "Failed to create user");
            return "edit"; // Retorna a la página de creación con un mensaje de error
        }
    }


    @GetMapping("/create")
    public String createUsuario(@ModelAttribute Usuario usuario,
                                @RequestParam String firstName,
                                @RequestParam String lastName,
                                @RequestParam String email,
                                @RequestParam String password,
                                @RequestParam String perfil,
                                Model model) {

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            model.addAttribute("error", "Missing data");
            return "create"; // Retorna a la página de creación con un mensaje de error
        }

        if (usuarioRepository.findByEmail(email) != null) {
            model.addAttribute("error", "Email already in use");
            return "create"; // Retorna a la página de creación con un mensaje de error
        }

        RolEnum rol = RolEnum.valueOf(perfil);

        Usuario newUser = usuarioService.createUser(firstName, lastName, email, password, rol);

        if (newUser != null) {
            return "redirect:/admin/all"; // Redirige a una página de éxito
        } else {
            model.addAttribute("error", "Failed to create user");
            return "create"; // Retorna a la página de creación con un mensaje de error
        }
    }
}
