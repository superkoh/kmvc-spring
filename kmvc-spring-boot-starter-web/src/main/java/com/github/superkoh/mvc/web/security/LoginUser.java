package com.github.superkoh.mvc.web.security;

import java.time.Instant;

public interface LoginUser {

  Long getId();

  String getUsername();

  String getToken();

  String getDeviceToken();

  Instant getTokenExpireTime();
}
