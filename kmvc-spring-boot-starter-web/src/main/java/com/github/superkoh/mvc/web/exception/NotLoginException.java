package com.github.superkoh.mvc.web.exception;

import com.github.superkoh.mvc.exception.BizException;

public class NotLoginException extends BizException {

  public NotLoginException() {
    super(4031, "not login");
  }
}
