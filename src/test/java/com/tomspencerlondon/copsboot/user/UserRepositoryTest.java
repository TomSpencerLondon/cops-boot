package com.tomspencerlondon.copsboot.user;

import static org.assertj.core.api.Assertions.assertThat;

import com.tomspencerlondon.copsboot.infrastructure.test.CopsbootDataJpaTest;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

@CopsbootDataJpaTest
public class UserRepositoryTest {
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
}