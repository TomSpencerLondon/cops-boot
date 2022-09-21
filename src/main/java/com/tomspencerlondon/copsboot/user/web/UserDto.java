package com.tomspencerlondon.copsboot.user.web;


import com.tomspencerlondon.copsboot.user.User;
import com.tomspencerlondon.copsboot.user.UserId;
import com.tomspencerlondon.copsboot.user.UserRole;
import java.util.Set;
import lombok.Value;

@Value
public class UserDto {
  private final UserId id;
  private final String email;
  private final Set<UserRole> roles;

  public static UserDto fromUser(User user) {
    return new UserDto(user.getId(),
                       user.getEmail(),
                       user.getRoles());
  }
}
