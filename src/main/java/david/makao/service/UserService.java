package david.makao.service;

import david.makao.model.UserEntity;

import java.util.List;

/**
 * Interfaz para el servicio de gestión de usuarios.
 * Define los métodos para buscar, guardar y eliminar usuarios en la base de datos.
 *
 * @author David
 * @version 1.0
 */
public interface UserService {

    /**
     * Busca un usuario por su correo electrónico.
     *
     * @param email correo electrónico del usuario a buscar
     * @return entidad del usuario si existe, o null si no se encuentra
     */
    UserEntity findByEmail(String email);

    /**
     * Busca un usuario por su ID.
     *
     * @param id ID del usuario a buscar
     * @return entidad del usuario si existe, o null si no se encuentra
     */
    UserEntity findById(Long id);

    /**
     * Obtiene una lista con todos los usuarios registrados.
     *
     * @return lista con todos los usuarios
     */
    List<UserEntity> findAllUsers();

    /**
     * Guarda un usuario en la base de datos.
     * Puede ser para crear un nuevo usuario o actualizar uno existente.
     *
     * @param user entidad del usuario a guardar
     */
    void saveUser(UserEntity user);

    /**
     * Elimina un usuario por su ID.
     *
     * @param id ID del usuario a eliminar
     */
    void deleteUserById(Long id);
}
