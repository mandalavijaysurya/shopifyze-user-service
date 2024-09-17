package org.scaler.ecommerceuserservice.dtos;

import lombok.*;
import java.util.*;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginResponseDTO {
    private String name;
    private String email;
}
