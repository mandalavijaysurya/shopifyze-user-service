package org.scaler.ecommerceuserservice.exceptions;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */
public class UserAlreadyExistsException extends Exception{
        public UserAlreadyExistsException(String message) {
            super(message);
        }
        public UserAlreadyExistsException(String message, Throwable cause) {
            super(message, cause);
        }
}
