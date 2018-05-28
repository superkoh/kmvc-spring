package com.github.superkoh.mvc.web.handler;

import com.github.superkoh.mvc.config.profile.KProfiles;
import com.github.superkoh.mvc.web.response.BizResp;
import com.github.superkoh.mvc.web.response.BizRespWrapper;
import com.github.superkoh.mvc.web.response.SuccessResp;
import org.springframework.context.annotation.Profile;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;


@Profile(KProfiles.RT_WEB)
abstract public class AbstractResponseBodyHandler implements ResponseBodyAdvice<BizResp> {

  @Override
  public boolean supports(MethodParameter returnType,
      Class<? extends HttpMessageConverter<?>> converterType) {
    return BizResp.class.isAssignableFrom(returnType.getParameterType());
  }

  @Override
  public BizResp beforeBodyWrite(BizResp body, MethodParameter returnType,
      MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType,
      ServerHttpRequest request, ServerHttpResponse response) {
    if (body instanceof BizRespWrapper) {
      return body;
    }
    return new SuccessResp(body);
  }
}
