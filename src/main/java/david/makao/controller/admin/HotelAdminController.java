package david.makao.controller.admin;

import david.makao.model.CityEntity;
import david.makao.model.HotelEntity;
import david.makao.repository.CityRepository;
import david.makao.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

/**
 * Controlador para la gestión administrativa de hoteles.
 *
 * <p>Proporciona funcionalidades CRUD para hoteles, incluyendo:
 * <ul>
 *   <li>Listado de todos los hoteles</li>
 *   <li>Creación de nuevos hoteles</li>
 *   <li>Edición de hoteles existentes</li>
 *   <li>Eliminación de hoteles</li>
 *   <li>Subida de imágenes para hoteles</li>
 * </ul>
 *
 * <p>Acceso restringido a roles ADMIN y COLLABORATOR mediante Spring Security.
 * Todas las rutas están prefijadas con "/admin/hoteles".
 *
 * @author David Makao
 * @version 1.0
 * @see HotelRepository
 * @see CityRepository
 */
@Controller
@PreAuthorize("hasAnyRole('ADMIN', 'COLLABORATOR')")
@RequestMapping("/admin/hoteles")
public class HotelAdminController {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private CityRepository cityRepository;

    /** Ruta donde se almacenan las imágenes de los hoteles */
    private final Path uploadPath = Paths.get("src/main/resources/static/images/imagesHotel");

    /**
     * Muestra el listado de todos los hoteles.
     *
     * @param model Modelo para pasar datos a la vista
     * @return Vista "admin/hoteles" con el listado
     */
    @GetMapping
    public String listarHoteles(Model model) {
        model.addAttribute("hoteles", hotelRepository.findAll());
        return "admin/hoteles";
    }

    /**
     * Muestra el formulario para crear un nuevo hotel.
     *
     * @param model Modelo para pasar datos a la vista
     * @return Vista "admin/hotel-form" con formulario vacío
     */
    @GetMapping("/nuevo")
    public String nuevoHotel(Model model) {
        model.addAttribute("hotel", new HotelEntity());
        model.addAttribute("ciudades", cityRepository.findAll());
        return "admin/hotel-form";
    }

    /**
     * Procesa el guardado de un hotel (nuevo o editado).
     *
     * <p>Gestiona:
     * <ul>
     *   <li>Actualización de datos básicos del hotel</li>
     *   <li>Asociación con ciudad</li>
     *   <li>Subida y validación de imagen (max 1MB)</li>
     *   <li>Mantenimiento de imagen existente si no se sube nueva</li>
     * </ul>
     *
     * @param hotelId ID del hotel a actualizar (opcional para nuevos)
     * @param name Nombre del hotel
     * @param address Dirección del hotel
     * @param description Descripción del hotel
     * @param info Información adicional
     * @param stars Número de estrellas (1-5)
     * @param cityId ID de la ciudad asociada
     * @param imageFile Archivo de imagen subido (opcional)
     * @return Redirección al listado de hoteles
     * @throws IOException Si hay error al guardar la imagen
     * @throws IllegalArgumentException Si la imagen supera 1MB
     */
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

    /**
     * Muestra el formulario para editar un hotel existente.
     *
     * @param id ID del hotel a editar
     * @param model Modelo para pasar datos a la vista
     * @return Vista "admin/hotel-form" con datos del hotel
     *         o redirección al listado si no existe
     */
    @GetMapping("/editar/{id}")
    public String editarHotel(@PathVariable Long id, Model model) {
        HotelEntity hotel = hotelRepository.findById(id).orElse(null);
        if (hotel == null) return "redirect:/admin/hoteles";

        model.addAttribute("hotel", hotel);
        model.addAttribute("ciudades", cityRepository.findAll());
        return "admin/hotel-form";
    }

    /**
     * Elimina un hotel existente.
     *
     * @param id ID del hotel a eliminar
     * @return Redirección al listado de hoteles
     */
    @GetMapping("/eliminar/{id}")
    public String eliminarHotel(@PathVariable Long id) {
        hotelRepository.deleteById(id);
        return "redirect:/admin/hoteles";
    }
}