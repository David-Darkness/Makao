package david.makao.controller;

import david.makao.model.UserEntity;
import david.makao.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PerfilController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/perfil")
    public String verPerfil(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        UserEntity user = userRepository.findByEmail(userDetails.getUsername()).orElse(null);
        model.addAttribute("usuario", user);
        return "perfil";
    }
}

