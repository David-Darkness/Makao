package david.makao.service;

import david.makao.model.RestaurantEntity;
import java.util.List;

public interface RestaurantService {
    List<RestaurantEntity> getAllRestaurants();
    List<RestaurantEntity> getRestaurantsByCity(Long cityId);
}

