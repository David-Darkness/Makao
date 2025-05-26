package david.makao.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

/**
 * Entidad JPA que representa un usuario del sistema.
 * Contiene datos personales, credenciales y roles asignados para control de acceso.
 *
 * <p>Tabla en base de datos: "users".</p>
 *
 * Las validaciones en campos como email, nombre, usuario y contraseña aseguran integridad y formato correcto.
 * La relación Many-to-Many con roles permite asignar múltiples roles a un usuario.
 *
 * @author David
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class UserEntity {

    /**
     * Identificador único del usuario (clave primaria).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Email del usuario, que además funciona como nombre de usuario para login.
     * Debe ser único y válido.
     */
    @Email
    @NotBlank
    @Size(min = 5, max = 50)
    @Column(unique = true, nullable = false)
    private String email;

    /**
     * Nombre del usuario.
     */
    @NotBlank
    @Size(min = 5, max = 50)
    private String name;

    /**
     * Apellido del usuario.
     */
    @NotBlank
    @Size(min = 5, max = 50)
    private String lastName;

    /**
     * Nombre de usuario para autenticación.
     */
    @NotBlank
    @Size(min = 3, max = 50)
    private String username;

    /**
     * Contraseña cifrada del usuario.
     */
    @NotBlank
    private String password;

    /**
     * Número de teléfono del usuario.
     */
    @NotBlank
    @Size(min = 3, max = 20)
    private String phone;

    /**
     * Número de identificación del usuario.
     */
    @NotBlank
    @Size(min = 3, max = 15)
    private String identificationNumber;

    /**
     * Roles asignados al usuario.
     * Relación Many-to-Many con RoleEntity.
     * Se cargan de forma eager para que los roles estén disponibles en la autenticación.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @Builder.Default
    private Set<RoleEntity> roles = new HashSet<>();
}
