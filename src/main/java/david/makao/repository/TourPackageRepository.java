package david.makao.repository;

import david.makao.model.TourPackageEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TourPackageRepository extends CrudRepository<TourPackageEntity, Long> {
    List<TourPackageEntity> findByCity_CityId(Long cityId);
    // Paquetes turísticos por ciudad
}

