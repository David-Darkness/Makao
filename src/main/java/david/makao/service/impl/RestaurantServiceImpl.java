package david.makao.service.impl;

import david.makao.model.RestaurantEntity;
import david.makao.repository.RestaurantRepository;
import david.makao.service.RestaurantService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@RequiredArgsConstructor
@Service
public class RestaurantServiceImpl implements RestaurantService {


    @Autowired
    private final RestaurantRepository restaurantRepository;


    @Override
    public List<RestaurantEntity> getAllRestaurants() {
        return (List<RestaurantEntity>) restaurantRepository.findAll();
    }

    @Override
    public List<RestaurantEntity> getRestaurantsByCity(Long cityId) {
        return restaurantRepository.findByCity_CityId(cityId);
    }
    @Override
    public RestaurantEntity getRestaurantById(Long id) {
        return restaurantRepository.findById(id).orElse(null);
    }

    @Override
    public void saveRestaurant(RestaurantEntity restaurante) {
        restaurantRepository.save(restaurante);
    }

    @Override
    public void deleteRestaurant(Long id) {
        restaurantRepository.deleteById(id);
    }



}

