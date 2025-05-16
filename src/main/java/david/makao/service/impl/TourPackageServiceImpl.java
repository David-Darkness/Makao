package david.makao.service.impl;

import david.makao.model.TourPackageEntity;
import david.makao.repository.TourPackageRepository;
import david.makao.service.TourPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TourPackageServiceImpl implements TourPackageService {

    private final TourPackageRepository tourPackageRepository;

    //constructor manual
    @Autowired
    public TourPackageServiceImpl(TourPackageRepository tourPackageRepository) {
        this.tourPackageRepository = tourPackageRepository;
    }

    @Override
    public List<TourPackageEntity> getAllPackages() {
        return (List<TourPackageEntity>) tourPackageRepository.findAll();
    }

    @Override
    public List<TourPackageEntity> getPackagesByCity(Long cityId) {
        return tourPackageRepository.findByCity_CityId(cityId);
    }
}
