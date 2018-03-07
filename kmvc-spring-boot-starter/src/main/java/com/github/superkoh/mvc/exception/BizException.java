package com.github.superkoh.mvc.exception;

public class BizException extends Exception {

  private int errorCode = -1;

  public BizException(int errorCode, String message) {
    super(message);
    this.errorCode = errorCode;
  }

  public BizException(String message) {
    super(message);
  }

  public int getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(int errorCode) {
    this.errorCode = errorCode;
  }
}
