package ge.giorgi.springbootdemo.car.user;

import ge.giorgi.springbootdemo.car.user.model.UserRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static ge.giorgi.springbootdemo.car.security.AuthorizationConstants.ADMIN;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@PreAuthorize(ADMIN)
public class UserController {

    private final UserService userService;

    @PostMapping
    public void createUser(@RequestBody @Valid UserRequest userRequest){
        userService.createUser(userRequest);
    }
}
