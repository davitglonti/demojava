package ge.giorgi.springbootdemo.car.user.auth;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import ge.giorgi.springbootdemo.car.error.InvalidLoginException;
import ge.giorgi.springbootdemo.car.user.UserService;
import ge.giorgi.springbootdemo.car.user.persistence.AppUser;
import ge.giorgi.springbootdemo.car.user.persistence.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoginService {

    @Value("${jwt.secret-key}")
    private String secretKey;

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public LoginResponse login(LoginRequest loginRequest) {
        AppUser appUser = userService.getUser(loginRequest.getUsername());
        if (passwordEncoder.matches(loginRequest.getPassword(), appUser.getPassword())) {
            return generateLoginResponse(appUser);
        }
        throw new InvalidLoginException("invalid-login");
    }

    private LoginResponse generateLoginResponse(AppUser user) {
        try {
            JWTClaimsSet claims = new JWTClaimsSet.Builder()
                    .subject(user.getUsername())
                    .claim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toSet()))
                    .issuer("carsapp.ge")
                    .expirationTime(new Date(System.currentTimeMillis() + 3600 * 1000))
                    .build();

            JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);
            SignedJWT signedJWT = new SignedJWT(header, claims);
            signedJWT.sign(new MACSigner(secretKey.getBytes()));

            return new LoginResponse(signedJWT.serialize());

        } catch (Exception e) {
            throw new InvalidLoginException("Failed to generate token");
        }
    }
}

