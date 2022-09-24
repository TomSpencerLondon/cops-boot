package com.tomspencerlondon.copsboot.user;

import com.tomspencerlondon.copsboot.user.User;
import com.tomspencerlondon.copsboot.user.UserId;
import java.util.UUID;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Users {
  private static final PasswordEncoder PASSWORD_ENCODER = PasswordEncoderFactories.createDelegatingPasswordEncoder();
  public static final String OFFICER_EMAIL = "officer@example.com";
  public static final String OFFICER_PASSWORD = "officer";
  public static final String CAPTAIN_EMAIL = "captain@example.com";
  public static final String CAPTAIN_PASSWORD = "captain";

  private static User OFFICER = User.createOfficer(newRandomId(),
      OFFICER_EMAIL,
      PASSWORD_ENCODER.encode(OFFICER_PASSWORD));

  private static User CAPTAIN = User.createCaptain(newRandomId(),
      CAPTAIN_EMAIL,
      PASSWORD_ENCODER.encode(CAPTAIN_PASSWORD));


  public static UserId newRandomId() {
    return new UserId(UUID.randomUUID());
  }

  public static User newRandomOfficer() {
    return newRandomOfficer(newRandomId());
  }

  public static User newRandomOfficer(UserId userId) {
    String uniqueId = userId.asString().substring(0, 5);
    return User.createOfficer(userId,
        "user-" + uniqueId + "@example.com",
        PASSWORD_ENCODER.encode("user"));
  }

  public static User officer() {
    return OFFICER;
  }

  public static User captain() {
    return CAPTAIN;
  }

  private Users() {
  }
}
