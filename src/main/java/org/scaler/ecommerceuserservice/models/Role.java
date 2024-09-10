package org.scaler.ecommerceuserservice.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */
@Entity(name = "roles")
@Getter
@Setter
public class Role extends BaseModel {
    private String name;
}
