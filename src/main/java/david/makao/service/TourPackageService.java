package david.makao.service;

import david.makao.model.TourPackageEntity;
import java.util.List;

public interface TourPackageService {
    List<TourPackageEntity> getAllPackages();
    List<TourPackageEntity> getPackagesByCity(Long cityId);

    // Métodos CRUD adicionales
    TourPackageEntity getPackageById(Long packageId);
    TourPackageEntity createPackage(TourPackageEntity packageEntity);
    TourPackageEntity updatePackage(TourPackageEntity packageEntity);
    void deletePackage(Long packageId);
}


