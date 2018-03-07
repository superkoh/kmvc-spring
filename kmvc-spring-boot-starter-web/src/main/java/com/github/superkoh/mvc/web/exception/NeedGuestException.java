package com.github.superkoh.mvc.web.exception;

import com.github.superkoh.mvc.exception.BizException;

public class NeedGuestException extends BizException {

  public NeedGuestException() {
    super(4032, "need guest");
  }
}
