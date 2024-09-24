package org.scaler.ecommerceuserservice.security.oauth.repositories;

import org.scaler.ecommerceuserservice.security.oauth.models.AuthorizationConsent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */
public interface AuthorizationConsentRepository extends JpaRepository<AuthorizationConsent, AuthorizationConsent.AuthorizationConsentId> {
    Optional<AuthorizationConsent> findByRegisteredClientIdAndPrincipalName(String clientId, String principalName);
    void deleteByRegisteredClientIdAndPrincipalName(String registeredClientId, String principalName);
}
