package org.scaler.ecommerceuserservice.utils;

import org.scaler.ecommerceuserservice.dtos.*;
import org.scaler.ecommerceuserservice.models.Role;
import org.scaler.ecommerceuserservice.models.Token;
import org.scaler.ecommerceuserservice.models.User;

import java.util.stream.Collectors;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */
public class UserServiceUtils {

    public static SignUpResponseDTO convertUserToSignUpResponseDTO(User user) {
        return SignUpResponseDTO.builder()
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }

    public static LoginResponseDTO convertTokenToLoginResponseDTO(Token token) {
         return LoginResponseDTO.builder()
                .name(token
                        .getUser()
                        .getName()
                )
                .email(token.
                        getUser()
                        .getEmail()
                )
                .roles(token
                        .getUser()
                        .getRoles()
                        .stream()
                        .map(Role::getName)
                        .collect(Collectors.toList())
                )
                .build();
    }
    public static UserDTO convertUserToUserDTO(User user) {
        return UserDTO.builder()
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }
    public static RoleResponseDTO convertRoleToRoleResponseDTO(String role) {
        return RoleResponseDTO.builder()
                .name(role)
                .build();
    }
}
