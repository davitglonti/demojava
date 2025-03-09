package ge.giorgi.springbootdemo.car.persistence;

import ge.giorgi.springbootdemo.car.models.CarDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;



public interface CarRepository extends JpaRepository<Car, Long> {

    @Query("SELECT NEW ge.giorgi.springbootdemo.car.models.CarDTO(c.id, c.model, c.year, c.driveable, " +
            "NEW ge.giorgi.springbootdemo.car.models.EngineDTO(e.id, e.horsepower, e.capacity))" +
            "FROM Car c JOIN c.engine e"
    )
    Page<CarDTO> findCars(Pageable pageable);


}
