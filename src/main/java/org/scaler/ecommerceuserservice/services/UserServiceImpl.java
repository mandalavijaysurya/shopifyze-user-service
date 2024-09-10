package org.scaler.ecommerceuserservice.services;

import org.scaler.ecommerceuserservice.exceptions.UserAlreadyExistsException;
import org.scaler.ecommerceuserservice.models.Token;
import org.scaler.ecommerceuserservice.models.User;
import org.scaler.ecommerceuserservice.repositories.RoleRepository;
import org.scaler.ecommerceuserservice.repositories.TokenRepository;
import org.scaler.ecommerceuserservice.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */
@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(
            UserRepository userRepository,
            TokenRepository tokenRepository,
            RoleRepository roleRepository,
            BCryptPasswordEncoder bCryptPasswordEncoder
    ) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = bCryptPasswordEncoder;

    }

    @Override
    public User signup(String name, String email, String password) throws UserAlreadyExistsException {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isPresent()) {
            throw new UserAlreadyExistsException("User already exits with email " + email);
        }
        User user = User.builder()
                .name(name)
                .email(email)
                .password(passwordEncoder.encode(password))
                .build();
        return userRepository.save(user);
    }

    @Override
    public Token login(String name, String password) {
        return null;
    }

    @Override
    public void logout(String token) {

    }

    @Override
    public User validate(String token) {
        return null;
    }
}
