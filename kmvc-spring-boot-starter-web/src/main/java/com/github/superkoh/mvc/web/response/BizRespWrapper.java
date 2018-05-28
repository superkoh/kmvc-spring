package com.github.superkoh.mvc.web.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@JsonInclude(Include.NON_ABSENT)
@Data
abstract public class BizRespWrapper extends BizResp {

  private int ok;
  private BizResp obj;
  private String msg;

  public BizRespWrapper(int ok, BizResp obj, String msg) {
    this.ok = ok;
    this.obj = obj;
    this.msg = msg;
  }
}
