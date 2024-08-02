package net.duchung.shop_app.controller;

import jakarta.validation.Valid;
import net.duchung.shop_app.dto.UserDto;
import net.duchung.shop_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserDto userDto,
                                      BindingResult bindingResult) {

        try {
            if (bindingResult.hasErrors()) {
                List<String> errors = bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
                return ResponseEntity.badRequest().body(errors);
            }        return ResponseEntity.ok(userService.createUser(userDto));

        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String phoneNumber, @RequestParam String password) {
        return ResponseEntity.ok(userService.login(phoneNumber, password));
    }

}