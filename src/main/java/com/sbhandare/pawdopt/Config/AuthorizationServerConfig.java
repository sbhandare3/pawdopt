package com.sbhandare.pawdopt.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private TokenStore tokenStore = new InMemoryTokenStore();
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Value("${security.oauth2.client.client-id}")
    private String client_id;
    @Value("${security.oauth2.client.client-secret}")
    private String client_secret;

    /**
     * Setting up the endpointsconfigurer authentication manager.
     * The AuthorizationServerEndpointsConfigurer defines the authorization and token endpoints and the token services.
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .tokenStore(this.tokenStore)
                .authenticationManager(authenticationManager).userDetailsService(userDetailsService);

    }

    /**
     * Setting up the clients with a clientId, a clientSecret, a scope, the grant types and the authorities.
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients
                .inMemory()
                .withClient(client_id)
                .authorizedGrantTypes("client_credentials", "password", "refresh_token")
                .authorities("ROLE_CLIENT","ROLE_TRUSTED_CLIENT")
                .scopes("read","write","trust")
                .resourceIds("oauth2-resource")
                //.accessTokenValiditySeconds(3600)
                .secret(client_secret);
    }

    /**
     * We here defines the security constraints on the token endpoint.
     * We set it up to isAuthenticated, which returns true if the user is not anonymous
     * @param security the AuthorizationServerSecurityConfigurer.
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .checkTokenAccess("isAuthenticated()");
    }
}