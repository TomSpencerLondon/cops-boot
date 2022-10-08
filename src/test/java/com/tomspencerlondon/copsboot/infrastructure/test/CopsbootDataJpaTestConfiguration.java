package com.tomspencerlondon.copsboot.infrastructure.test;

import com.tomspencerlondon.orm.jpa.InMemoryUniqueIdGenerator;
import com.tomspencerlondon.orm.jpa.UniqueIdGenerator;
import java.util.UUID;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class CopsbootDataJpaTestConfiguration {
  @Bean
  public UniqueIdGenerator<UUID> generator() {
    return new InMemoryUniqueIdGenerator();
  }
}
