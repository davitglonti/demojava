package ge.giorgi.springbootdemo.car.user.auth;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {

    public String accessToken;

}
