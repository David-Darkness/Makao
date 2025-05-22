package david.makao.repository;

import david.makao.model.ERole;
import david.makao.model.RoleEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByRole(ERole role);
    boolean existsByRole(ERole role);
}

