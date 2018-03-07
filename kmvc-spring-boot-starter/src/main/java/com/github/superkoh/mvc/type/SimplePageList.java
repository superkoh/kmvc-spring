package com.github.superkoh.mvc.type;

import com.github.superkoh.mvc.lang.BaseObject;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SimplePageList<T> extends BaseObject {

  private List<T> list;
  private int pageSize;
  private int pageNo;

  public SimplePageList(List<T> list, Page page) {
    this.list = list;
    this.pageSize = page.getPageSize();
    this.pageNo = page.getPageNo();
  }

  public <I> SimplePageList(SimplePageList<I> pageList, Function<? super I, ? extends T> mapper) {
    this.list = pageList.getList().stream().map(mapper).collect(Collectors.toList());
    this.pageSize = pageList.getPageSize();
    this.pageNo = pageList.getPageNo();
  }
}
