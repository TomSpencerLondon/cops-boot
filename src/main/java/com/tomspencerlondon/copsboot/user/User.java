package com.tomspencerlondon.copsboot.user;

import com.google.common.collect.Sets;
import com.sun.istack.NotNull;
import com.tomspencerlondon.orm.jpa.AbstractEntity;
import java.util.Set;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Table;

@Entity
@Table(name = "copsboot_user")
public class User extends AbstractEntity<UserId> {
  private String email;
  private String password;

  @ElementCollection(fetch = FetchType.EAGER)
  @Enumerated(EnumType.STRING)
  @NotNull
  private Set<UserRole> roles;

  protected User() {
  }

  public User(UserId id, String email, String password, Set<UserRole> roles) {
    super(id);
    this.email = email;
    this.password = password;
    this.roles = roles;
  }

  public static User createOfficer(UserId userId, String email, String encodedPassword) {
    return new User(userId, email, encodedPassword, Sets.newHashSet(UserRole.OFFICER));
  }

  public static User createCaptain(UserId userId, String email, String encodedPassword) {
    return new User(userId, email, encodedPassword, Sets.newHashSet(UserRole.CAPTAIN));
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }

  public Set<UserRole> getRoles() {
    return roles;
  }
}
