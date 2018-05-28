package com.github.superkoh.mvc.web.response;

public class SuccessResp extends BizRespWrapper {

  public final static SuccessResp SUCCESS = new SuccessResp();

  public SuccessResp() {
    this(null);
  }

  public SuccessResp(BizResp obj) {
    this(obj, null);
  }

  public SuccessResp(BizResp obj, String msg) {
    super(0, obj, msg);
  }
}
