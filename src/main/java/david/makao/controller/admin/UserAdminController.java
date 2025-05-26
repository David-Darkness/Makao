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

/**
 * Controlador para la administración de usuarios del sistema.
 * Proporciona funcionalidades CRUD para la gestión de usuarios y sus roles.
 *
 * <p>El acceso a todos los métodos de este controlador está restringido exclusivamente
 * a usuarios con rol ADMIN.</p>
 *
 * @author David
 * @version 1.0
 */
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

    /**
     * Muestra la lista de todos los usuarios registrados en el sistema.
     *
     * @param model Modelo para pasar datos a la vista
     * @return Nombre de la vista que muestra la lista de usuarios
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public String listarUsuarios(Model model) {
        List<UserEntity> usuarios = (List<UserEntity>) userRepository.findAll();
        model.addAttribute("usuarios", usuarios);
        return "admin/usuarios";
    }

    /**
     * Muestra el formulario para crear un nuevo usuario.
     *
     * @param model Modelo para pasar datos a la vista
     * @return Nombre de la vista del formulario de usuario
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/nuevo")
    public String nuevoUsuario(Model model) {
        model.addAttribute("usuario", new UserEntity());
        model.addAttribute("rolesDisponibles", roleRepository.findAll());
        return "admin/usuarios-form";
    }

    /**
     * Guarda un usuario en el sistema, ya sea nuevo o existente.
     * Maneja el cifrado de contraseñas y la asignación de roles.
     *
     * @param usuario Entidad del usuario a guardar
     * @param roleIds Lista de IDs de roles a asignar al usuario
     * @return Redirección a la lista de usuarios
     */
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

    /**
     * Muestra el formulario para editar un usuario existente.
     *
     * @param id ID del usuario a editar
     * @param model Modelo para pasar datos a la vista
     * @return Nombre de la vista del formulario de usuario o redirección si el usuario no existe
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/editar/{id}")
    public String editarUsuario(@PathVariable Long id, Model model) {
        UserEntity usuario = userRepository.findById(id).orElse(null);
        if (usuario == null) return "redirect:/admin/usuarios";

        model.addAttribute("usuario", usuario);
        model.addAttribute("rolesDisponibles", roleRepository.findAll());
        return "admin/usuarios-form";
    }

    /**
     * Elimina un usuario del sistema.
     *
     * @param id ID del usuario a eliminar
     * @return Redirección a la lista de usuarios
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "redirect:/admin/usuarios";
    }
}
