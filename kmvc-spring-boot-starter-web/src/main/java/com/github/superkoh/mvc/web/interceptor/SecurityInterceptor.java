package com.github.superkoh.mvc.web.interceptor;

import com.github.superkoh.mvc.web.constant.KHttpHeaders;
import com.github.superkoh.mvc.web.exception.PermissionDeniedException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class SecurityInterceptor extends HandlerInterceptorAdapter {

  private String xAuth;

  public SecurityInterceptor(String xAuth) {
    this.xAuth = xAuth;
  }

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    if (!(handler instanceof HandlerMethod)) {
      return super.preHandle(request, response, handler);
    }
    String auth = request.getHeader(KHttpHeaders.X_AUTH);
    if (null == auth || !xAuth.equals(auth)) {
      throw new PermissionDeniedException();
    }
    return super.preHandle(request, response, handler);
  }

}
