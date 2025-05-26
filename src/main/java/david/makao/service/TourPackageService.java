package david.makao.service;

import david.makao.model.TourPackageEntity;
import java.util.List;

/**
 * Interfaz para el servicio de gestión de paquetes turísticos.
 * Define métodos para obtener, crear, actualizar y eliminar paquetes turísticos.
 *
 * @author David
 * @version 1.0
 */
public interface TourPackageService {

    /**
     * Obtiene la lista de todos los paquetes turísticos disponibles.
     *
     * @return lista con todos los paquetes turísticos
     */
    List<TourPackageEntity> getAllPackages();

    /**
     * Obtiene la lista de paquetes turísticos que pertenecen a una ciudad específica.
     *
     * @param cityId el ID de la ciudad para filtrar paquetes turísticos
     * @return lista de paquetes turísticos asociados a la ciudad dada
     */
    List<TourPackageEntity> getPackagesByCity(Long cityId);

    /**
     * Obtiene un paquete turístico por su ID.
     *
     * @param packageId ID del paquete turístico a buscar
     * @return entidad del paquete turístico encontrado, o null si no existe
     */
    TourPackageEntity getPackageById(Long packageId);

    /**
     * Crea un nuevo paquete turístico y lo guarda en la base de datos.
     *
     * @param packageEntity entidad del paquete turístico a crear
     * @return entidad del paquete turístico creado
     */
    TourPackageEntity createPackage(TourPackageEntity packageEntity);

    /**
     * Actualiza un paquete turístico existente en la base de datos.
     *
     * @param packageEntity entidad del paquete turístico con los datos actualizados
     * @return entidad del paquete turístico actualizado
     */
    TourPackageEntity updatePackage(TourPackageEntity packageEntity);

    /**
     * Elimina un paquete turístico por su ID.
     *
     * @param packageId ID del paquete turístico a eliminar
     */
    void deletePackage(Long packageId);
}

