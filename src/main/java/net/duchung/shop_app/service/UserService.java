package net.duchung.shop_app.service;

import net.duchung.shop_app.dto.UserDto;
import net.duchung.shop_app.exception.DataNotFoundException;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    UserDto createUser(UserDto userDto) throws DataNotFoundException;
    String login(String phoneNumber, String password);
}
