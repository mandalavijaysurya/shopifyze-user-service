package org.scaler.ecommerceuserservice.repositories;

import org.scaler.ecommerceuserservice.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */
public interface TokenRepository extends JpaRepository<Token, UUID> {
    Optional<Token> findByValue(String value);
}