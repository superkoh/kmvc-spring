package com.github.superkoh.mvc.web.response;

import com.github.superkoh.mvc.type.SimplePageList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SimplePageRes<T extends BizRes> extends ListRes<T> {

  private Integer pageSize;
  private Integer pageNo;

  public SimplePageRes(List<T> list, Integer pageSize, Integer pageNo) {
    super(list);
    this.pageSize = pageSize;
    this.pageNo = pageNo;
  }

  public <I> SimplePageRes(SimplePageList<I> pageList, Function<? super I, ? extends T> mapper) {
    this(pageList.getList().stream().map(mapper).collect(Collectors.toList()),
        pageList.getPageSize(),
        pageList.getPageNo());
  }

  public <I> SimplePageRes(SimplePageList<I> pageList, Predicate<? super I> filter,
      Function<? super I, ? extends T> mapper) {
    this(pageList.getList().stream().filter(filter).map(mapper).collect(Collectors.toList()),
        pageList.getPageSize(),
        pageList.getPageNo());
  }

  public Integer getPageSize() {
    return pageSize;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  public Integer getPageNo() {
    return pageNo;
  }

  public void setPageNo(Integer pageNo) {
    this.pageNo = pageNo;
  }
}
