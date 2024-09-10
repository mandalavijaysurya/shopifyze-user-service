package org.scaler.ecommerceuserservice.dtos;

import lombok.*;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */
@Getter
@Setter
public class SignUpRequestDTO {
    private String name;
    private String email;
    private String password;
    // TODO: Yet to add roles here
}
