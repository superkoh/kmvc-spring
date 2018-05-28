package com.github.superkoh.mvc.web.response;

import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ListResp<T extends BizResp> extends BizResp {

  private List<T> list;

  public ListResp(List<T> list) {
    this.list = list;
  }
}
