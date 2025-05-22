package david.makao.controller.admin;

import david.makao.model.CityEntity;
import david.makao.model.HotelEntity;
import david.makao.repository.CityRepository;
import david.makao.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Controller
@RequestMapping("/admin/hoteles")
public class HotelAdminController {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private CityRepository cityRepository;

    private final Path uploadPath = Paths.get("src/main/resources/static/images/imagesHotel");

    @GetMapping
    public String listarHoteles(Model model) {
        model.addAttribute("hoteles", hotelRepository.findAll());
        return "admin/hoteles";
    }

    @GetMapping("/nuevo")
    public String nuevoHotel(Model model) {
        model.addAttribute("hotel", new HotelEntity());
        model.addAttribute("ciudades", cityRepository.findAll());
        return "admin/hotel-form";
    }

    @PostMapping("/guardar")
    public String guardarHotel(
            @RequestParam(required = false) Long hotelId,
            @RequestParam String name,
            @RequestParam String address,
            @RequestParam String description,
            @RequestParam String info,
            @RequestParam int stars,
            @RequestParam Long cityId,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile
    ) throws IOException {

        HotelEntity hotel = (hotelId != null)
                ? hotelRepository.findById(hotelId).orElse(new HotelEntity())
                : new HotelEntity();

        hotel.setName(name);
        hotel.setAddress(address);
        hotel.setDescription(description);
        hotel.setInfo(info);
        hotel.setCity(cityRepository.findById(cityId).orElse(null));
        hotel.setStars(stars);

        // Guardar imagen
        if (imageFile != null && !imageFile.isEmpty()) {
            if (imageFile.getSize() > 1_000_000) {
                throw new IllegalArgumentException("La imagen no puede superar 1 MB.");
            }

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String filename = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
            Path filePath = uploadPath.resolve(filename);
            Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            hotel.setImagePath(filename);
        } else if (hotelId != null) {
            HotelEntity existente = hotelRepository.findById(hotelId).orElse(null);
            if (existente != null) {
                hotel.setImagePath(existente.getImagePath());
            }
        }

        hotelRepository.save(hotel);
        return "redirect:/admin/hoteles";
    }

    @GetMapping("/editar/{id}")
    public String editarHotel(@PathVariable Long id, Model model) {
        HotelEntity hotel = hotelRepository.findById(id).orElse(null);
        if (hotel == null) return "redirect:/admin/hoteles";

        model.addAttribute("hotel", hotel);
        model.addAttribute("ciudades", cityRepository.findAll());
        return "admin/hotel-form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarHotel(@PathVariable Long id) {
        hotelRepository.deleteById(id);
        return "redirect:/admin/hoteles";
    }
}
