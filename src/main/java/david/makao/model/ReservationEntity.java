package david.makao.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Entidad JPA que representa una reserva realizada por un usuario.
 * Contiene información sobre fechas de reserva, número de personas,
 * precio total y las relaciones con usuario, paquete turístico, hotel y restaurante.
 *
 * <p>Tabla en base de datos: "reservations".</p>
 *
 * @author David
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "reservations")
public class ReservationEntity {

    /**
     * Identificador único de la reserva (clave primaria).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;

    /**
     * Fecha en la que se realizó la reserva.
     */
    private LocalDate reservationDate;

    /**
     * Fecha de inicio del paquete turístico reservado.
     */
    private LocalDate startDate;

    /**
     * Fecha de fin del paquete turístico reservado.
     */
    private LocalDate endDate;

    /**
     * Número de personas para la reserva. No puede ser nulo.
     */
    @Column(nullable = false)
    private int numberOfPeople;

    /**
     * Precio total calculado para la reserva.
     */
    private BigDecimal TotalPrice;

    /**
     * Usuario que realizó la reserva.
     * Relación Many-to-One con UserEntity.
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    /**
     * Paquete turístico reservado.
     * Relación Many-to-One con TourPackageEntity.
     */
    @ManyToOne
    @JoinColumn(name = "package_id")
    private TourPackageEntity tourPackage;

    /**
     * Hotel asociado a la reserva.
     * Relación Many-to-One con HotelEntity.
     */
    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private HotelEntity hotel;

    /**
     * Restaurante asociado a la reserva.
     * Relación Many-to-One con RestaurantEntity.
     */
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private RestaurantEntity restaurant;
}
