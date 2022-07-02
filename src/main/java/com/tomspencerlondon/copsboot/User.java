package com.tomspencerlondon.copsboot;

import com.sun.istack.NotNull;
import com.tomspencerlondon.orm.jpa.AbstractEntity;
import java.util.Set;
import java.util.UUID;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
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
