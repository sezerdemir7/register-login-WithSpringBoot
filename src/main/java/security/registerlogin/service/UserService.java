package security.registerlogin.service;

import security.registerlogin.dto.UserDto;
import security.registerlogin.entity.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    User findUserByEmail(String email);

    List<UserDto> findAllUsers();
}
