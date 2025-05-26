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

/**
 * Controlador para la gestión administrativa de restaurantes.
 *
 * <p>Proporciona funcionalidades CRUD completas para restaurantes, incluyendo:
 * <ul>
 *   <li>Listado de todos los restaurantes</li>
 *   <li>Creación de nuevos restaurantes</li>
 *   <li>Edición de restaurantes existentes</li>
 *   <li>Eliminación de restaurantes</li>
 *   <li>Gestión de imágenes asociadas</li>
 * </ul>
 *
 * <p>Características principales:
 * <ul>
 *   <li>Acceso restringido a roles ADMIN y COLLABORATOR</li>
 *   <li>Ruta base "/admin/restaurantes"</li>
 *   <li>Asociación con ciudades</li>
 *   <li>Soporte para carga de imágenes (max 1MB)</li>
 *   <li>Generación de nombres únicos para archivos</li>
 * </ul>
 *
 * @author David Makao
 * @version 1.0
 * @see RestaurantRepository
 * @see CityRepository
 */
@Controller
@PreAuthorize("hasAnyRole('ADMIN', 'COLLABORATOR')")
@RequestMapping("/admin/restaurantes")
public class RestaurantAdminController {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private CityRepository cityRepository;

    /** Ruta donde se almacenan las imágenes de los restaurantes */
    private final Path uploadPath = Paths.get("src/main/resources/static/images/imagesRestaurante");

    /**
     * Muestra el listado de todos los restaurantes.
     *
     * @param model Modelo para pasar datos a la vista
     * @return Vista "admin/restaurantes" con el listado completo
     */
    @GetMapping
    public String listarRestaurantes(Model model) {
        model.addAttribute("restaurantes", restaurantRepository.findAll());
        return "admin/restaurantes";
    }

    /**
     * Muestra el formulario para crear un nuevo restaurante.
     *
     * @param model Modelo para pasar datos a la vista
     * @return Vista "admin/restaurantes-form" con formulario vacío y listado de ciudades
     */
    @GetMapping("/nuevo")
    public String nuevoRestaurante(Model model) {
        model.addAttribute("restaurante", new RestaurantEntity());
        model.addAttribute("ciudades", cityRepository.findAll());
        return "admin/restaurantes-form";
    }

    /**
     * Procesa el guardado de un restaurante (nuevo o editado).
     *
     * <p>Este método maneja:
     * <ul>
     *   <li>Actualización de datos básicos del restaurante</li>
     *   <li>Asociación con ciudad</li>
     *   <li>Validación y almacenamiento de imágenes (tamaño máximo 1MB)</li>
     *   <li>Mantenimiento de imagen existente si no se proporciona nueva</li>
     *   <li>Generación de nombres únicos para archivos de imagen</li>
     * </ul>
     *
     * @param restaurantId ID del restaurante a actualizar (opcional para nuevos)
     * @param name Nombre del restaurante
     * @param address Dirección del restaurante
     * @param info Información adicional sobre el restaurante
     * @param cityId ID de la ciudad asociada al restaurante
     * @param imageFile Archivo de imagen a subir (opcional)
     * @return Redirección al listado de restaurantes
     * @throws IOException Si ocurre un error al guardar la imagen
     * @throws IllegalArgumentException Si la imagen supera el tamaño máximo permitido (1MB)
     */
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

        // Procesamiento de imagen
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

    /**
     * Muestra el formulario para editar un restaurante existente.
     *
     * @param id ID del restaurante a editar
     * @param model Modelo para pasar datos a la vista
     * @return Vista "admin/restaurantes-form" con datos del restaurante
     *         o redirección al listado si el restaurante no existe
     */
    @GetMapping("/editar/{id}")
    public String editarRestaurante(@PathVariable Long id, Model model) {
        RestaurantEntity restaurante = restaurantRepository.findById(id).orElse(null);
        if (restaurante == null) return "redirect:/admin/restaurantes";

        model.addAttribute("restaurante", restaurante);
        model.addAttribute("ciudades", cityRepository.findAll());
        return "admin/restaurantes-form";
    }

    /**
     * Elimina un restaurante existente.
     *
     * @param id ID del restaurante a eliminar
     * @return Redirección al listado de restaurantes
     */
    @GetMapping("/eliminar/{id}")
    public String eliminarRestaurante(@PathVariable Long id) {
        restaurantRepository.deleteById(id);
        return "redirect:/admin/restaurantes";
    }
}