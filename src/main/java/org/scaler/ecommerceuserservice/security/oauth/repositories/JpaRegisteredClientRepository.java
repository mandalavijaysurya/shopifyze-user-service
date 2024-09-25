package org.scaler.ecommerceuserservice.security.oauth.repositories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.lang.Assert;
import org.scaler.ecommerceuserservice.security.oauth.models.Client;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.jackson2.OAuth2AuthorizationServerJackson2Module;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */

@Component
public class JpaRegisteredClientRepository implements RegisteredClientRepository {
    private final ClientRepository clientRepository;
    private final ObjectMapper objectMapper;

    public JpaRegisteredClientRepository(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
        ClassLoader classLoader = JpaRegisteredClientRepository.class.getClassLoader();
        List<Module> securityModules = SecurityJackson2Modules.getModules(classLoader);
        this.objectMapper = new ObjectMapper();
        objectMapper.registerModules(securityModules);
        objectMapper.registerModule(new OAuth2AuthorizationServerJackson2Module());
    }


    @Override
    public void save(RegisteredClient registeredClient) {
        Assert.notNull(registeredClient, "registeredClient cannot be null");
        this.clientRepository.save(toEntity(registeredClient));
    }

    @Override
    public RegisteredClient findById(String id) {
        Assert.hasText(id, "id cannot be empty");
        return this.clientRepository.findByClientId(id).map(this::toObject).orElse(null);
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        Assert.hasText(clientId, "clientId cannot be empty");
        return this.clientRepository.findByClientId(clientId).map(this::toObject).orElse(null);
    }

    private RegisteredClient toObject(Client client){
        Set<String> clientAuthenticationMethods = StringUtils.commaDelimitedListToSet(client.getClientAuthenticationMethods());
        Set<String> authorizationGrantTypes = StringUtils.commaDelimitedListToSet(client.getAuthorizationGrantTypes());
        Set<String> redirectUris = StringUtils.commaDelimitedListToSet(client.getRedirectUris());
        Set<String> postLogoutRedirectUris = StringUtils.commaDelimitedListToSet(client.getPostLogoutRedirectUris());
        Set<String> clientScopes = StringUtils.commaDelimitedListToSet(client.getScopes());

        RegisteredClient.Builder builder = RegisteredClient.withId(client.getId())
                .clientId(client.getClientId())
                .clientSecret(client.getClientSecret())
                .clientAuthenticationMethods(authenticationMethods ->
                        clientAuthenticationMethods.forEach(authenticationMethod ->
                                        authenticationMethods.add(resolveClientAuthenticationMethod(authenticationMethod))
                                )
                )
                .authorizationGrantTypes(grantTypes ->
                        authorizationGrantTypes.forEach(grantType ->
                                        grantTypes.add(resolveAuthorizationGrantType(grantType))
                                )
                )
                .redirectUris(uris -> uris.addAll(redirectUris))
                .postLogoutRedirectUris(uris -> uris.addAll(postLogoutRedirectUris))
                .scopes(scopes -> scopes.addAll(clientScopes));
        Map<String, Object> clientSettingsMap = parseMap(client.getClientSettings());
        builder.clientSettings(ClientSettings.withSettings(clientSettingsMap).build());

        Map<String, Object> tokenSettingsMap = parseMap(client.getTokenSettings());
        builder.tokenSettings(TokenSettings.withSettings(tokenSettingsMap).build());

        return builder.build();
    }

    private Map<String, Object> parseMap(String data){
        try{
            return this.objectMapper.readValue(data, new TypeReference<Map<String, Object>>() {
            });
        }catch (Exception ex){
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    private String writeMap(Map<String, Object> data){
        try{
            return this.objectMapper.writeValueAsString(data);
        }catch (Exception ex){
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }
    private static ClientAuthenticationMethod resolveClientAuthenticationMethod(String clientAuthenticationMethod){
        if(ClientAuthenticationMethod.CLIENT_SECRET_BASIC.getValue().equals(clientAuthenticationMethod))
            return ClientAuthenticationMethod.CLIENT_SECRET_BASIC;
        else if(ClientAuthenticationMethod.CLIENT_SECRET_POST.getValue().equals(clientAuthenticationMethod))
            return ClientAuthenticationMethod.CLIENT_SECRET_POST;
        else if(ClientAuthenticationMethod.PRIVATE_KEY_JWT.getValue().equals(clientAuthenticationMethod))
            return ClientAuthenticationMethod.PRIVATE_KEY_JWT;
        else if(ClientAuthenticationMethod.NONE.getValue().equals(clientAuthenticationMethod))
            return ClientAuthenticationMethod.NONE;
        return new ClientAuthenticationMethod(clientAuthenticationMethod);
    }

    private static AuthorizationGrantType resolveAuthorizationGrantType(String authorizationGrantType){
        if(AuthorizationGrantType.AUTHORIZATION_CODE.getValue().equals(authorizationGrantType))
            return AuthorizationGrantType.AUTHORIZATION_CODE;
        else if(AuthorizationGrantType.CLIENT_CREDENTIALS.getValue().equals(authorizationGrantType))
            return AuthorizationGrantType.CLIENT_CREDENTIALS;
        else if(AuthorizationGrantType.REFRESH_TOKEN.getValue().equals(authorizationGrantType))
            return AuthorizationGrantType.REFRESH_TOKEN;
        return new AuthorizationGrantType(authorizationGrantType);
    }

    private Client toEntity(RegisteredClient registeredClient){
        Client client = new Client();
        client.setId(registeredClient.getId());
        client.setClientId(registeredClient.getClientId());
        client.setClientName(registeredClient.getClientName());
        client.setClientIdIssuedAt(registeredClient.getClientIdIssuedAt());
        client.setClientSecret(registeredClient.getClientSecret());
        client.setClientAuthenticationMethods(StringUtils.collectionToCommaDelimitedString(registeredClient.getClientAuthenticationMethods()));
        client.setAuthorizationGrantTypes(StringUtils.collectionToCommaDelimitedString(registeredClient.getAuthorizationGrantTypes()));
        client.setRedirectUris(StringUtils.collectionToCommaDelimitedString(registeredClient.getRedirectUris()));
        client.setPostLogoutRedirectUris(StringUtils.collectionToCommaDelimitedString(registeredClient.getPostLogoutRedirectUris()));
        client.setScopes(StringUtils.collectionToCommaDelimitedString(registeredClient.getScopes()));
        client.setClientSettings(writeMap(registeredClient.getClientSettings().getSettings()));
        client.setTokenSettings(writeMap(registeredClient.getTokenSettings().getSettings()));
        return client;
    }
}
