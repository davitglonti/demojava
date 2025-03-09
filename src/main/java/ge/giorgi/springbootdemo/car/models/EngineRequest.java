package ge.giorgi.springbootdemo.car.models;


import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EngineRequest {
    @Positive
    private int horsePower;
    @Positive
    private double capacity;


}

