package security.registerlogin.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import security.registerlogin.dto.UserDto;
import security.registerlogin.entity.Role;
import security.registerlogin.entity.User;
import security.registerlogin.repository.RoleRepository;
import security.registerlogin.repository.UserRepository;
import security.registerlogin.service.UserService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;



    @Override
    public void saveUser(UserDto userDto) {
        User user=new User();
        user.setName(userDto.getFirstName()+" "+userDto.getLastName());
        user.setEmail(userDto.getEmail());
        //encrypt the password using spring security
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        Role role=roleRepository.findByName("ROLE_ADMIN");
        if(role==null){
            role=checkRoleExist();
        }

        user.setRoles(Arrays.asList(role));
        userRepository.save(user);

    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users=userRepository.findAll();
        return users.stream()
                .map((user)->mapToUserDto(user))
                .collect(Collectors.toList());
    }



    private UserDto mapToUserDto(User user){
        UserDto userDto=new UserDto();
        String[] str=user.getName().split(" ");
        userDto.setFirstName(str[0]);
        userDto.setLastName(str[1]);

        userDto.setEmail(user.getEmail());
        return userDto;
    }


    private Role checkRoleExist(){
        Role role=new Role();
        role.setName("ROLE_ADMIN");
        return roleRepository.save(role);
    }
}
