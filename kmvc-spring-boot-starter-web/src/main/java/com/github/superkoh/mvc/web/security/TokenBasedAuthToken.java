package com.github.superkoh.mvc.web.security;

import org.apache.shiro.authc.AuthenticationToken;

public class TokenBasedAuthToken implements AuthenticationToken {

  private String token;
  private String deviceToken;
  private String userType;

  public TokenBasedAuthToken(String token, String deviceToken, String userType) {
    this.token = token;
    this.deviceToken = deviceToken;
    this.userType = userType;
  }

  @Override
  public Object getPrincipal() {
    return null;
  }

  @Override
  public Object getCredentials() {
    return token;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getDeviceToken() {
    return deviceToken;
  }

  public void setDeviceToken(String deviceToken) {
    this.deviceToken = deviceToken;
  }

  public String getUserType() {
    return userType;
  }

  public void setUserType(String userType) {
    this.userType = userType;
  }
}
