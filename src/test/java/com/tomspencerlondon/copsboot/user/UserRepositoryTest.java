package com.tomspencerlondon.copsboot.user;

import static org.assertj.core.api.Assertions.assertThat;

import com.tomspencerlondon.orm.jpa.InMemoryUniqueIdGenerator;
import com.tomspencerlondon.orm.jpa.UniqueIdGenerator;
import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@DataJpaTest
public class UserRepositoryTest {

  @Autowired
  private UserRepository repository;

  @Test
  void savesUser() {
    HashSet<UserRole> roles = new HashSet<>();
    roles.add(UserRole.OFFICER);
    User user = repository.save(
        new User(repository.nextId(),
            "alex.foley@beverly-hills.com",
            "my-secret-pwd",
            roles));

    assertThat(user)
        .isNotNull();

    assertThat(repository.count())
        .isEqualTo(1L);
  }

  @Test
  void findByEmail() {
    User user = Users.newRandomOfficer();
    repository.save(user);
    Optional<User> optional = repository.findByEmailIgnoreCase(user.getEmail());

    assertThat(optional)
        .isNotEmpty()
        .contains(user);
  }

  @Test
  void findByEmailIgnoringCase() {
    User user = Users.newRandomOfficer();
    repository.save(user);
    Optional<User> optional = repository.findByEmailIgnoreCase(user.getEmail().toUpperCase());

    assertThat(optional)
        .isNotEmpty()
        .contains(user);
  }

  @Test
  void findByEmail_unknownEmail() {
    User user = Users.newRandomOfficer();
    repository.save(user);
    Optional<User> optional = repository.findByEmailIgnoreCase("will.not@find.me");

    assertThat(optional)
        .isEmpty();
  }

  @TestConfiguration
  static class TestConfig {
    @Bean
    public UniqueIdGenerator<UUID> generator() {
      return new InMemoryUniqueIdGenerator();
    }
  }
}
