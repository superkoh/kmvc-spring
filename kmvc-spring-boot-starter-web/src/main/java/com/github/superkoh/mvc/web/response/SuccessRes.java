package com.github.superkoh.mvc.web.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_ABSENT)
public class SuccessRes extends BizRes {

  private int ok = 0;
  private BizRes obj;
  private String vd;

  public int getOk() {
    return ok;
  }

  public void setOk(int ok) {
    this.ok = ok;
  }

  public BizRes getObj() {
    return obj;
  }

  public void setObj(BizRes obj) {
    this.obj = obj;
  }

  public String getVd() {
    return vd;
  }

  public void setVd(String vd) {
    this.vd = vd;
  }
}
