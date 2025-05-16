package david.makao.service;

import david.makao.model.HotelEntity;
import java.util.List;

public interface HotelService {
    List<HotelEntity> getAllHotels();
    List<HotelEntity> getHotelsByCity(Long cityId);
}

