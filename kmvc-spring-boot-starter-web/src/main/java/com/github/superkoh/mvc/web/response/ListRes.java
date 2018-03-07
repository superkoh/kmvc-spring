package com.github.superkoh.mvc.web.response;

import java.util.List;

public class ListRes<T extends BizRes> extends BizRes {

  private List<T> list;

  public ListRes(List<T> list) {
    this.list = list;
  }

  public List<T> getList() {
    return list;
  }

  public void setList(List<T> list) {
    this.list = list;
  }
}
