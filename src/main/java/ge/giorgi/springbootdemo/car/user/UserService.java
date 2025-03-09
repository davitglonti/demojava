package ge.giorgi.springbootdemo.car.user;


import ge.giorgi.springbootdemo.car.error.NotFoundException;
import ge.giorgi.springbootdemo.car.user.persistence.AppUser;
import ge.giorgi.springbootdemo.car.user.persistence.AppUserRepository;
import ge.giorgi.springbootdemo.car.user.model.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    public void createUser(UserRequest userRequest){
        AppUser user = new AppUser();
        user.setUsername(userRequest.getUsername());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setRoles(userRequest.getRoleIds().stream().
                map(roleService::getRole).collect(Collectors.toSet()));

        appUserRepository.save(user);
    }

    public AppUser getUser(String username){
        return appUserRepository.findByUsername(username).orElseThrow(()-> new NotFoundException("User not found"));
    }
}
