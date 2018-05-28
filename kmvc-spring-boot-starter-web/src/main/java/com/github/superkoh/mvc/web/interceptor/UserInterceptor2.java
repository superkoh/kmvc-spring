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
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

public class UserInterceptor2 extends HandlerInterceptorAdapter {

  private LoginUserService loginUserService;

  public UserInterceptor2(LoginUserService loginUserService) {
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
      Cookie tokenCookie = WebUtils.getCookie(request, KCookies.TOKEN_KEY);
      if (null != tokenCookie) {
        token = tokenCookie.getValue();
      }
    }
    Long loginUserId = null;
    if (null != token) {
      String deviceToken = (String) request.getAttribute(KReqAttrs.DEVICE_TOKEN);
      loginUserId = loginUserService.checkLoginUserToken(token, deviceToken);
      if (null != loginUserId) {
        request.setAttribute(KReqAttrs.LOGIN_USER_TOKEN, token);
        request.setAttribute(KReqAttrs.LOGIN_USER_ID, loginUserId);
        String finalToken = token;
        Long finalLoginUserId = loginUserId;
        request.setAttribute(KReqAttrs.LOGIN_USER, new LoginUser() {
          @Override
          public Long getId() {
            return finalLoginUserId;
          }

          @Override
          public String getUsername() {
            throw new UnsupportedOperationException();
          }

          @Override
          public String getToken() {
            return finalToken;
          }

          @Override
          public String getDeviceToken() {
            return deviceToken;
          }

          @Override
          public Instant getTokenExpireTime() {
            throw new UnsupportedOperationException();
          }
        });
      }
    }

    HandlerMethod handlerMethod = (HandlerMethod) handler;
    if (null == loginUserId) {
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
