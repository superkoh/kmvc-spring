package com.github.superkoh.mvc.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BizException extends Exception {

  private int errorCode = -1;

  public BizException(int errorCode, String message) {
    super(message);
    this.errorCode = errorCode;
  }

  public BizException(String message) {
    super(message);
  }
}
