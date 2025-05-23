package david.makao.controller.admin;

import david.makao.model.CityEntity;
import david.makao.model.RestaurantEntity;
import david.makao.repository.CityRepository;
import david.makao.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Controller
@PreAuthorize("hasAnyRole('ADMIN', 'COLLABORATOR')")
@RequestMapping("/admin/restaurantes")
public class RestaurantAdminController {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private CityRepository cityRepository;

    private final Path uploadPath = Paths.get("src/main/resources/static/images/imagesRestaurante");

    @PreAuthorize("hasAnyRole('ADMIN', 'COLLABORATOR')")
    @GetMapping
    public String listarRestaurantes(Model model) {
        model.addAttribute("restaurantes", restaurantRepository.findAll());
        return "admin/restaurantes";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'COLLABORATOR')")
    @GetMapping("/nuevo")
    public String nuevoRestaurante(Model model) {
        model.addAttribute("restaurante", new RestaurantEntity());
        model.addAttribute("ciudades", cityRepository.findAll());
        return "admin/restaurantes-form";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'COLLABORATOR')")
    @PostMapping("/guardar")
    public String guardarRestaurante(
            @RequestParam(required = false) Long restaurantId,
            @RequestParam String name,
            @RequestParam String address,
            @RequestParam String info,
            @RequestParam Long cityId,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile
    ) throws IOException {

        RestaurantEntity restaurante = (restaurantId != null)
                ? restaurantRepository.findById(restaurantId).orElse(new RestaurantEntity())
                : new RestaurantEntity();

        restaurante.setName(name);
        restaurante.setAddress(address);
        restaurante.setInfo(info);
        restaurante.setCity(cityRepository.findById(cityId).orElse(null));

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

            restaurante.setImagePath(filename);
        } else if (restaurantId != null) {
            RestaurantEntity existente = restaurantRepository.findById(restaurantId).orElse(null);
            if (existente != null) {
                restaurante.setImagePath(existente.getImagePath());
            }
        }

        restaurantRepository.save(restaurante);
        return "redirect:/admin/restaurantes";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'COLLABORATOR')")
    @GetMapping("/editar/{id}")
    public String editarRestaurante(@PathVariable Long id, Model model) {
        RestaurantEntity restaurante = restaurantRepository.findById(id).orElse(null);
        if (restaurante == null) return "redirect:/admin/restaurantes";

        model.addAttribute("restaurante", restaurante);
        model.addAttribute("ciudades", cityRepository.findAll());
        return "admin/restaurantes-form";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'COLLABORATOR')")
    @GetMapping("/eliminar/{id}")
    public String eliminarRestaurante(@PathVariable Long id) {
        restaurantRepository.deleteById(id);
        return "redirect:/admin/restaurantes";
    }
}
