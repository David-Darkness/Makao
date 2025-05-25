package david.makao.repository;

import david.makao.model.TourPackageEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TourPackageRepository extends CrudRepository<TourPackageEntity, Long> {

    // Buscar paquetes turísticos por el id de la ciudad (relación ManyToOne con City)
    List<TourPackageEntity> findByCity_CityId(Long cityId);

    // Para obtener todos los paquetes turísticos, puedes usar findAll() que viene con CrudRepository
    List<TourPackageEntity> findAll();

}

