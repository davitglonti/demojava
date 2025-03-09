package ge.giorgi.springbootdemo.car.persistence;

import ge.giorgi.springbootdemo.car.models.EngineDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface EngineRepository extends JpaRepository<Engine, Long> {

    @Query("SELECT NEW ge.giorgi.springbootdemo.car.models.EngineDTO(e.id, e.horsepower, e.capacity)" +
            "FROM Engine e WHERE e.capacity = :capacity ")
    Page<EngineDTO> findEngines(double capacity, Pageable pageable);

    @Query("SELECT COUNT(c) > 0 FROM Car c WHERE c.engine.id = :engineId")
    boolean existsCarByEngineId(Long engineId);
}
