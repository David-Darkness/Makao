package david.makao.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * Entidad JPA que representa un hotel.
 * Contiene información detallada del hotel, incluyendo su nombre, dirección,
 * descripción, información adicional, ruta de imagen, cantidad de estrellas,
 * y su relación con la ciudad y las reservas asociadas.
 *
 * <p>Tabla en base de datos: "hotels".</p>
 *
 * @author David
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "hotels")
public class HotelEntity {

    /**
     * Identificador único del hotel (clave primaria).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hotelId;

    /**
     * Nombre del hotel.
     */
    private String name;

    /**
     * Dirección física del hotel.
     */
    private String address;

    /**
     * Descripción breve del hotel.
     */
    private String description;

    /**
     * Información adicional extensa sobre el hotel.
     * Se permite un texto largo (hasta 10000 caracteres).
     */
    @Column(length = 10000)
    private String info;

    /**
     * Ruta o nombre del archivo de imagen asociada al hotel.
     */
    private String imagePath;

    /**
     * Cantidad de estrellas del hotel, representando su categoría o calidad.
     */
    private int stars;

    /**
     * Ciudad a la que pertenece el hotel.
     * Relación Many-to-One con CityEntity.
     */
    @ManyToOne
    @JoinColumn(name = "city_id")
    private CityEntity city;

    /**
     * Lista de reservas asociadas a este hotel.
     * Relación One-to-Many con ReservationEntity.
     */
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    private List<ReservationEntity> reservations;
}

