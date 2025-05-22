package david.makao.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "cities")
public class CityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cityId;

    private String name;
    private String description;


    @ManyToOne
    @JoinColumn(name = "department_id")
    @ToString.Exclude
    private DepartmentEntity department;

    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL)
    private List<TourPackageEntity> tourPackages;

    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL)
    private List<HotelEntity> hotels;

    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL)
    private List<RestaurantEntity> restaurants;
}

