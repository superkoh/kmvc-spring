package com.github.superkoh.mvc.web.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@JsonInclude(Include.NON_ABSENT)
@Data
public class SuccessRes extends BizRes {

  private int ok = 0;
  private BizRes obj;
  private String vd;
}
