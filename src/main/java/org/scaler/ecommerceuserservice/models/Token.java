package org.scaler.ecommerceuserservice.models;

import com.fasterxml.jackson.databind.ser.Serializers;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Token extends BaseModel {
    private String value;
    private LocalDateTime expiresAt;
    @ManyToOne
    private User user;
}
