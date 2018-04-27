package com.github.superkoh.mvc.web.interceptor;

import com.github.superkoh.mvc.web.annotation.GuestRequired;
import com.github.superkoh.mvc.web.annotation.LoginRequired;
import com.github.superkoh.mvc.web.constant.KCookies;
import com.github.superkoh.mvc.web.constant.KHttpHeaders;
import com.github.superkoh.mvc.web.constant.KReqAttrs;
import com.github.superkoh.mvc.web.exception.NeedGuestException;
import com.github.superkoh.mvc.web.exception.NotLoginException;
import com.github.superkoh.mvc.web.security.LoginUser;
import java.time.Instant;
import java.util.Arrays;
import java.util.Optional;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class UserInterceptor extends HandlerInterceptorAdapter {

  private LoginUserService loginUserService;

  public UserInterceptor(LoginUserService loginUserService) {
    this.loginUserService = loginUserService;
  }

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    if (!(handler instanceof HandlerMethod)) {
      return super.preHandle(request, response, handler);
    }
    String token = request.getHeader(KHttpHeaders.X_USER_TOKEN);
    if (null == token) {
      Cookie[] cookies = request.getCookies();
      if (null != cookies) {
        Optional<Cookie> oCookie = Arrays.stream(cookies)
            .parallel()
            .filter(cookie -> cookie.getName().equals(KCookies.TOKEN_KEY))
            .limit(1)
            .findFirst();
        if (oCookie.isPresent()) {
          token = oCookie.get().getValue();
        }
      }
    }
    LoginUser loginUser = null;
    if (null != token) {
      loginUser = loginUserService.getLoginUserByToken(token);
      if (null != loginUser) {
        if (loginUser.getTokenExpireTime().isBefore(Instant.now())) {
          loginUser = null;
        } else {
          String deviceToken = (String) request.getAttribute(KReqAttrs.DEVICE_TOKEN);
          if (null != loginUser.getDeviceToken() && !deviceToken
              .equals(loginUser.getDeviceToken())) {
            loginUser = null;
          }
        }
      }
      if (null != loginUser) {
        request.setAttribute(KReqAttrs.LOGIN_USER, loginUser);
      }
    }

    HandlerMethod handlerMethod = (HandlerMethod) handler;
    if (null == loginUser) {
      if (handlerMethod.getMethod().isAnnotationPresent(LoginRequired.class) || handlerMethod
          .getClass().isAnnotationPresent(LoginRequired.class)) {
        throw new NotLoginException();
      }
    } else {
      if (handlerMethod.getMethod().isAnnotationPresent(GuestRequired.class) || handlerMethod
          .getClass().isAnnotationPresent(GuestRequired.class)) {
        throw new NeedGuestException();
      }
    }
    return super.preHandle(request, response, handler);
  }
}
