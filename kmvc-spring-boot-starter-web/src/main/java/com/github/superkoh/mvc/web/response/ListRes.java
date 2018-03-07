package com.github.superkoh.mvc.web.response;

import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ListRes<T extends BizRes> extends BizRes {

  private List<T> list;

  public ListRes(List<T> list) {
    this.list = list;
  }
}
