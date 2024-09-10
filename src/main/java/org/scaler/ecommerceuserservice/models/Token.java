package org.scaler.ecommerceuserservice.models;

import java.time.LocalDateTime;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */
public class Token {
    private LocalDateTime expiresAt;
    private User user;
}
