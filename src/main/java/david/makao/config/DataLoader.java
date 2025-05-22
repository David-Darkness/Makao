/*
package david.makao.config;

import david.makao.model.CityEntity;
import david.makao.model.DepartmentEntity;
import david.makao.repository.CityRepository;
import david.makao.repository.DepartmentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner loadData(DepartmentRepository departmentRepository, CityRepository cityRepository) {
        return args -> {
            // Crear departamentos
            DepartmentEntity meta = DepartmentEntity.builder()
                    .name("Meta")
                    .description("Departamento de los Llanos Orientales de Colombia")
                    .build();

            DepartmentEntity huila = DepartmentEntity.builder()
                    .name("Huila")
                    .description("Departamento ubicado al sur del país en la región andina")
                    .build();

            // Guardar departamentos
            departmentRepository.save(meta);
            departmentRepository.save(huila);

            // Crear ciudades/pueblos del Meta
            cityRepository.save(CityEntity.builder()
                    .name("Villavicencio")
                    .description("Capital del departamento del Meta")
                    .department(meta)
                    .build());

            cityRepository.save(CityEntity.builder()
                    .name("Acacías")
                    .description("Municipio agrícola del Meta")
                    .department(meta)
                    .build());

            cityRepository.save(CityEntity.builder()
                    .name("Granada")
                    .description("Municipio ganadero e importante en la región del Ariari")
                    .department(meta)
                    .build());

            cityRepository.save(CityEntity.builder()
                    .name("Puerto López")
                    .description("Centro geográfico de Colombia")
                    .department(meta)
                    .build());

            cityRepository.save(CityEntity.builder()
                    .name("San Martín")
                    .description("Municipio tradicional del Meta")
                    .department(meta)
                    .build());

            // Crear ciudades/pueblos del Huila
            cityRepository.save(CityEntity.builder()
                    .name("Neiva")
                    .description("Capital del departamento del Huila")
                    .department(huila)
                    .build());

            cityRepository.save(CityEntity.builder()
                    .name("Pitalito")
                    .description("Importante productor de café del sur del Huila")
                    .department(huila)
                    .build());

            cityRepository.save(CityEntity.builder()
                    .name("Garzón")
                    .description("Municipio con actividad agrícola y cultural")
                    .department(huila)
                    .build());

            cityRepository.save(CityEntity.builder()
                    .name("La Plata")
                    .description("Municipio del occidente del Huila")
                    .department(huila)
                    .build());

            cityRepository.save(CityEntity.builder()
                    .name("Campoalegre")
                    .description("Municipio agrícola del Huila")
                    .department(huila)
                    .build());
        };
    }
}

*/