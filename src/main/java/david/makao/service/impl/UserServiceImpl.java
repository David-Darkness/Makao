package david.makao.service.impl;

import david.makao.model.UserEntity;
import david.makao.repository.UserRepository;
import david.makao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementación del servicio {@link UserService} que gestiona operaciones
 * CRUD sobre la entidad {@link UserEntity}.
 *
 * <p>Proporciona métodos para buscar usuarios por email o ID, obtener todos los usuarios,
 * guardar un usuario y eliminar un usuario por su ID.</p>
 *
 * @author David
 * @version 1.0
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Busca un usuario por su email.
     *
     * @param email el email del usuario a buscar
     * @return el usuario encontrado o null si no existe
     */
    @Override
    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    /**
     * Busca un usuario por su ID.
     *
     * @param id el ID del usuario a buscar
     * @return el usuario encontrado o null si no existe
     */
    @Override
    public UserEntity findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    /**
     * Obtiene la lista de todos los usuarios registrados.
     *
     * @return lista con todos los usuarios
     */
    @Override
    public List<UserEntity> findAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Guarda o actualiza un usuario en la base de datos.
     *
     * @param user la entidad usuario a guardar o actualizar
     */
    @Override
    public void saveUser(UserEntity user) {
        userRepository.save(user);
    }

    /**
     * Elimina un usuario por su ID.
     *
     * @param id el ID del usuario a eliminar
     */
    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
}
