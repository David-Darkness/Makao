package david.makao.controller.admin;

import david.makao.model.CityEntity;
import david.makao.model.TourPackageEntity;
import david.makao.repository.CityRepository;
import david.makao.service.TourPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

/**
 * Controlador para la administración de paquetes turísticos.
 * Proporciona funcionalidades CRUD para los paquetes turísticos, incluyendo
 * la gestión de imágenes asociadas.
 *
 * <p>El acceso a los métodos de este controlador está restringido a usuarios
 * con roles de ADMIN o COLLABORATOR.</p>
 *
 * @author David
 * @version 1.0
 */
@Controller
@PreAuthorize("hasAnyRole('ADMIN', 'COLLABORATOR')")
@RequestMapping("/admin/paquetes")
public class TourPackageAdminController {

    @Autowired
    private TourPackageService tourPackageService;

    @Autowired
    private CityRepository cityRepository;

    // Ruta para guardar las imágenes dentro de static
    private final Path uploadPath = Paths.get("src/main/resources/static/images/imagesPaquetes");

    /**
     * Muestra la lista de todos los paquetes turísticos disponibles.
     *
     * @param model Modelo para pasar datos a la vista
     * @return Nombre de la vista que muestra la lista de paquetes
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'COLLABORATOR')")
    @GetMapping
    public String listarPaquetes(Model model) {
        model.addAttribute("paquetes", tourPackageService.getAllPackages());
        return "admin/paquetes";
    }

    /**
     * Muestra el formulario para crear un nuevo paquete turístico.
     *
     * @param model Modelo para pasar datos a la vista
     * @return Nombre de la vista del formulario de paquetes
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'COLLABORATOR')")
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("paquete", new TourPackageEntity());
        model.addAttribute("ciudades", cityRepository.findAll());
        return "admin/paquetes-form";
    }

    /**
     * Guarda un paquete turístico, ya sea nuevo o existente.
     * Maneja la carga de imágenes asociadas al paquete.
     *
     * @param packageId ID del paquete (opcional, para actualización)
     * @param name Nombre del paquete
     * @param description Descripción del paquete
     * @param price Precio del paquete
     * @param durationDays Duración en días del paquete
     * @param cityId ID de la ciudad asociada al paquete
     * @param imageFile Archivo de imagen del paquete (opcional)
     * @return Redirección a la lista de paquetes
     * @throws IOException Si ocurre un error al guardar la imagen
     * @throws IllegalArgumentException Si la imagen supera el tamaño máximo permitido (1MB)
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'COLLABORATOR')")
    @PostMapping("/guardar")
    public String guardarPaquete(
            @RequestParam(required = false) Long packageId,
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam BigDecimal price,
            @RequestParam int durationDays,
            @RequestParam Long cityId,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile
    ) throws IOException {

        TourPackageEntity paquete = (packageId != null)
                ? tourPackageService.getPackageById(packageId)
                : new TourPackageEntity();

        paquete.setName(name);
        paquete.setDescription(description);
        paquete.setPrice(price);
        paquete.setDurationDays(durationDays);

        CityEntity ciudad = cityRepository.findById(cityId).orElse(null);
        paquete.setCity(ciudad);

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

            paquete.setImagePath(filename);
        } else if (packageId != null) {
            TourPackageEntity existente = tourPackageService.getPackageById(packageId);
            if (existente != null) {
                paquete.setImagePath(existente.getImagePath());
            }
        } else {
            paquete.setImagePath(null);
        }

        if (packageId == null) {
            tourPackageService.createPackage(paquete);
        } else {
            tourPackageService.updatePackage(paquete);
        }

        return "redirect:/admin/paquetes";
    }

    /**
     * Muestra el formulario para editar un paquete turístico existente.
     *
     * @param id ID del paquete a editar
     * @param model Modelo para pasar datos a la vista
     * @return Nombre de la vista del formulario de paquetes o redirección si el paquete no existe
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'COLLABORATOR')")
    @GetMapping("/editar/{id}")
    public String editarPaquete(@PathVariable Long id, Model model) {
        TourPackageEntity paquete = tourPackageService.getPackageById(id);
        if (paquete == null) {
            return "redirect:/admin/paquetes";
        }
        model.addAttribute("paquete", paquete);
        model.addAttribute("ciudades", cityRepository.findAll());
        return "admin/paquetes-form";
    }

    /**
     * Elimina un paquete turístico.
     *
     * @param id ID del paquete a eliminar
     * @return Redirección a la lista de paquetes
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'COLLABORATOR')")
    @GetMapping("/eliminar/{id}")
    public String eliminarPaquete(@PathVariable Long id) {
        tourPackageService.deletePackage(id);
        return "redirect:/admin/paquetes";
    }
}