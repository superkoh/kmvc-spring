package com.github.superkoh.mvc.web.interceptor;

import com.github.superkoh.mvc.web.security.LoginUser;
import javax.validation.constraints.NotBlank;

public interface LoginUserService {

  @Deprecated
  default LoginUser getLoginUserByToken(@NotBlank String token) {
    return null;
  }

  @Deprecated
  default String getUserType() {
    return "user";
  }

  default Long checkLoginUserToken(@NotBlank String token, @NotBlank String deviceToken) {
    return null;
  }
}
