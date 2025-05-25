package david.makao.controller;

import david.makao.model.ERole;
import david.makao.model.RoleEntity;
import david.makao.model.UserEntity;
import david.makao.repository.RoleRepository;
import david.makao.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Collections;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new UserEntity());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") UserEntity user,
                               BindingResult result,
                               Model model) {

        if (userRepository.existsByEmail(user.getEmail())) {
            result.rejectValue("email", "error.user", "Este correo ya está registrado");
        }

        if (userRepository.existsByUsername(user.getUsername())) {
            result.rejectValue("username", "error.user", "Este nombre de usuario ya está en uso");
        }

        if (result.hasErrors()) {
            return "register";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        RoleEntity customerRole = roleRepository.findByRole(ERole.CUSTOMER)
                .orElseThrow(() -> new RuntimeException("Rol CUSTOMER no encontrado"));

        user.setRoles(Collections.singleton(customerRole));
        userRepository.save(user);

        return "redirect:/login?registered=true";
    }

}
