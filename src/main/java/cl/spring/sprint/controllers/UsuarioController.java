package cl.spring.sprint.controllers;

import cl.spring.sprint.dtos.UsuarioDTO;
import cl.spring.sprint.models.RolEnum;
import cl.spring.sprint.models.Usuario;
import cl.spring.sprint.repositories.UsuarioRepository;
import cl.spring.sprint.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/usuarios")
    public List<UsuarioDTO> getClients() {
        return this.usuarioService.allUsers().stream().map(UsuarioDTO::new).collect(toList());
    }
    @GetMapping("/usuarios/{id}")
    public UsuarioDTO getClient(@PathVariable Long id) {
        return this.usuarioRepository.findById(id).map(UsuarioDTO::new).orElse(null);
    }
    @GetMapping("/usuarios/current")
    public UsuarioDTO getClient(Authentication authentication) {
        Usuario client = this.usuarioService.findByEmail(authentication.getName());
        return new UsuarioDTO(client);
    }

    @PostMapping("/usuarios")
    public ResponseEntity<Object> createUsuario(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String perfil,
            @RequestParam String email,
            @RequestParam String password
    ) {

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        if (usuarioRepository.findByEmail(email) != null) {
            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);
        }

        RolEnum rol = RolEnum.valueOf(perfil);

        Usuario newUser = usuarioService.createUser(firstName, lastName, email, password, rol);

        // You might want to perform additional checks or actions here
        if (newUser != null) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Failed to create user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
