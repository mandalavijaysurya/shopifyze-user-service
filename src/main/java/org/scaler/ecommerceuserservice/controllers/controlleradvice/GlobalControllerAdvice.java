package org.scaler.ecommerceuserservice.controllers.controlleradvice;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import org.scaler.ecommerceuserservice.dtos.ErrorResponseDTO;
import org.scaler.ecommerceuserservice.exceptions.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
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
    public ResponseEntity<ErrorResponseDTO> handleUserAlreadyExistsException(UserAlreadyExistsException e, HttpServletRequest request) {
        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .errorMessage(e.getMessage())
                .errorCode("400")
                .endPoint(request.getRequestURI())
                .timestamp(now())
                .build();
        return ResponseEntity.badRequest().body(errorResponseDTO);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleUserNotFoundException(UserNotFoundException e, HttpServletRequest request) {
        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .errorMessage(e.getMessage())
                .errorCode("404")
                .timestamp(now())
                .endPoint(request.getRequestURI())
                .build();
        return ResponseEntity
                .status(404)
                .body(errorResponseDTO);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponseDTO> handleBadCredentialsException(BadCredentialsException e, HttpServletRequest request) {
        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .errorMessage(e.getMessage())
                .errorCode("401")
                .timestamp(now())
                .endPoint(request.getRequestURI())
                .build();
        return ResponseEntity.badRequest().body(errorResponseDTO);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidTokenException(InvalidTokenException e, HttpServletRequest request) {
        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .errorMessage(e.getMessage())
                .errorCode("401")
                .timestamp(now())
                .endPoint(request.getRequestURI())
                .build();
        return ResponseEntity.badRequest().body(errorResponseDTO);
    }
    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleRoleNotFoundException(RoleNotFoundException e, HttpServletRequest request) {
        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .errorMessage(e.getMessage())
                .errorCode("404")
                .timestamp(now())
                .endPoint(request.getRequestURI())
                .build();
        return ResponseEntity
                .status(404)
                .body(errorResponseDTO);
    }
    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ErrorResponseDTO> handleJwtException( JwtException e, HttpServletRequest request){
        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .errorMessage(e.getMessage())
                .errorCode("401")
                .timestamp(now())
                .endPoint(request.getRequestURI())
                .build();
        return ResponseEntity.badRequest().body(errorResponseDTO);
    }
    @ExceptionHandler(RoleAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDTO> handleRoleAlreadyExistsException(RoleAlreadyExistsException e, HttpServletRequest request){
        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .errorMessage(e.getMessage())
                .errorCode("400")
                .timestamp(now())
                .endPoint(request.getRequestURI())
                .build();
        return ResponseEntity.badRequest().body(errorResponseDTO);
    }

}
