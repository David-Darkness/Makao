package david.makao.repository;

import david.makao.model.ReservationEntity;
import david.makao.model.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends CrudRepository<ReservationEntity, Long> {
    List<ReservationEntity> findByUser(UserEntity user);   // Reservas por usuario
}

