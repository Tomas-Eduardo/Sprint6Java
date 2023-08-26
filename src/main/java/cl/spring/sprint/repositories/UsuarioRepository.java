package cl.spring.sprint.repositories;

import cl.spring.sprint.models.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

    Usuario findByEmail(String email);

    @Query(value = "SELECT * FROM usuario WHERE email = :email LIMIT 1", nativeQuery = true)
    Object findUserByEmail(@Param("email") String email);

}
