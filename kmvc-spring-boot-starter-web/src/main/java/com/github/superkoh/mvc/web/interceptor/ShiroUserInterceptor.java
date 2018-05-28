package com.github.superkoh.mvc.web.interceptor;

import com.github.superkoh.mvc.web.constant.KCookies;
import com.github.superkoh.mvc.web.constant.KHttpHeaders;
import com.github.superkoh.mvc.web.constant.KReqAttrs;
import com.github.superkoh.mvc.web.security.LoginUser;
import com.github.superkoh.mvc.web.security.TokenBasedAuthToken;
import java.time.Instant;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

public class ShiroUserInterceptor extends HandlerInterceptorAdapter {

  private String userType;

  public ShiroUserInterceptor(String userType) {
    this.userType = userType;
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
    Subject subject = SecurityUtils.getSubject();
    if (null != token) {
      String deviceToken = (String) request.getAttribute(KReqAttrs.DEVICE_TOKEN);
      try {
        subject.login(new TokenBasedAuthToken(token, deviceToken, userType));
      } catch (AuthenticationException ignored) {
      }
      if (subject.isAuthenticated()) {
        request.setAttribute(KReqAttrs.LOGIN_USER_ID, subject.getPrincipal());
        String finalToken = token;
        request.setAttribute(KReqAttrs.LOGIN_USER, new LoginUser() {
          @Override
          public Long getId() {
            return (Long) subject.getPrincipal();
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
    return super.preHandle(request, response, handler);
  }
}
