package david.makao.controller.admin;

import david.makao.model.RoleEntity;
import david.makao.model.UserEntity;
import david.makao.repository.RoleRepository;
import david.makao.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/admin/usuarios")
public class UserAdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Mostrar lista de usuarios
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public String listarUsuarios(Model model) {
        List<UserEntity> usuarios = (List<UserEntity>) userRepository.findAll();
        model.addAttribute("usuarios", usuarios);
        return "admin/usuarios";
    }

    // Mostrar formulario para nuevo usuario
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/nuevo")
    public String nuevoUsuario(Model model) {
        model.addAttribute("usuario", new UserEntity());
        model.addAttribute("rolesDisponibles", roleRepository.findAll());
        return "admin/usuarios-form";
    }

    // Guardar usuario (nuevo o editado)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/guardar")
    public String guardarUsuario(@ModelAttribute UserEntity usuario,
                                 @RequestParam(name = "roles", required = false) List<Long> roleIds) {

        Set<RoleEntity> rolesSeleccionados = new HashSet<>();
        if (roleIds != null) {
            for (Long roleId : roleIds) {
                roleRepository.findById(roleId).ifPresent(rolesSeleccionados::add);
            }
        }
        usuario.setRoles(rolesSeleccionados);

        if (usuario.getId() != null) {
            // Edición de usuario
            UserEntity usuarioExistente = userRepository.findById(usuario.getId()).orElse(null);
            if (usuarioExistente != null) {
                if (usuario.getPassword() == null || usuario.getPassword().isBlank()) {
                    // No se ingresó nueva contraseña → mantener la anterior
                    usuario.setPassword(usuarioExistente.getPassword());
                } else {
                    // Se ingresó nueva contraseña → encriptar
                    usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
                }
            }
        } else {
            // Nuevo usuario → encriptar contraseña
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        }

        userRepository.save(usuario);
        return "redirect:/admin/usuarios";
    }

    // Editar usuario
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/editar/{id}")
    public String editarUsuario(@PathVariable Long id, Model model) {
        UserEntity usuario = userRepository.findById(id).orElse(null);
        if (usuario == null) return "redirect:/admin/usuarios";

        model.addAttribute("usuario", usuario);
        model.addAttribute("rolesDisponibles", roleRepository.findAll());
        return "admin/usuarios-form";
    }

    // Eliminar usuario
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "redirect:/admin/usuarios";
    }
}
