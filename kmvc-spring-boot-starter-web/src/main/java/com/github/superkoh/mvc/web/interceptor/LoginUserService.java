package com.github.superkoh.mvc.web.interceptor;

import com.github.superkoh.mvc.web.security.LoginUser;
import javax.validation.constraints.NotBlank;

public interface LoginUserService {

  LoginUser getLoginUserByToken(@NotBlank String token);

  String getUserType();
}
