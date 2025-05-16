package david.makao.repository;

import david.makao.model.HotelEntity;
import david.makao.model.CityEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends CrudRepository<HotelEntity, Long> {
    List<HotelEntity> findByCity_CityId(Long id);

    // Hoteles por ciudad
}

