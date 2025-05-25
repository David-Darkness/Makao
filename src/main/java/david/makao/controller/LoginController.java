package david.makao.controller;

import ch.qos.logback.core.model.Model;
import david.makao.model.TourPackageEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class LoginController {




    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // busca login.html en /templates
    }


    @GetMapping("/navbar")
    public String navbar() {
        return "navbar";
    }





}

