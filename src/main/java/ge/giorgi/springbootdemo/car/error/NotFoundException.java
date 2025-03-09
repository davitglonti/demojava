package ge.giorgi.springbootdemo.car.error;


public class NotFoundException extends RuntimeException{

    public NotFoundException(String message){
        super(message);
    }

}
