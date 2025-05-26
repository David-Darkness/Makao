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

/**
 * Controlador encargado de manejar el registro de nuevos usuarios en la aplicación.
 *
 * <p>Este controlador presenta el formulario de registro, valida los datos ingresados
 * por el usuario, codifica la contraseña y asigna un rol por defecto antes de guardar
 * el nuevo usuario en la base de datos.</p>
 *
 * @author David
 * @version 1.0
 */
@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Muestra el formulario de registro de usuario.
     *
     * @param model Modelo para pasar un objeto vacío de usuario a la vista
     * @return Nombre de la vista del formulario de registro
     */
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new UserEntity());
        return "register";
    }

    /**
     * Procesa el formulario de registro de usuario.
     *
     * <p>Este método valida si el correo electrónico o el nombre de usuario ya existen,
     * codifica la contraseña, asigna el rol {@code CUSTOMER} y guarda el usuario en la base de datos.</p>
     *
     * @param user Objeto {@link UserEntity} con los datos ingresados por el usuario
     * @param result Objeto {@link BindingResult} que contiene los errores de validación, si existen
     * @param model Modelo para pasar datos a la vista en caso de error
     * @return Redirección al login si el registro fue exitoso, o vuelve al formulario si hay errores
     *
     * @throws RuntimeException Si no se encuentra el rol {@code CUSTOMER} en la base de datos
     */
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
