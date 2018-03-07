package com.github.superkoh.mvc.web.handler;

import com.github.superkoh.mvc.web.constant.KHttpHeaders;
import com.github.superkoh.mvc.config.profile.KProfiles;
import com.github.superkoh.mvc.web.response.BizRes;
import com.github.superkoh.mvc.web.response.ErrorRes;
import com.github.superkoh.mvc.web.response.SuccessRes;
import org.springframework.context.annotation.Profile;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;


@Profile(KProfiles.RT_WEB)
abstract public class AbstractResponseBodyHandler implements ResponseBodyAdvice<BizRes> {

  @Override
  public boolean supports(MethodParameter returnType,
      Class<? extends HttpMessageConverter<?>> converterType) {
    return true;
  }

  @Override
  public BizRes beforeBodyWrite(BizRes body, MethodParameter returnType,
      MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType,
      ServerHttpRequest request, ServerHttpResponse response) {
    String deviceToken = response.getHeaders().getFirst(KHttpHeaders.X_DEVICE_TOKEN);
    if (body instanceof ErrorRes) {
      ((ErrorRes) body).setVd(deviceToken);
      return body;
    }
    if (body instanceof SuccessRes) {
      ((SuccessRes) body).setVd(deviceToken);
      return body;
    }
    SuccessRes res = new SuccessRes();
    res.setObj(body);
    res.setVd(deviceToken);
    return res;
  }
}
