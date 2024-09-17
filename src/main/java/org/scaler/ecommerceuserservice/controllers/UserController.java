package org.scaler.ecommerceuserservice.controllers;

import org.scaler.ecommerceuserservice.dtos.*;
import org.scaler.ecommerceuserservice.exceptions.UserAlreadyExistsException;
import org.scaler.ecommerceuserservice.exceptions.UserNotFoundException;
import org.scaler.ecommerceuserservice.models.Token;
import org.scaler.ecommerceuserservice.models.User;
import org.scaler.ecommerceuserservice.services.UserService;
import org.scaler.ecommerceuserservice.utils.UserServiceUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.scaler.ecommerceuserservice.utils.UserServiceUtils.*;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */
@RestController
@RequestMapping("/auth")
public class UserController {
    private final UserService userService;

    public UserController(@Qualifier("login") UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDTO> signUp(@RequestBody SignUpRequestDTO requestDTO) throws UserAlreadyExistsException {
        String name = requestDTO.getName();
        String email = requestDTO.getEmail();
        String password = requestDTO.getPassword();

        User user = userService.signup(name, email, password);

        return ResponseEntity.status(HttpStatus.CREATED).body(convertUserToSignUpResponseDTO(user));

    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) throws UserNotFoundException {
        String email = loginRequestDTO.getEmail();
        String password = loginRequestDTO.getPassword();
        Token userToken = userService.login(email, password);
        return ResponseEntity.accepted().header("Authorization", userToken.getValue()).body(convertTokenToLoginResponseDTO(userToken));
    }
    @PostMapping("/logout")
    public void logout(@RequestHeader("Authorization") String token){
        userService.logout(token);
    }
    @GetMapping("/validate")
    public ResponseEntity<UserDTO> validate(@RequestHeader("Authorization") String token) throws UserNotFoundException {
        User user = userService.validate(token);
        return ResponseEntity.ok().body(convertUserToUserDTO(user));
    }
    @PostMapping("/role")
    public ResponseEntity<RoleResponseDTO> addRole(@RequestBody RoleRequestDTO roleRequestDTO) {
        String roleName = roleRequestDTO.getName();
        userService.addRole(roleName);
        RoleResponseDTO roleResponseDTO = UserServiceUtils.convertRoleToRoleResponseDTO(roleName);
        return ResponseEntity.status(HttpStatus.CREATED).body(roleResponseDTO);
    }
}
