package ge.giorgi.springbootdemo.car.error;

public class InvalidLoginException extends RuntimeException{

    public InvalidLoginException(String exception){
        super(exception);
    }
}
