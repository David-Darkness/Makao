package david.makao.repository;

import david.makao.model.DepartmentEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para acceder a los datos de la entidad {@link DepartmentEntity}.
 * Proporciona métodos CRUD básicos para gestionar los departamentos.
 *
 * <p>Extiende {@link CrudRepository} para heredar operaciones estándar como guardar, eliminar,
 * buscar por ID, y listar todos los departamentos.</p>
 *
 * <p>Si se requieren métodos personalizados en el futuro, pueden agregarse aquí.</p>
 *
 * @author David
 * @version 1.0
 */
@Repository
public interface DepartmentRepository extends CrudRepository<DepartmentEntity, Long> {

}

