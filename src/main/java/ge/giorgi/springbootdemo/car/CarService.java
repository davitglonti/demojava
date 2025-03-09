package ge.giorgi.springbootdemo.car;


import ge.giorgi.springbootdemo.car.error.InvalidPaginationException;
import ge.giorgi.springbootdemo.car.error.NotFoundException;
import ge.giorgi.springbootdemo.car.models.CarDTO;
import ge.giorgi.springbootdemo.car.models.CarRequest;
import ge.giorgi.springbootdemo.car.models.EngineDTO;
import ge.giorgi.springbootdemo.car.persistence.Car;
import ge.giorgi.springbootdemo.car.persistence.CarRepository;
import ge.giorgi.springbootdemo.car.persistence.EngineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;
    private final EngineService engineService;

//    public List<CarDTO> getCars() {
//        return carRepository.findAll().stream().map(this::mapCar).collect(Collectors.toList());
//    }

    public Page<CarDTO> getCars(int page, int pageSize) {
        if (page < 0 || pageSize <= 0) {
            throw new InvalidPaginationException("Page index must be non-negative and page size must be positive.");
        }
        return carRepository.findCars(PageRequest.of(page, pageSize));
    }

    public void addCar(CarRequest carRequest) {
        Car car = new Car();
        car.setModel(carRequest.getModel());
        car.setYear(carRequest.getYear());
        car.setDriveable(carRequest.isDriveable());
        car.setEngine(engineService.findEngine(carRequest.getEngineId()));
        carRepository.save(car);
    }

    public void updateCar(long id, CarRequest carRequest) {
        Car car=carRepository.findById(id).orElseThrow(() -> buildNotFoundException(id));
        car.setModel(carRequest.getModel());
        car.setYear(carRequest.getYear());
        car.setDriveable(carRequest.isDriveable());
        car.setEngine(engineService.findEngine(carRequest.getEngineId()));
        carRepository.save(car);

    }

    public void deleteCar(long id) {
        if(!carRepository.existsById(id))
            throw buildNotFoundException(id);
        carRepository.deleteById(id);
    }

    public CarDTO getCar(long id){
        Car car=findCar(id);
        return mapCar(car);
    }
    public Car findCar(long id) {
        return carRepository.findById(id).orElseThrow(() -> buildNotFoundException(id));
    }

    private CarDTO mapCar(Car car){
        return new  CarDTO(car.getId(), car.getModel(), car.getYear(), car.isDriveable(),
                new EngineDTO(car.getEngine().getId(), car.getEngine().getHorsepower(), car.getEngine().getCapacity()));
    }

    private NotFoundException buildNotFoundException(Long id){
        return new NotFoundException("car with id: "+ id + " was not found");
    }
}
