package david.makao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {




    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // busca login.html en /templates
    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }

    @GetMapping("/navbar")
    public String navbar() {
        return "navbar";
    }



    // Página de inicio después de login
    @GetMapping("/home")
    public String home() {
        return "home";
    }
}

