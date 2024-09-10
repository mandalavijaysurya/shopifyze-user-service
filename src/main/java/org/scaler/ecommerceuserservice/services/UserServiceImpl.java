package org.scaler.ecommerceuserservice.services;

import org.apache.commons.lang3.RandomStringUtils;
import org.scaler.ecommerceuserservice.exceptions.InvalidTokenException;
import org.scaler.ecommerceuserservice.exceptions.UserAlreadyExistsException;
import org.scaler.ecommerceuserservice.exceptions.UserNotFoundException;
import org.scaler.ecommerceuserservice.models.Token;
import org.scaler.ecommerceuserservice.models.User;
import org.scaler.ecommerceuserservice.repositories.RoleRepository;
import org.scaler.ecommerceuserservice.repositories.TokenRepository;
import org.scaler.ecommerceuserservice.repositories.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static java.time.LocalDateTime.now;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */
@Service("login")
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
            throw new UserAlreadyExistsException("User already exits with email: " + email);
        }
        LocalDateTime now = now();
        User user = User.builder()
                .name(name)
                .email(email)
                .password(passwordEncoder.encode(password))
                .createdAt(now)
                .updatedAt(now)
                .build();
        return userRepository.save(user);
    }

    @Override
    public Token login(String email, String password) throws UserNotFoundException {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isEmpty()) {
            throw new UserNotFoundException("No User found with email: " + email);
        }
        User user = userOptional.get();
        if(!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Bad credentials");
        }

        Token token = createToken(user);
        return tokenRepository.save(token);
    }

    @Override
    public void logout(String token) {
        Token userToken = tokenRepository.findByValue(token).get();
        userToken.setDeleted(true);
        userToken.setUpdatedAt(now());
        tokenRepository.save(userToken);
    }

    @Override
    public User validate(String tokenValue) throws UserNotFoundException {
        Optional<Token> userToken = tokenRepository.findByValue(tokenValue);

        if(userToken.isEmpty()) {
            throw new UserNotFoundException("No User found with token: " + tokenValue);
        }

        Token token = userToken.get();
        LocalDateTime expiryDateTime = token.getExpiresAt();
        boolean isTokenDeleted = token.isDeleted();
        if(expiryDateTime.isBefore(now()) || isTokenDeleted){
            throw new InvalidTokenException("Token was expired");
        }
        return token.getUser();
    }

    private Token createToken(User user) {
        LocalDateTime currentTime = now();
        LocalDateTime expiryTime = currentTime.plusDays(6);
        String token = RandomStringUtils.secure().nextAlphanumeric(128);
        LocalDateTime now = now();
        return Token.builder()
                .value(token)
                .expiresAt(expiryTime)
                .user(user)
                .createdAt(now)
                .createdAt(now)
                .build();
    }
}
