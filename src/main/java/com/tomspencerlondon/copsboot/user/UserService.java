package com.tomspencerlondon.copsboot.user;

import java.util.Optional;

public interface UserService {
  User createOfficer(String email, String password);

  Optional<User> getUser(UserId userId);

  Optional<User> findUserByEmail(String email);

}
