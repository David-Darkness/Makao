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

    @PreAuthorize("hasAnyRole('ADMIN', 'COLLABORATOR')")
    @GetMapping
    public String listarPaquetes(Model model) {
        model.addAttribute("paquetes", tourPackageService.getAllPackages());
        return "admin/paquetes";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'COLLABORATOR')")
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("paquete", new TourPackageEntity());
        model.addAttribute("ciudades", cityRepository.findAll());
        return "admin/paquetes-form";
    }

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

    @PreAuthorize("hasAnyRole('ADMIN', 'COLLABORATOR')")
    @GetMapping("/eliminar/{id}")
    public String eliminarPaquete(@PathVariable Long id) {
        tourPackageService.deletePackage(id);
        return "redirect:/admin/paquetes";
    }
}

