package com.tomspencerlondon.copsboot.infrastructure.security;

import com.tomspencerlondon.copsboot.user.User;
import com.tomspencerlondon.copsboot.user.UserId;
import com.tomspencerlondon.copsboot.user.UserRole;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class ApplicationUserDetails extends org.springframework.security.core.userdetails.User { //<1>

  private static final String ROLE_PREFIX = "ROLE_";

  private final UserId userId;

  public ApplicationUserDetails(User user) {
    super(user.getEmail(), user.getPassword(), createAuthorities(user.getRoles()));
    this.userId = user.getId();
  }

  public UserId getUserId() {
    return userId;
  }

  private static Collection<SimpleGrantedAuthority> createAuthorities(Set<UserRole> roles) {
    return roles.stream()
        .map(userRole -> new SimpleGrantedAuthority(ROLE_PREFIX + userRole.name()))
        .collect(Collectors.toSet());
  }
}
