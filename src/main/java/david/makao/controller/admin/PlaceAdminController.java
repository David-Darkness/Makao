package david.makao.controller.admin;

import david.makao.model.CityEntity;
import david.makao.model.DepartmentEntity;
import david.makao.repository.CityRepository;
import david.makao.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador para la gestión administrativa de lugares geográficos (departamentos y ciudades).
 *
 * <p>Proporciona funcionalidades CRUD para:
 * <ul>
 *   <li>Departamentos (creación, edición, eliminación)</li>
 *   <li>Ciudades (creación, edición, eliminación)</li>
 * </ul>
 *
 * <p>Características principales:
 * <ul>
 *   <li>Acceso restringido a roles ADMIN y COLLABORATOR</li>
 *   <li>Ruta base "/admin/lugares"</li>
 *   <li>Vistas separadas para departamentos y ciudades</li>
 *   <li>Relación entre ciudades y departamentos</li>
 * </ul>
 *
 * @author David Makao
 * @version 1.0
 * @see DepartmentRepository
 * @see CityRepository
 */
@Controller
@PreAuthorize("hasAnyRole('ADMIN', 'COLLABORATOR')")
@RequestMapping("/admin/lugares")
public class PlaceAdminController {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private CityRepository cityRepository;

    /**
     * Muestra el listado combinado de departamentos y ciudades.
     *
     * @param model Modelo para pasar datos a la vista
     * @return Vista "admin/lugares" con ambos listados
     */
    @GetMapping
    public String listarLugares(Model model) {
        model.addAttribute("departamentos", departmentRepository.findAll());
        model.addAttribute("ciudades", cityRepository.findAll());
        return "admin/lugares";
    }

    // ---- MÉTODOS PARA DEPARTAMENTOS ----

    /**
     * Muestra el formulario para crear un nuevo departamento.
     *
     * @param model Modelo para pasar datos a la vista
     * @return Vista "admin/departamento-form" con formulario vacío
     */
    @GetMapping("/departamento/nuevo")
    public String nuevoDepartamento(Model model) {
        model.addAttribute("departamento", new DepartmentEntity());
        return "admin/departamento-form";
    }

    /**
     * Procesa el guardado de un departamento (nuevo o editado).
     *
     * @param departamento Entidad DepartmentEntity con los datos del formulario
     * @return Redirección al listado principal de lugares
     */
    @PostMapping("/departamento/guardar")
    public String guardarDepartamento(@ModelAttribute DepartmentEntity departamento) {
        departmentRepository.save(departamento);
        return "redirect:/admin/lugares";
    }

    /**
     * Muestra el formulario para editar un departamento existente.
     *
     * @param id ID del departamento a editar
     * @param model Modelo para pasar datos a la vista
     * @return Vista "admin/departamento-form" con datos del departamento
     *         o redirección al listado si no existe
     */
    @GetMapping("/departamento/editar/{id}")
    public String editarDepartamento(@PathVariable Long id, Model model) {
        DepartmentEntity departamento = departmentRepository.findById(id).orElse(null);
        if (departamento == null) return "redirect:/admin/lugares";
        model.addAttribute("departamento", departamento);
        return "admin/departamento-form";
    }

    /**
     * Elimina un departamento existente.
     *
     * @param id ID del departamento a eliminar
     * @return Redirección al listado principal de lugares
     */
    @GetMapping("/departamento/eliminar/{id}")
    public String eliminarDepartamento(@PathVariable Long id) {
        departmentRepository.deleteById(id);
        return "redirect:/admin/lugares";
    }

    // ---- MÉTODOS PARA CIUDADES ----

    /**
     * Muestra el formulario para crear una nueva ciudad.
     *
     * @param model Modelo para pasar datos a la vista
     * @return Vista "admin/ciudad-form" con formulario vacío y listado de departamentos
     */
    @GetMapping("/ciudad/nuevo")
    public String nuevaCiudad(Model model) {
        model.addAttribute("ciudad", new CityEntity());
        model.addAttribute("departamentos", departmentRepository.findAll());
        return "admin/ciudad-form";
    }

    /**
     * Procesa el guardado de una ciudad (nueva o editada).
     *
     * @param ciudad Entidad CityEntity con los datos del formulario
     * @param departmentId ID del departamento al que pertenece la ciudad
     * @return Redirección al listado principal de lugares
     */
    @PostMapping("/ciudad/guardar")
    public String guardarCiudad(@ModelAttribute CityEntity ciudad, @RequestParam Long departmentId) {
        DepartmentEntity departamento = departmentRepository.findById(departmentId).orElse(null);
        ciudad.setDepartment(departamento);
        cityRepository.save(ciudad);
        return "redirect:/admin/lugares";
    }

    /**
     * Muestra el formulario para editar una ciudad existente.
     *
     * @param id ID de la ciudad a editar
     * @param model Modelo para pasar datos a la vista
     * @return Vista "admin/ciudad-form" con datos de la ciudad y listado de departamentos
     *         o redirección al listado si no existe
     */
    @GetMapping("/ciudad/editar/{id}")
    public String editarCiudad(@PathVariable Long id, Model model) {
        CityEntity ciudad = cityRepository.findById(id).orElse(null);
        if (ciudad == null) return "redirect:/admin/lugares";
        model.addAttribute("ciudad", ciudad);
        model.addAttribute("departamentos", departmentRepository.findAll());
        return "admin/ciudad-form";
    }

    /**
     * Elimina una ciudad existente.
     *
     * @param id ID de la ciudad a eliminar
     * @return Redirección al listado principal de lugares
     */
    @GetMapping("/ciudad/eliminar/{id}")
    public String eliminarCiudad(@PathVariable Long id) {
        cityRepository.deleteById(id);
        return "redirect:/admin/lugares";
    }
}
