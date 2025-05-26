package david.makao.repository;

import david.makao.model.CityEntity;
import david.makao.model.DepartmentEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para acceder a los datos de la entidad {@link CityEntity}.
 * Proporciona métodos para realizar operaciones CRUD y consultas personalizadas sobre ciudades.
 *
 * <p>Extiende {@link CrudRepository} para obtener funcionalidad básica de persistencia.</p>
 *
 * <p>Incluye métodos personalizados para buscar ciudades por su departamento asociado.</p>
 *
 * @author David
 * @version 1.0
 */
@Repository
public interface CityRepository extends CrudRepository<CityEntity, Long> {

    /**
     * Busca todas las ciudades asociadas a un departamento específico.
     *
     * @param department el departamento al que pertenecen las ciudades.
     * @return lista de ciudades dentro del departamento dado.
     */
    List<CityEntity> findByDepartment(DepartmentEntity department);

    /**
     * Busca todas las ciudades por el ID del departamento.
     *
     * @param departmentId el ID del departamento.
     * @return lista de ciudades que pertenecen al departamento con el ID especificado.
     */
    List<CityEntity> findByDepartment_DepartmentId(Long departmentId);

}

