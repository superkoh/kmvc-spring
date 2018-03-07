package com.github.superkoh.mvc.web.interceptor;

import com.github.superkoh.mvc.web.constant.KHttpHeaders;
import com.github.superkoh.mvc.web.constant.KReqAttrs;
import com.github.superkoh.mvc.web.utils.KHttpUtils;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class CommonInterceptor extends HandlerInterceptorAdapter {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    if (!(handler instanceof HandlerMethod)) {
      return super.preHandle(request, response, handler);
    }
    String deviceToken = request.getHeader(KHttpHeaders.X_DEVICE_TOKEN);
    if (null == deviceToken || deviceToken.isEmpty()) {
      deviceToken = KHttpUtils.getDeviceTokenCookie(request);
    }
    if (null == deviceToken || deviceToken.isEmpty()) {
      deviceToken = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
      KHttpUtils.setDeviceTokenCookie(response, deviceToken);
    }
    request.setAttribute(KReqAttrs.DEVICE_TOKEN, deviceToken);
    return super.preHandle(request, response, handler);
  }
}
