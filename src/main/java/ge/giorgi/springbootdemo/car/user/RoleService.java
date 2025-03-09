package ge.giorgi.springbootdemo.car.user;

import ge.giorgi.springbootdemo.car.error.NotFoundException;
import ge.giorgi.springbootdemo.car.user.persistence.Role;
import ge.giorgi.springbootdemo.car.user.persistence.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Role getRole(Long id){
        return roleRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Role with id: "+ id + " does not exists"));
    }
}
