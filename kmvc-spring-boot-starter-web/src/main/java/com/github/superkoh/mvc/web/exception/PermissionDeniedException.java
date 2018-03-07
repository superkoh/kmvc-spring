package com.github.superkoh.mvc.web.exception;

import com.github.superkoh.mvc.exception.BizRuntimeException;

public class PermissionDeniedException extends BizRuntimeException {

  public PermissionDeniedException() {
    super(4030, "没有权限");
  }
}
