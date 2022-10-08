package com.tomspencerlondon.copsboot.user;

import static org.assertj.core.api.Assertions.assertThat;

import com.tomspencerlondon.copsboot.infrastructure.SpringProfiles;
import com.tomspencerlondon.orm.jpa.InMemoryUniqueIdGenerator;
import com.tomspencerlondon.orm.jpa.UniqueIdGenerator;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles(SpringProfiles.INTEGRATION_TEST)
public class UserRepositoryIntegrationTest {
  @Autowired
  private UserRepository repository;
  @PersistenceContext
  private EntityManager entityManager;
  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Test
  public void testSaveUser() {
    Set<UserRole> roles = new HashSet<>();
    roles.add(UserRole.OFFICER);
    User user = repository.save(new User(repository.nextId(),
        "alex.foley@beverly-hills.com",
        "my-secret-pwd",
        roles));
    assertThat(user).isNotNull();
    assertThat(repository.count()).isEqualTo(1L);
    entityManager.flush();
    assertThat(jdbcTemplate.queryForObject("SELECT count(*) FROM copsboot_user", Long.class)).isEqualTo(1L);

    assertThat(jdbcTemplate.queryForObject("SELECT count(*) FROM user_roles", Long.class)).isEqualTo(1L);
    assertThat(jdbcTemplate.queryForObject("SELECT roles FROM user_roles", String.class)).isEqualTo("OFFICER");
  }

  @TestConfiguration
  static class TestConfig {
    @Bean
    public UniqueIdGenerator<UUID> generator() {
      return new InMemoryUniqueIdGenerator();
    }
  }
}