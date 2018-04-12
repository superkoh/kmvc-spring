package com.github.superkoh.mvc.web.filter;

import com.github.superkoh.mvc.web.constant.KReqAttrs;
import com.github.superkoh.mvc.web.security.LoginUser;
import com.github.superkoh.mvc.web.utils.KHttpUtils;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

public class LogFilter implements Filter {

  private static final Logger accessLogger = LoggerFactory.getLogger("accessLogger");

  private List<LogFilterInterceptor> logFilterInterceptorList;

  public LogFilter(List<LogFilterInterceptor> logFilterInterceptorList) {
    if (null == logFilterInterceptorList) {
      this.logFilterInterceptorList = new ArrayList<>();
    } else {
      this.logFilterInterceptorList = logFilterInterceptorList;
    }
  }

  private static void addLogKV(StringBuilder builder, String key, String value) {
    builder.append("[").append(key).append(":").append(value).append("]");
  }

  private static String parseParams(Map<String, String[]> paramMap) {
    StringBuilder builder = new StringBuilder();
    for (String key : paramMap.keySet()) {
      addLogKV(builder, key, Arrays.toString(paramMap.get(key)));
    }
    return builder.toString();
  }

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {

  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException,
      ServletException {
    if (!(request instanceof HttpServletRequest)) {
      return;
    }
    ContentCachingRequestWrapper wrapperRequest = new ContentCachingRequestWrapper(
        (HttpServletRequest) request);
    ContentCachingResponseWrapper wrapperResponse = new ContentCachingResponseWrapper(
        (HttpServletResponse) response);
    String ua = wrapperRequest.getHeader("User-Agent");
    ua = null == ua ? "" : ua;
    if (ua.equals("KeepAliveClient") || HttpMethod.HEAD.matches(wrapperRequest.getMethod())) {
      chain.doFilter(wrapperRequest, wrapperResponse);
      wrapperResponse.copyBodyToResponse();
    } else {
      logFilterInterceptorList.forEach(LogFilterInterceptor::prepare);
      StringBuilder accessLogBuilder = new StringBuilder();
      long startTime = Instant.now().toEpochMilli();
      chain.doFilter(wrapperRequest, wrapperResponse);
      addLogKV(accessLogBuilder, "request", wrapperRequest.getRequestURI());
      addLogKV(accessLogBuilder, "method", wrapperRequest.getMethod());
      addLogKV(accessLogBuilder, "status", String.valueOf(wrapperResponse.getStatusCode()));
      addLogKV(accessLogBuilder, "remoteIp", KHttpUtils.getRemoteIp(request));
      addLogKV(accessLogBuilder, "locale",
          RequestContextUtils.getLocale(wrapperRequest).toLanguageTag());
      addLogKV(accessLogBuilder, "ua", wrapperRequest.getHeader("User-Agent"));
      String deviceToken = (String) request.getAttribute(KReqAttrs.DEVICE_TOKEN);
      if (null != deviceToken) {
        addLogKV(accessLogBuilder, "vd", deviceToken);
      }
      LoginUser loginUser = (LoginUser) request.getAttribute(KReqAttrs.LOGIN_USER);
      if (null != loginUser) {
        addLogKV(accessLogBuilder, "user", String.valueOf(loginUser.getId()));
        addLogKV(accessLogBuilder, "token", loginUser.getToken());
        addLogKV(accessLogBuilder, "tokenExpireTime", loginUser.getTokenExpireTime().toString());

      }
      addLogKV(accessLogBuilder, "params", parseParams(wrapperRequest.getParameterMap()));
      boolean isPost = "POST".equalsIgnoreCase(wrapperRequest.getMethod());
      if (isPost) {
        byte[] content = wrapperRequest.getContentAsByteArray();
        if (content.length < 1) {
          content = StreamUtils.copyToByteArray(wrapperRequest.getInputStream());
        }
        String body = IOUtils.toString(content, "UTF-8")
            .replaceAll("\\r\\n", "")
            .replaceAll("\\n", "")
            .replaceAll("\"password\":\"[^\"]*\"",
                "\"password\":\"******\"");
        addLogKV(accessLogBuilder, "body", body);
      }
      if (isPost || accessLogger.isDebugEnabled()) {
        if (null != wrapperResponse.getContentType() && (wrapperResponse.getContentType().equals(
            MediaType.APPLICATION_JSON_UTF8_VALUE) || wrapperResponse.getContentType().equals(
            MediaType.APPLICATION_JSON_VALUE))) {
          addLogKV(accessLogBuilder, "response",
              IOUtils.toString(wrapperResponse.getContentAsByteArray(), "UTF-8"));
        }
      }
      logFilterInterceptorList
          .forEach(logFilterInterceptor -> addLogKV(accessLogBuilder, logFilterInterceptor
              .getKey(), logFilterInterceptor.getOutput()));
      long endTime = Instant.now().toEpochMilli();
      addLogKV(accessLogBuilder, "duration", String.valueOf(endTime - startTime) + "ms");
      accessLogger.info(accessLogBuilder.toString());
      wrapperResponse.copyBodyToResponse();
    }
  }

  @Override
  public void destroy() {

  }
}
