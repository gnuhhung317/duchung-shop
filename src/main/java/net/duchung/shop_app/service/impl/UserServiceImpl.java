package net.duchung.shop_app.service.impl;

import net.duchung.shop_app.component.JwtUtil;
import net.duchung.shop_app.dto.UserDto;
import net.duchung.shop_app.entity.Role;
import net.duchung.shop_app.entity.User;
import net.duchung.shop_app.exception.DataNotFoundException;
import net.duchung.shop_app.repository.RoleRepository;
import net.duchung.shop_app.repository.UserRepository;
import net.duchung.shop_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;
    @Override
    public UserDto createUser(UserDto userDto) throws RuntimeException {
        String phoneNumber = userDto.getPhoneNumber();
        if(!userDto.getPassword().equals(userDto.getRetypePassword())){
            throw new RuntimeException("Password does not match");
        }
        //check if phone number already exists
        if(userRepository.existsByPhoneNumber(phoneNumber)){
            throw new DataIntegrityViolationException("Phone number already exists");
        }
        User user = toEntity(userDto);
        if(user.getFacebookAccountId()==0&&user.getGoogleAccountId()==0){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        User savedUser = userRepository.save(user);
        return toDto(savedUser);
    }

    @Override
    public String login(String phoneNumber, String password) {
        User user = userRepository.findByPhoneNumber(phoneNumber).orElseThrow(() -> new DataNotFoundException("Invalid phone number or password"));
        if(user.getFacebookAccountId()==0&&user.getGoogleAccountId()==0){
            if(!passwordEncoder.matches(password, user.getPassword())){
                throw new BadCredentialsException("Invalid phone number or password");
            }
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(phoneNumber, password,user.getAuthorities());
        authenticationManager.authenticate(authenticationToken);
        return jwtUtil.generateToken(user);

    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(this::toDto).toList();
    }

    public UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFullName(user.getFullName());
        userDto.setAddress(user.getAddress());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setPassword(user.getPassword());
        userDto.setDateOfBirth(user.getDateOfBirth());
        userDto.setFacebookAccountId(user.getFacebookAccountId());
        userDto.setGoogleAccountId(user.getGoogleAccountId());
        userDto.setRoleId(user.getRole().getId());
        return userDto;
    }
    public User toEntity(UserDto userDto) {
        User user = new User();

        user.setFullName(userDto.getFullName());
        user.setAddress(userDto.getAddress());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setPassword(userDto.getPassword());
        user.setDateOfBirth(userDto.getDateOfBirth());
        user.setFacebookAccountId(userDto.getFacebookAccountId());
        user.setGoogleAccountId(userDto.getGoogleAccountId());

        Role role = roleRepository.findById(userDto.getRoleId()).orElseThrow(() -> new DataNotFoundException("Role not found"));
        user.setRole(role);
        return user;
    }
}
