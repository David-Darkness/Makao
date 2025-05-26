package david.makao.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * Entidad JPA que representa un departamento.
 * Contiene información básica del departamento y la lista de ciudades que pertenecen a él.
 *
 * <p>Tabla en base de datos: "departments".</p>
 *
 * @author David
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "departments")
public class DepartmentEntity {

    /**
     * Identificador único del departamento (clave primaria).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long departmentId;

    /**
     * Nombre del departamento.
     */
    private String name;

    /**
     * Descripción o información adicional sobre el departamento.
     */
    private String description;

    /**
     * Lista de ciudades que pertenecen a este departamento.
     * Relación One-to-Many con CityEntity.
     */
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<CityEntity> cities;
}
