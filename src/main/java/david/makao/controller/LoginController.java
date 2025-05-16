package david.makao.controller;

import david.makao.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {




    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // busca login.html en /templates
    }




    // Página de inicio después de login
    @GetMapping("/home")
    public String home() {
        return "home";
    }
}

