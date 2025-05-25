package david.makao.service;


import david.makao.model.ReservationEntity;
import david.makao.model.UserEntity;

import java.util.List;
import java.util.Map;

public interface ReservationService {
    void crearReserva(Map<String, String> datos);
    List<ReservationEntity> findByUser(UserEntity user);
    List<ReservationEntity> getAllReservations();
    ReservationEntity buscarPorId(Long id);
    void guardar(ReservationEntity reserva);
    void eliminar(Long id);
}

