package com.github.superkoh.mvc.web.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.github.superkoh.mvc.exception.BizException;
import com.github.superkoh.mvc.exception.BizRuntimeException;

@JsonInclude(Include.NON_ABSENT)
public class ErrorRes extends BizRes {

  private int ok = -1;
  private String msg = "系统异常，请稍后重试";
  private String vd;

  public ErrorRes() {
  }

  public ErrorRes(String msg) {
    this.msg = msg;
  }

  public ErrorRes(int ok, String msg) {
    this.ok = ok;
    this.msg = msg;
  }

  public ErrorRes(Exception e) {
    this.msg = e.getLocalizedMessage();
    if (null == this.msg || this.msg.isEmpty()) {
      this.msg = "系统异常，请稍后重试";
    }
  }

  public ErrorRes(BizException e) {
    this.ok = e.getErrorCode();
    this.msg = e.getMessage();
    if (null == this.msg || this.msg.isEmpty()) {
      this.msg = "系统异常，请稍后重试";
    }
  }

  public ErrorRes(BizRuntimeException e) {
    this.ok = e.getErrorCode();
    this.msg = e.getMessage();
    if (null == this.msg || this.msg.isEmpty()) {
      this.msg = "系统异常，请稍后重试";
    }
  }

  public int getOk() {
    return ok;
  }

  public void setOk(int ok) {
    this.ok = ok;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public String getVd() {
    return vd;
  }

  public void setVd(String vd) {
    this.vd = vd;
  }
}
