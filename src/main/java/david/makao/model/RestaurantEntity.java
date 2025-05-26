package david.makao.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * Entidad JPA que representa un restaurante.
 * Contiene información básica del restaurante, su ubicación en una ciudad,
 * y la lista de reservas asociadas.
 *
 * <p>Tabla en base de datos: "restaurants".</p>
 *
 * @author David
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "restaurants")
public class RestaurantEntity {

    /**
     * Identificador único del restaurante (clave primaria).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long restaurantId;

    /**
     * Nombre del restaurante.
     */
    private String name;

    /**
     * Dirección física del restaurante.
     */
    private String address;

    /**
     * Información adicional o descripción del restaurante.
     * Campo de texto largo.
     */
    @Column(length = 10000)
    private String info;

    /**
     * Ruta o URL de la imagen representativa del restaurante.
     */
    @Column(name = "image_path")
    private String imagePath;

    /**
     * Ciudad donde se encuentra ubicado el restaurante.
     * Relación Many-to-One con CityEntity.
     */
    @ManyToOne
    @JoinColumn(name = "city_id")
    private CityEntity city;

    /**
     * Lista de reservas realizadas que involucran este restaurante.
     * Relación One-to-Many con ReservationEntity.
     */
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<ReservationEntity> reservations;
}
