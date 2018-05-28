package com.github.superkoh.mvc.web;

import com.github.superkoh.mvc.web.constant.KReqAttrs;
import com.github.superkoh.mvc.web.utils.KHttpUtils;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;

abstract public class BaseController {

  @Autowired(required = false)
  private HttpServletRequest request;

  protected String getDeviceToken() {
    return (String) request.getAttribute(KReqAttrs.DEVICE_TOKEN);
  }

  protected String getRemoteIp() {
    return KHttpUtils.getRemoteIp(request);
  }
}
