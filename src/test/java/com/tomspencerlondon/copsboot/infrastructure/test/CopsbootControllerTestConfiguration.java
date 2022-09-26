package com.tomspencerlondon.copsboot.infrastructure.test;

import com.tomspencerlondon.copsboot.infrastructure.security.OAuth2ServerConfiguration;
import com.tomspencerlondon.copsboot.infrastructure.security.SecurityConfiguration;
import com.tomspencerlondon.copsboot.infrastructure.security.StubUserDetailsService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@TestConfiguration
@Import(OAuth2ServerConfiguration.class)
public class CopsbootControllerTestConfiguration {
  @Bean
  public UserDetailsService userDetailsService() {
    return new StubUserDetailsService();
  }

  @Bean
  public SecurityConfiguration securityConfiguration() {
    return new SecurityConfiguration();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

  @Bean
  public TokenStore tokenStore() {
    return new InMemoryTokenStore();
  }
}
