package david.makao.repository;

import david.makao.model.RestaurantEntity;
import david.makao.model.CityEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RestaurantRepository extends CrudRepository<RestaurantEntity, Long> {
    List<RestaurantEntity> findByCity_CityId(Long cityId);
}

