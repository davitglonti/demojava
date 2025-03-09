package ge.giorgi.springbootdemo.car.models;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CarDTO {
    private long id;
    private String model;
    private int year;
    private boolean driveable;
    private EngineDTO engineDTO;



}
