package com.github.superkoh.mvc.type;

import com.github.superkoh.mvc.lang.BaseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Page extends BaseObject {

  private int pageSize;
  private int pageNo;
  private String orderBy;

  public Page() {
  }

  public Page(int pageSize, int pageNo) {
    this.pageSize = pageSize;
    this.pageNo = pageNo;
  }

  public Page(int pageSize, int pageNo, String orderBy) {
    this(pageSize, pageNo);
    this.orderBy = orderBy;
  }

  public int getOffset() {
    return (this.pageNo - 1) * this.pageSize;
  }

  public int getLimit() {
    return this.pageSize;
  }
}
