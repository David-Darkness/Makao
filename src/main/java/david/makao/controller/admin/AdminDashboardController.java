package david.makao.controller.admin;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@PreAuthorize("hasAnyRole('ADMIN', 'COLLABORATOR')")
@RequestMapping("/admin")
public class AdminDashboardController {

    @GetMapping
    public String mostrarDashboard() {
        return "admin/dashboard"; // esta será tu vista principal
    }

    @GetMapping("/dashboard")
    public String mostrarDashboard2() {
        return "admin/dashboard"; // esta será tu vista principal
    }


}
