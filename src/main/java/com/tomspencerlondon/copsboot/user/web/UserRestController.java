package com.tomspencerlondon.copsboot.user.web;

import com.tomspencerlondon.copsboot.infrastructure.security.ApplicationUserDetails;
import com.tomspencerlondon.copsboot.user.User;
import com.tomspencerlondon.copsboot.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

  private final UserService service;

  @Autowired
  public UserRestController(UserService service) {
    this.service = service;
  }

  @GetMapping("/me")
  public UserDto currentUser(@AuthenticationPrincipal ApplicationUserDetails userDetails) {
    User user = service.getUser(userDetails.getUserId())
        .orElseThrow(() -> new UserNotFoundException(userDetails.getUserId()));

    return UserDto.fromUser(user);
  }
}
