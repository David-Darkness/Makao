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

@Controller
@PreAuthorize("hasAnyRole('ADMIN', 'COLLABORATOR')")
@RequestMapping("/admin/lugares")
public class PlaceAdminController {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private CityRepository cityRepository;

    // Mostrar ciudades y departamentos
    @PreAuthorize("hasAnyRole('ADMIN', 'COLLABORATOR')")
    @GetMapping
    public String listarLugares(Model model) {
        model.addAttribute("departamentos", departmentRepository.findAll());
        model.addAttribute("ciudades", cityRepository.findAll());
        return "admin/lugares"; // Vista principal
    }

    // ---- DEPARTAMENTOS ----
    @PreAuthorize("hasAnyRole('ADMIN', 'COLLABORATOR')")
    @GetMapping("/departamento/nuevo")
    public String nuevoDepartamento(Model model) {
        model.addAttribute("departamento", new DepartmentEntity());
        return "admin/departamento-form";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'COLLABORATOR')")
    @PostMapping("/departamento/guardar")
    public String guardarDepartamento(@ModelAttribute DepartmentEntity departamento) {
        departmentRepository.save(departamento);
        return "redirect:/admin/lugares";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'COLLABORATOR')")
    @GetMapping("/departamento/editar/{id}")
    public String editarDepartamento(@PathVariable Long id, Model model) {
        DepartmentEntity departamento = departmentRepository.findById(id).orElse(null);
        if (departamento == null) return "redirect:/admin/lugares";
        model.addAttribute("departamento", departamento);
        return "admin/departamento-form";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'COLLABORATOR')")
    @GetMapping("/departamento/eliminar/{id}")
    public String eliminarDepartamento(@PathVariable Long id) {
        departmentRepository.deleteById(id);
        return "redirect:/admin/lugares";
    }

    // ---- CIUDADES ----
    @PreAuthorize("hasAnyRole('ADMIN', 'COLLABORATOR')")
    @GetMapping("/ciudad/nuevo")
    public String nuevaCiudad(Model model) {
        model.addAttribute("ciudad", new CityEntity());
        model.addAttribute("departamentos", departmentRepository.findAll());
        return "admin/ciudad-form";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'COLLABORATOR')")
    @PostMapping("/ciudad/guardar")
    public String guardarCiudad(@ModelAttribute CityEntity ciudad, @RequestParam Long departmentId) {
        DepartmentEntity departamento = departmentRepository.findById(departmentId).orElse(null);
        ciudad.setDepartment(departamento);
        cityRepository.save(ciudad);
        return "redirect:/admin/lugares";
    }


    @PreAuthorize("hasAnyRole('ADMIN', 'COLLABORATOR')")
    @GetMapping("/ciudad/editar/{id}")
    public String editarCiudad(@PathVariable Long id, Model model) {
        CityEntity ciudad = cityRepository.findById(id).orElse(null);
        if (ciudad == null) return "redirect:/admin/lugares";
        model.addAttribute("ciudad", ciudad);
        model.addAttribute("departamentos", departmentRepository.findAll());
        return "admin/ciudad-form";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'COLLABORATOR')")
    @GetMapping("/ciudad/eliminar/{id}")
    public String eliminarCiudad(@PathVariable Long id) {
        cityRepository.deleteById(id);
        return "redirect:/admin/lugares";
    }
}
