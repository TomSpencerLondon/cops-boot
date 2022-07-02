package com.tomspencerlondon.copsboot;

import com.tomspencerlondon.orm.jpa.InMemoryUniqueIdGenerator;
import com.tomspencerlondon.orm.jpa.UniqueIdGenerator;
import java.util.UUID;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CopsbootApplication {

  public static void main(String[] args) {
    SpringApplication.run(CopsbootApplication.class, args);
  }


  @Bean
  public UniqueIdGenerator<UUID> uniqueIdGenerator() {
    return new InMemoryUniqueIdGenerator();
  }
}
