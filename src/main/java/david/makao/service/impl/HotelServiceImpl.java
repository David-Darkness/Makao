package david.makao.service.impl;

import david.makao.model.HotelEntity;
import david.makao.repository.HotelRepository;
import david.makao.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    @Autowired
    private HotelRepository hotelRepository;


    @Override
    public List<HotelEntity> getAllHotels() {
        return (List<HotelEntity>) hotelRepository.findAll();
    }

    @Override
    public List<HotelEntity> getHotelsByCity(Long cityId) {
        return hotelRepository.findByCity_CityId(cityId);

    }
}

