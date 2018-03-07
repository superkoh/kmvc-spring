package com.github.superkoh.mvc.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BizRuntimeException extends RuntimeException {

  private int errorCode = -1;

  public BizRuntimeException(int errorCode, String message) {
    super(message);
    this.errorCode = errorCode;
  }

  public BizRuntimeException(String message) {
    super(message);
  }
}
