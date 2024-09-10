package org.scaler.ecommerceuserservice.controllers.controlleradvice;

import org.scaler.ecommerceuserservice.dtos.ErrorResponseDTO;
import org.scaler.ecommerceuserservice.exceptions.UserAlreadyExistsException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static java.time.LocalDateTime.now;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */
@ControllerAdvice
public class GlobalControllerAdvice {
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDTO> handleUserAlreadyExistsException(UserAlreadyExistsException e) {
        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .errorMessage(e.getMessage())
                .errorCode("400")
                .endPoint("")
                .timestamp(now())
                .build();
        return ResponseEntity.badRequest().body(errorResponseDTO);
    }
}
