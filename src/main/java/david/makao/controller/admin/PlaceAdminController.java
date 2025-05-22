package david.makao.controller.admin;

import david.makao.model.CityEntity;
import david.makao.model.DepartmentEntity;
import david.makao.repository.CityRepository;
import david.makao.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/lugares")
public class PlaceAdminController {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private CityRepository cityRepository;

    // Mostrar ciudades y departamentos
    @GetMapping
    public String listarLugares(Model model) {
        model.addAttribute("departamentos", departmentRepository.findAll());
        model.addAttribute("ciudades", cityRepository.findAll());
        return "admin/lugares"; // Vista principal
    }

    // ---- DEPARTAMENTOS ----
    @GetMapping("/departamento/nuevo")
    public String nuevoDepartamento(Model model) {
        model.addAttribute("departamento", new DepartmentEntity());
        return "admin/departamento-form";
    }

    @PostMapping("/departamento/guardar")
    public String guardarDepartamento(@ModelAttribute DepartmentEntity departamento) {
        departmentRepository.save(departamento);
        return "redirect:/admin/lugares";
    }

    @GetMapping("/departamento/editar/{id}")
    public String editarDepartamento(@PathVariable Long id, Model model) {
        DepartmentEntity departamento = departmentRepository.findById(id).orElse(null);
        if (departamento == null) return "redirect:/admin/lugares";
        model.addAttribute("departamento", departamento);
        return "admin/departamento-form";
    }

    @GetMapping("/departamento/eliminar/{id}")
    public String eliminarDepartamento(@PathVariable Long id) {
        departmentRepository.deleteById(id);
        return "redirect:/admin/lugares";
    }

    // ---- CIUDADES ----
    @GetMapping("/ciudad/nuevo")
    public String nuevaCiudad(Model model) {
        model.addAttribute("ciudad", new CityEntity());
        model.addAttribute("departamentos", departmentRepository.findAll());
        return "admin/ciudad-form";
    }

    @PostMapping("/ciudad/guardar")
    public String guardarCiudad(@ModelAttribute CityEntity ciudad, @RequestParam Long departmentId) {
        DepartmentEntity departamento = departmentRepository.findById(departmentId).orElse(null);
        ciudad.setDepartment(departamento);
        cityRepository.save(ciudad);
        return "redirect:/admin/lugares";
    }


    @GetMapping("/ciudad/editar/{id}")
    public String editarCiudad(@PathVariable Long id, Model model) {
        CityEntity ciudad = cityRepository.findById(id).orElse(null);
        if (ciudad == null) return "redirect:/admin/lugares";
        model.addAttribute("ciudad", ciudad);
        model.addAttribute("departamentos", departmentRepository.findAll());
        return "admin/ciudad-form";
    }

    @GetMapping("/ciudad/eliminar/{id}")
    public String eliminarCiudad(@PathVariable Long id) {
        cityRepository.deleteById(id);
        return "redirect:/admin/lugares";
    }
}
