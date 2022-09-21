package com.tomspencerlondon.copsboot;

import com.tomspencerlondon.orm.jpa.InMemoryUniqueIdGenerator;
import com.tomspencerlondon.orm.jpa.UniqueIdGenerator;
import java.util.UUID;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
public class CopsbootConfiguration {

  @Bean
  public UniqueIdGenerator<UUID> uniqueIdGenerator() {
    return new InMemoryUniqueIdGenerator();
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
