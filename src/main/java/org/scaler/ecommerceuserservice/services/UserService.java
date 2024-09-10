package org.scaler.ecommerceuserservice.services;

import org.scaler.ecommerceuserservice.exceptions.UserAlreadyExistsException;
import org.scaler.ecommerceuserservice.models.Token;
import org.scaler.ecommerceuserservice.models.User;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */
public interface UserService {

    User signup(String name, String email, String password) throws UserAlreadyExistsException;
    Token login(String name, String password);
    void logout(String token);
    User validate(String token);
}
