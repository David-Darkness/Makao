package david.makao.controller;

import david.makao.model.CityEntity;
import david.makao.model.DepartmentEntity;
import david.makao.model.TourPackageEntity;
import david.makao.repository.CityRepository;
import david.makao.repository.DepartmentRepository;
import david.makao.service.TourPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador encargado de manejar las solicitudes relacionadas con la visualización de
 * paquetes turísticos. Permite mostrar todos los paquetes o filtrarlos por departamento o ciudad.
 *
 * <p>Accede a la ruta /paquetes, que muestra la vista 'paquetes.html' con la lista de paquetes y filtros.</p>
 *
 * @author David
 * @version 1.0
 */
@Controller
@RequestMapping("/paquetes")
public class TourPackageWebController {

    private final TourPackageService tourPackageService;
    private final CityRepository cityRepository;
    private final DepartmentRepository departmentRepository;

    /**
     * Constructor que inyecta los servicios y repositorios necesarios para el controlador.
     *
     * @param tourPackageService Servicio para operaciones con paquetes turísticos
     * @param cityRepository Repositorio para acceder a las ciudades
     * @param departmentRepository Repositorio para acceder a los departamentos
     */
    @Autowired
    public TourPackageWebController(
            TourPackageService tourPackageService,
            CityRepository cityRepository,
            DepartmentRepository departmentRepository) {
        this.tourPackageService = tourPackageService;
        this.cityRepository = cityRepository;
        this.departmentRepository = departmentRepository;
    }

    /**
     * Maneja las solicitudes GET para mostrar los paquetes turísticos.
     * Permite filtrar por departamento o ciudad mediante parámetros opcionales.
     * Además, carga las listas de departamentos y ciudades para mostrarlas en la vista como opciones de filtro.
     *
     * @param departmentId ID del departamento para filtrar (opcional)
     * @param cityId ID de la ciudad para filtrar (opcional)
     * @param model Modelo para pasar los atributos a la vista
     * @return Nombre de la plantilla Thymeleaf 'paquetes.html'
     */
    @GetMapping
    public String showAllPackages(@RequestParam(required = false) Long departmentId,
                                  @RequestParam(required = false) Long cityId,
                                  Model model) {

        List<TourPackageEntity> packages;

        if (cityId != null) {
            packages = tourPackageService.getPackagesByCity(cityId);
        } else {
            packages = tourPackageService.getAllPackages();
        }

        List<DepartmentEntity> departments = (List<DepartmentEntity>) departmentRepository.findAll();
        List<CityEntity> cities = (List<CityEntity>) cityRepository.findAll();

        model.addAttribute("tourPackages", packages);
        model.addAttribute("departments", departments);
        model.addAttribute("cities", cities);
        model.addAttribute("selectedDepartment", departmentId);
        model.addAttribute("selectedCity", cityId);

        return "paquetes";
    }
}
