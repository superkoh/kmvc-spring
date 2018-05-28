package com.github.superkoh.mvc.web;

import com.github.superkoh.mvc.web.constant.KReqAttrs;
import com.github.superkoh.mvc.web.security.LoginUser;
import com.github.superkoh.mvc.web.utils.KHttpUtils;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;

abstract public class BaseController {

  @Autowired(required = false)
  private HttpServletRequest request;

  protected boolean isLogin() {
    return null != request.getAttribute(KReqAttrs.LOGIN_USER_ID);
  }

  @Deprecated
  protected LoginUser getLoginUser() {
    return (LoginUser) request.getAttribute(KReqAttrs.LOGIN_USER);
  }

  protected String getDeviceToken() {
    return (String) request.getAttribute(KReqAttrs.DEVICE_TOKEN);
  }

  protected Long getCurrentUserId() {
    return (Long) request.getAttribute(KReqAttrs.LOGIN_USER_ID);
  }

  protected String getCurrentUserToken() {
    return (String) request.getAttribute(KReqAttrs.LOGIN_USER_TOKEN);
  }

  protected String getRemoteIp() {
    return KHttpUtils.getRemoteIp(request);
  }
}
