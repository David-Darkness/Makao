package david.makao.repository;

import david.makao.model.CityEntity;
import david.makao.model.DepartmentEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends CrudRepository<CityEntity, Long> {
    List<CityEntity> findByDepartment(DepartmentEntity department);   // Ciudades por departamento
    List<CityEntity> findByDepartment_DepartmentId(Long departmentId);


}

