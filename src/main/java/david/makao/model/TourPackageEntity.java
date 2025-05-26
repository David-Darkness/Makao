package david.makao.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * Entidad JPA que representa un paquete turístico.
 * Contiene información detallada sobre el paquete, como nombre, descripción, precio, duración y ciudad asociada.
 * Además mantiene la relación con las reservas que incluyen este paquete.
 *
 * <p>Tabla en base de datos: "tour_packages".</p>
 *
 * @author David
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tour_packages")
public class TourPackageEntity {

    /**
     * Identificador único del paquete turístico (clave primaria).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long packageId;

    /**
     * Nombre del paquete turístico.
     */
    private String name;

    /**
     * Descripción detallada del paquete.
     * Puede tener una longitud considerable (hasta 10000 caracteres).
     */
    @Column(length = 10000)
    private String description;

    /**
     * Precio del paquete turístico.
     */
    private BigDecimal price;

    /**
     * Duración del paquete en días.
     */
    private int durationDays;

    /**
     * Ruta o nombre del archivo de imagen asociada al paquete.
     */
    private String imagePath;

    /**
     * Ciudad asociada al paquete turístico.
     * Relación Many-to-One con CityEntity.
     */
    @ManyToOne
    @JoinColumn(name = "city_id")
    private CityEntity city;

    /**
     * Lista de reservas que incluyen este paquete turístico.
     * Relación One-to-Many con ReservationEntity.
     */
    @OneToMany(mappedBy = "tourPackage", cascade = CascadeType.ALL)
    private List<ReservationEntity> reservations;
}
