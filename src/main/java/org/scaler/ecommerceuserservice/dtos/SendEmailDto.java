package org.scaler.ecommerceuserservice.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */

@Getter
@Setter
@Builder
public class SendEmailDto {
    private String fromEmail;
    private String toEmail;
    private String subject;
    private String body;
}
