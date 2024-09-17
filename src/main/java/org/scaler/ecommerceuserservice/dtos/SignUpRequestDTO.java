package org.scaler.ecommerceuserservice.dtos;

import lombok.*;

import java.util.List;

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
    private List<String> role;
}
