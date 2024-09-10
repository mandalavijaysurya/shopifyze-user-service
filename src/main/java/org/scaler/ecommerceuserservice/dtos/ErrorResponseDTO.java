package org.scaler.ecommerceuserservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponseDTO {
    private String errorCode;
    private String errorMessage;
    private String errorDescription;
    private LocalDateTime timestamp;
    private String endPoint;
}
