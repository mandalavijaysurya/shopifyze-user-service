package org.scaler.ecommerceuserservice.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.scaler.ecommerceuserservice.dtos.SendEmailDto;
import org.scaler.ecommerceuserservice.exceptions.RoleAlreadyExistsException;
import org.scaler.ecommerceuserservice.exceptions.RoleNotFoundException;
import org.scaler.ecommerceuserservice.exceptions.UserAlreadyExistsException;
import org.scaler.ecommerceuserservice.exceptions.UserNotFoundException;
import org.scaler.ecommerceuserservice.models.Role;
import org.scaler.ecommerceuserservice.models.Token;
import org.scaler.ecommerceuserservice.models.User;
import org.scaler.ecommerceuserservice.repositories.RoleRepository;
import org.scaler.ecommerceuserservice.repositories.TokenRepository;
import org.scaler.ecommerceuserservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static java.sql.Timestamp.valueOf;
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
    private SecretKey key;
    @Value("${jwt.secret}")
    private String secret;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;


    @PostConstruct
    public void checkEnv() {
        System.out.println("JWT Secret: " + secret);
        key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
    public UserServiceImpl(
            UserRepository userRepository,
            TokenRepository tokenRepository,
            RoleRepository roleRepository,
            BCryptPasswordEncoder bCryptPasswordEncoder,
            KafkaTemplate<String, String> template,
            ObjectMapper objectMapper
    ) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = bCryptPasswordEncoder;
        this.kafkaTemplate = template;
        this.objectMapper = objectMapper;
    }

    @Override
    public User signup(String name, String email, String password) throws UserAlreadyExistsException {
        Optional<User> userOptional = userRepository.findByEmail(email);
        Optional<Role> roleOptional = roleRepository.findByName("USER");
        if(userOptional.isPresent()) {
            throw new UserAlreadyExistsException("User already exits with email: " + email);
        }
        if(roleOptional.isEmpty()) {
            throw new RoleNotFoundException("The required role is not present yet. Please contact support!");
        }
        Role role = roleOptional.get();
        LocalDateTime now = now();
        User user = User.builder()
                .name(name)
                .email(email)
                .roles(List.of(role))
                .password(passwordEncoder.encode(password))
                .createdAt(now)
                .updatedAt(now)
                .build();

        SendEmailDto sendEmailDto = SendEmailDto.builder()
                .toEmail("ravalimanda44@gmail.com")
                .fromEmail("mandala.vijay.surya@gmail.com")
                .body("Hello!!!!")
                .subject("Hello from email service")
                .build();

        String sendEmailDtoString = null;
        try{
            sendEmailDtoString = objectMapper.writeValueAsString(sendEmailDto);
        }catch (Exception ex){
            System.out.println("Something went wrong while converting SendEmailDto to String");
        }
        kafkaTemplate.send("email", sendEmailDtoString);

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
    public User validate(String tokenValue) throws JwtException {
        Jws<Claims> claimsJws = Jwts
                .parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(tokenValue);
        String email = claimsJws.getPayload().get("email", String.class);
        String name = claimsJws.getPayload().get("name", String.class);
        String roles = claimsJws.getPayload().get("roles", String.class);
        return User.builder()
                .email(email)
                .name(name)
                .roles(Arrays.stream(roles.split(",")).map(Role::new).collect(Collectors.toList()))
                .build();
    }

    @Override
    public Role addRole(String role){
        Optional<Role> roleOptional = roleRepository.findByName(role);
        if(roleOptional.isPresent()) {
            throw new RoleAlreadyExistsException("Role already exits with name: " + role);
        }
        LocalDateTime now = now();
        Role newRole = Role.builder()
                .name(role)
                .createdAt(now)
                .updatedAt(now)
                .build();
        return roleRepository.save(newRole);
    }

    private Token createToken(User user) {
        Map<String, String> claims = new HashMap<>();
        claims.put("email", user.getEmail());
        claims.put("name", user.getName());
        claims.put("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.joining(",")));
        LocalDateTime currentTime = now();
        LocalDateTime expiryTime = currentTime.plusDays(6);
        LocalDateTime now = now();
        String jws = Jwts.builder()
                .claims(claims)
                .expiration(valueOf(expiryTime))
                .issuedAt(valueOf(now))
                .signWith(key)
                .compact();
        return Token.builder()
                .value(jws)
                .expiresAt(expiryTime)
                .user(user)
                .createdAt(now)
                .createdAt(now)
                .build();
    }
}
