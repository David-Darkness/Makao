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

@Controller
@RequestMapping("/paquetes")
public class TourPackageWebController {

    private final TourPackageService tourPackageService;
    private final CityRepository cityRepository;
    private final DepartmentRepository departmentRepository;

    @Autowired
    public TourPackageWebController(
            TourPackageService tourPackageService,
            CityRepository cityRepository,
            DepartmentRepository departmentRepository) {
        this.tourPackageService = tourPackageService;
        this.cityRepository = cityRepository;
        this.departmentRepository = departmentRepository;
    }

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



        return "paquetes"; // apunta a templates/paquetes.html
    }




}
