package com.tomspencerlondon.copsboot.user;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, UserId>, UserRepositoryCustom {
  Optional<User> findByEmailIgnoreCase(String email);
}
