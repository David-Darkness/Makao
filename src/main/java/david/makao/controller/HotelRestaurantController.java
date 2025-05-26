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

/**
 * Controlador para mostrar los hoteles y restaurantes disponibles en el sitio web.
 *
 * <p>Permite visualizar todos los hoteles y restaurantes, o filtrarlos por ciudad.
 * También gestiona las vistas de detalle para hoteles y restaurantes individuales.</p>
 *
 * @author David
 * @version 1.0
 */
@Controller
@RequiredArgsConstructor
public class HotelRestaurantController {

    private final CityRepository cityRepository;
    private final HotelRepository hotelRepository;
    private final RestaurantRepository restaurantRepository;

    /**
     * Muestra la vista combinada de hoteles y restaurantes.
     *
     * <p>Si se proporciona un ID de ciudad, se filtran los hoteles y restaurantes
     * correspondientes a dicha ciudad. Si no se proporciona, se muestran todos.</p>
     *
     * @param cityId ID de la ciudad seleccionada (opcional)
     * @param model Modelo para enviar datos a la vista
     * @return Nombre de la vista "hoteles-restaurantes"
     */
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

    /**
     * Muestra la vista de detalle de un hotel específico.
     *
     * @param id ID del hotel a consultar
     * @param model Modelo para enviar datos a la vista
     * @return Nombre de la vista "hotel-detalle"
     */
    @GetMapping("/hoteles/{id}")
    public String hotelDetalle(@PathVariable Long id, Model model) {
        HotelEntity hotel = hotelRepository.findById(id).orElseThrow();
        model.addAttribute("hotel", hotel);
        return "hotel-detalle";
    }

    /**
     * Muestra la vista de detalle de un restaurante específico.
     *
     * @param id ID del restaurante a consultar
     * @param model Modelo para enviar datos a la vista
     * @return Nombre de la vista "restaurante-detalle"
     */
    @GetMapping("/restaurantes/{id}")
    public String restauranteDetalle(@PathVariable Long id, Model model) {
        RestaurantEntity restaurante = restaurantRepository.findById(id).orElseThrow();
        model.addAttribute("restaurante", restaurante);
        return "restaurante-detalle";
    }
}
