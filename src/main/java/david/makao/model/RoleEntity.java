package david.makao.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Entidad JPA que representa un rol de usuario en el sistema.
 * Cada rol tiene un nombre único definido en el enum ERole.
 * Se relaciona con los usuarios que poseen ese rol mediante una relación Many-to-Many.
 *
 * <p>Tabla en base de datos: "roles".</p>
 *
 * @author David
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "roles")
public class RoleEntity {

    /**
     * Identificador único del rol (clave primaria).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre del rol, definido por el enum ERole.
     * Es único y no puede ser nulo.
     */
    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private ERole role;

    /**
     * Conjunto de usuarios que poseen este rol.
     * Relación Many-to-Many inversa con UserEntity.
     *
     * Se excluye del método toString y de equals/hashCode para evitar recursividad.
     */
    @ManyToMany(mappedBy = "roles")
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<UserEntity> users = new HashSet<>();
}
