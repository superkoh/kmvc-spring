package com.github.superkoh.mvc.web.response;

public class ErrResp extends BizRespWrapper {

  public ErrResp(int ok, String msg) {
    super(ok, null, msg);
  }

  public ErrResp(String msg) {
    super(-1, null, msg);
  }

  public ErrResp(Exception e) {
    this(-1, e.getMessage());
  }
}
