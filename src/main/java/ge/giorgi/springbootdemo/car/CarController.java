package ge.giorgi.springbootdemo.car;


import ge.giorgi.springbootdemo.car.models.CarDTO;
import ge.giorgi.springbootdemo.car.models.CarRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static ge.giorgi.springbootdemo.car.security.AuthorizationConstants.ADMIN;
import static ge.giorgi.springbootdemo.car.security.AuthorizationConstants.USER_OR_ADMIN;

@RestController
@RequestMapping("/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

//    public CarController(CarService carService){
//        this.carService=carService;
//    }

    @PostMapping
    @PreAuthorize(ADMIN)
    public ResponseEntity<String> addCar(@RequestBody @Valid CarRequest carRequest) {
        carService.addCar(carRequest);
        return ResponseEntity.ok("Car added successfully!");
    }

    @PutMapping("{id}")
    @PreAuthorize(ADMIN)
    public ResponseEntity<String> updateCar(@PathVariable long id, @RequestBody @Valid CarRequest carRequest) {
        carService.updateCar(id, carRequest);
        return ResponseEntity.ok("Car updated successfully!");
    }

    @DeleteMapping("{id}")
    @PreAuthorize(ADMIN)
    public ResponseEntity<String> deleteCar(@PathVariable long id) {
        carService.deleteCar(id);
        return ResponseEntity.ok("Car deleted successfully!");
    }

    @GetMapping("{id}")
    @PreAuthorize(USER_OR_ADMIN)
    ResponseEntity<CarDTO> getCar(@PathVariable long id){
        CarDTO cartofind=carService.getCar(id);
        return ResponseEntity.ok(cartofind);
    }
//    @GetMapping
//    public List<CarDTO> getCars(){
//        return carService.getCars();
//    }

    @GetMapping
    @PreAuthorize(USER_OR_ADMIN)
    public Page<CarDTO> getCars(@RequestParam int page, @RequestParam int pageSize){
        return carService.getCars(page, pageSize);
    }
}
