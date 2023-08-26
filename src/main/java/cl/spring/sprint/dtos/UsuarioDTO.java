package cl.spring.sprint.dtos;

import cl.spring.sprint.models.Usuario;


public class UsuarioDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String rol;

    public UsuarioDTO() {}

    public UsuarioDTO(Long id, String firstName, String lastName, String email, String rol) {
        super();
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.rol = rol;
    }

    public UsuarioDTO(Usuario client) {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public static UsuarioDTO fromEntity(Usuario usuario) {
        return new UsuarioDTO(
                usuario.getId(),
                usuario.getFirstName(),
                usuario.getLastName(),
                usuario.getEmail(),
                usuario.getRol().name()
        );
    }
}
