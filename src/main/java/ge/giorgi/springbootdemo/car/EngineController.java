package ge.giorgi.springbootdemo.car;

import ge.giorgi.springbootdemo.car.models.CarDTO;
import ge.giorgi.springbootdemo.car.models.EngineDTO;
import ge.giorgi.springbootdemo.car.models.EngineRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static ge.giorgi.springbootdemo.car.security.AuthorizationConstants.ADMIN;
import static ge.giorgi.springbootdemo.car.security.AuthorizationConstants.USER_OR_ADMIN;

@RestController
@RequestMapping("/engines")
@RequiredArgsConstructor
public class EngineController {

    private final EngineService engineService;

//    @GetMapping
//    public List<EngineDTO> getEngines(){
//        return engineService.getEngines();
//    }

    @GetMapping
    @PreAuthorize(USER_OR_ADMIN)
    public Page<EngineDTO> getEngines(@RequestParam int page, @RequestParam int pageSize, @RequestParam double capacity){
        return engineService.getEngines(page, pageSize, capacity);
    }

    @GetMapping("{id}")
    @PreAuthorize(USER_OR_ADMIN)
    public ResponseEntity<EngineDTO> getEngine(@PathVariable long id){
        EngineDTO engine=engineService.getEngine(id);
        return ResponseEntity.ok(engine);

    }

    @PutMapping("{id}")
    @PreAuthorize(ADMIN)
    public ResponseEntity<EngineDTO> updateEngine(@PathVariable long id, @RequestBody @Valid EngineRequest engineRequest){
        EngineDTO  engineDTO=engineService.updateEngine(id, engineRequest);
        return ResponseEntity.status(HttpStatus.OK).body(engineDTO);

    }
    @PostMapping
    @PreAuthorize(ADMIN)
    public ResponseEntity<String> addEngine(@RequestBody @Valid EngineRequest engineRequest){
        engineService.createEngine(engineRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("Engine added successfully");
    }

    @DeleteMapping("{id}")
    @PreAuthorize(ADMIN)
    public ResponseEntity<Void> deleteEngine(@PathVariable long id){
        engineService.deleteEngine(id);
        return ResponseEntity.noContent().build();
    }



}
