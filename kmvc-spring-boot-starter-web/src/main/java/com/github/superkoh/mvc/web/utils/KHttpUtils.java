package com.github.superkoh.mvc.web.utils;

import com.github.superkoh.mvc.web.constant.KCookies;
import com.github.superkoh.mvc.web.constant.KHttpHeaders;
import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.lang.Nullable;

public class KHttpUtils {

  public static String getRemoteIp(ServletRequest request) {
    String ip = null;
    if (request instanceof HttpServletRequest) {
      HttpServletRequest httpReq = (HttpServletRequest) request;
      ip = httpReq.getHeader("g-remote-ip");
      if (null == ip || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
        ip = httpReq.getHeader("X-Forwarded-For");
      }
      if (null == ip || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
        ip = httpReq.getHeader("Proxy-Client-IP");
      }
      if (null == ip || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
        ip = httpReq.getHeader("WL-Proxy-Client-IP");
      }
      if (null == ip || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
        ip = httpReq.getHeader("HTTP_CLIENT_IP");
      }
      if (null == ip || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
        ip = httpReq.getHeader("HTTP_X_FORWARDED_FOR");
      }
    }
    if (null == ip || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getRemoteAddr();
    }
    return ip;
  }

  @Nullable
  public static String getDeviceTokenCookie(HttpServletRequest request) {
    Cookie[] cookies = request.getCookies();
    if (null != cookies && cookies.length > 0) {
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals(KCookies.DEVICE_TOKEN_KEY)) {
          return cookie.getValue();
        }
      }
    }
    return null;
  }

  public static void setDeviceTokenCookie(HttpServletResponse response, String deviceToken) {
    response.setHeader(KHttpHeaders.X_DEVICE_TOKEN, deviceToken);
    Cookie cookie = new Cookie(KCookies.DEVICE_TOKEN_KEY, deviceToken);
    cookie.setPath("/");
    cookie.setMaxAge(36000000);
    response.addCookie(cookie);
  }
}
