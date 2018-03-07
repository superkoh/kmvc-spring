package com.github.superkoh.mvc.type;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PageList<T> extends SimplePageList<T> {

  private long totalCnt;

  public PageList(List<T> list, Page page, long totalCnt) {
    super(list, page);
    this.totalCnt = totalCnt;
  }

  public <I> PageList(PageList<I> pageList, Function<? super I, ? extends T> mapper) {
    super(pageList.getList().stream().map(mapper).collect(Collectors.toList()),
        new Page(pageList.getPageSize(), pageList.getPageNo()));
    this.totalCnt = pageList.getTotalCnt();
  }
}
