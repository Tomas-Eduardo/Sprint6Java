package cl.spring.sprint.services;

import cl.spring.sprint.models.RolEnum;
import cl.spring.sprint.models.Usuario;
import cl.spring.sprint.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Asegúrate de inyectar el PasswordEncoder

    public List<Usuario> allUsers() {

        return (List<Usuario>) usuarioRepository.findAll();
    }

    public Usuario findByEmail(String email) {
        Object result = usuarioRepository.findUserByEmail(email);
        if (result != null) {
            Object[] row = (Object[]) result;
            Usuario usuario = new Usuario();
            usuario.setId(((Number) row[0]).longValue());
            usuario.setEmail((String) row[1]);
            usuario.setFirstName((String) row[2]);
            usuario.setLastName((String) row[3]);
            usuario.setPassword((String) row[4]);
            Integer rolValue = (Integer) row[5];
            if (rolValue != null) {
                RolEnum[] roles = RolEnum.values();
                if (rolValue >= 0 && rolValue < roles.length) {
                    usuario.setRol(roles[rolValue]);
                }
            }
            return usuario;
        } else {
            return null;
        }
    }

    public List<Usuario> findAll() {

        return (List<Usuario>) usuarioRepository.findAll();
    }

    public Usuario createUser(String firstName, String lastName, String email, String password, RolEnum rol) {
        Usuario newUser = new Usuario();
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setEmail(email);
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setRol(rol);

        return usuarioRepository.save(newUser);
    }

    public void deleteUsuarioById(Long id) {
        usuarioRepository.deleteById(id);
    }

    public Usuario findById(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public Usuario editUser(Long id, String firstName, String lastName, String email, String password, RolEnum rol) {
        Usuario user = usuarioRepository.findById(id).orElse(null);
        if (user != null) {
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode(password));
            user.setRol(rol);
            return usuarioRepository.save(user);
        } else {
            return null;
        }
    }
}
