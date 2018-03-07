package com.github.superkoh.mvc.exception;

public class BizRuntimeException extends RuntimeException {

  private int errorCode = -1;

  public BizRuntimeException(int errorCode, String message) {
    super(message);
    this.errorCode = errorCode;
  }

  public BizRuntimeException(String message) {
    super(message);
  }

  public int getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(int errorCode) {
    this.errorCode = errorCode;
  }
}
