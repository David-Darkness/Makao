package david.makao.service;

import david.makao.model.UserEntity;

import java.util.List;

public interface UserService {
    UserEntity findByEmail(String email);
    UserEntity findById(Long id);
    List<UserEntity> findAllUsers();
    void saveUser(UserEntity user);
    void deleteUserById(Long id);
}

