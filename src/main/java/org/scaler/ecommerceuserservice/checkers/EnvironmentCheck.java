package org.scaler.ecommerceuserservice.checkers;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */


@Component
public class EnvironmentCheck {

    @Value("${jwt.secret}")
    private String secret;

    @PostConstruct
    public void checkEnv() {
        System.out.println("JWT Secret: " + secret);
    }
}
