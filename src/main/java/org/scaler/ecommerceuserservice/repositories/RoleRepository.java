package org.scaler.ecommerceuserservice.repositories;

import org.scaler.ecommerceuserservice.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
}
