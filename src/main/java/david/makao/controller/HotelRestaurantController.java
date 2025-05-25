package david.makao.controller;

import david.makao.model.CityEntity;
import david.makao.model.HotelEntity;
import david.makao.model.RestaurantEntity;
import david.makao.repository.CityRepository;
import david.makao.repository.HotelRepository;
import david.makao.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HotelRestaurantController {

    private final CityRepository cityRepository;
    private final HotelRepository hotelRepository;
    private final RestaurantRepository restaurantRepository;

    @GetMapping("/hoteles-restaurantes")
    public String showHotelsAndRestaurants(@RequestParam(required = false) Long cityId, Model model) {
        List<CityEntity> cities = (List<CityEntity>) cityRepository.findAll();

        List<HotelEntity> hotels = (cityId != null) ? hotelRepository.findByCity_CityId(cityId) : (List<HotelEntity>) hotelRepository.findAll();
        List<RestaurantEntity> restaurants = (cityId != null) ? restaurantRepository.findByCity_CityId(cityId) : (List<RestaurantEntity>) restaurantRepository.findAll();

        model.addAttribute("cities", cities);
        model.addAttribute("selectedCity", cityId);
        model.addAttribute("hotels", hotels);
        model.addAttribute("restaurants", restaurants);

        return "hoteles-restaurantes";
    }

    @GetMapping("/hoteles/{id}")
    public String hotelDetalle(@PathVariable Long id, Model model) {
        HotelEntity hotel = hotelRepository.findById(id).orElseThrow();
        model.addAttribute("hotel", hotel);
        return "hotel-detalle";
    }

    @GetMapping("/restaurantes/{id}")
    public String restauranteDetalle(@PathVariable Long id, Model model) {
        RestaurantEntity restaurante = restaurantRepository.findById(id).orElseThrow();
        model.addAttribute("restaurante", restaurante);
        return "restaurante-detalle";
    }


}
