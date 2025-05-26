package david.makao.service.impl;

import david.makao.model.TourPackageEntity;
import david.makao.repository.TourPackageRepository;
import david.makao.service.TourPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementación del servicio {@link TourPackageService} para gestionar la
 * lógica relacionada con los paquetes turísticos.
 *
 * <p>Proporciona métodos para obtener, crear, actualizar y eliminar paquetes
 * turísticos, así como para buscar por ciudad o por ID.</p>
 *
 * <p>Esta clase está anotada con {@code @Service} para ser detectada por Spring
 * y utiliza inyección por constructor para el repositorio.</p>
 *
 * @author David
 * @version 1.0
 */
@Service
public class TourPackageServiceImpl implements TourPackageService {

    private final TourPackageRepository tourPackageRepository;

    /**
     * Constructor para la inyección del repositorio {@link TourPackageRepository}.
     *
     * @param tourPackageRepository el repositorio de paquetes turísticos
     */
    @Autowired
    public TourPackageServiceImpl(TourPackageRepository tourPackageRepository) {
        this.tourPackageRepository = tourPackageRepository;
    }

    /**
     * Obtiene todos los paquetes turísticos existentes.
     *
     * @return lista de todos los {@link TourPackageEntity}.
     */
    @Override
    public List<TourPackageEntity> getAllPackages() {
        return (List<TourPackageEntity>) tourPackageRepository.findAll();
    }

    /**
     * Obtiene los paquetes turísticos asociados a una ciudad específica.
     *
     * @param cityId ID de la ciudad.
     * @return lista de paquetes turísticos de la ciudad dada.
     */
    @Override
    public List<TourPackageEntity> getPackagesByCity(Long cityId) {
        return tourPackageRepository.findByCity_CityId(cityId);
    }

    /**
     * Obtiene un paquete turístico por su ID.
     *
     * @param packageId ID del paquete turístico.
     * @return el {@link TourPackageEntity} correspondiente o {@code null} si no existe.
     */
    @Override
    public TourPackageEntity getPackageById(Long packageId) {
        return tourPackageRepository.findById(packageId).orElse(null);
    }

    /**
     * Crea un nuevo paquete turístico.
     *
     * @param packageEntity entidad {@link TourPackageEntity} a crear.
     * @return el paquete turístico creado con ID asignado.
     */
    @Override
    public TourPackageEntity createPackage(TourPackageEntity packageEntity) {
        return tourPackageRepository.save(packageEntity);
    }

    /**
     * Actualiza un paquete turístico existente.
     *
     * @param packageEntity entidad {@link TourPackageEntity} con datos actualizados.
     * @return el paquete turístico actualizado.
     */
    @Override
    public TourPackageEntity updatePackage(TourPackageEntity packageEntity) {
        return tourPackageRepository.save(packageEntity);
    }

    /**
     * Elimina un paquete turístico por su ID.
     *
     * @param packageId ID del paquete turístico a eliminar.
     */
    @Override
    public void deletePackage(Long packageId) {
        tourPackageRepository.deleteById(packageId);
    }
}
