package com.github.superkoh.mvc.type;

public class Page {

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

  public int getPageSize() {
    return pageSize;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }

  public int getPageNo() {
    return pageNo;
  }

  public void setPageNo(int pageNo) {
    this.pageNo = pageNo;
  }

  public String getOrderBy() {
    return orderBy;
  }

  public void setOrderBy(String orderBy) {
    this.orderBy = orderBy;
  }

  public int getOffset() {
    return offset;
  }

  public void setOffset(int offset) {
    this.offset = offset;
  }

  public int getLimit() {
    return limit;
  }

  public void setLimit(int limit) {
    this.limit = limit;
  }
}
