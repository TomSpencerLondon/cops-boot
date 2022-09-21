package com.tomspencerlondon.copsboot.user.web;

import com.tomspencerlondon.copsboot.user.UserId;

public class UserNotFoundException extends RuntimeException {
  public UserNotFoundException(UserId userId) {
    super(String.format("Could not find user with id %s", userId.asString()));
  }
}
