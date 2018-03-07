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

  private int offset;
  private int limit;

  public Page(int pageSize, int pageNo) {
    this.pageSize = pageSize;
    this.pageNo = pageNo;
    this.limit = this.pageSize;
    this.offset = (this.pageNo - 1) * this.pageSize;
  }

  public Page(int pageSize, int pageNo, String orderBy) {
    this(pageSize, pageNo);
    this.orderBy = orderBy;
  }
}
