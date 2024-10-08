package org.scaler.ecommerceuserservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */
@Entity(name = "tokens")
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Token extends BaseModel {
    private String value;
    private LocalDateTime expiresAt;
    @ManyToOne
    private User user;
}
