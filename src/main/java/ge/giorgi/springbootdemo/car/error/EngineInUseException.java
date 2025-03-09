package ge.giorgi.springbootdemo.car.error;

public class EngineInUseException extends RuntimeException {
    public EngineInUseException(String message) {
        super(message);
    }
}
