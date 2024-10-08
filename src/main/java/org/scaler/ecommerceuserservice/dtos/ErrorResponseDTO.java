package org.scaler.ecommerceuserservice.dtos;

import lombok.*;

import java.time.LocalDateTime;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ErrorResponseDTO {
    private LocalDateTime timestamp;
    private String errorCode;
    private String errorMessage;
    private String endPoint;
}
