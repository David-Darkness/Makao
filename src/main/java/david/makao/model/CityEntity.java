package david.makao.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * Entidad JPA que representa una ciudad.
 * Contiene información básica de la ciudad y sus relaciones con departamentos,
 * paquetes turísticos, hoteles y restaurantes.
 *
 * <p>Tabla en base de datos: "cities".</p>
 *
 * @author David
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "cities")
public class CityEntity {

    /**
     * Identificador único de la ciudad (clave primaria).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cityId;

    /**
     * Nombre de la ciudad.
     */
    private String name;

    /**
     * Descripción o información adicional sobre la ciudad.
     */
    private String description;

    /**
     * Departamento al que pertenece la ciudad.
     * Relación Many-to-One con DepartmentEntity.
     */
    @ManyToOne
    @JoinColumn(name = "department_id")
    @ToString.Exclude
    private DepartmentEntity department;

    /**
     * Lista de paquetes turísticos asociados a esta ciudad.
     * Relación One-to-Many con TourPackageEntity.
     */
    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL)
    private List<TourPackageEntity> tourPackages;

    /**
     * Lista de hoteles ubicados en esta ciudad.
     * Relación One-to-Many con HotelEntity.
     */
    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL)
    private List<HotelEntity> hotels;

    /**
     * Lista de restaurantes ubicados en esta ciudad.
     * Relación One-to-Many con RestaurantEntity.
     */
    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL)
    private List<RestaurantEntity> restaurants;
}

