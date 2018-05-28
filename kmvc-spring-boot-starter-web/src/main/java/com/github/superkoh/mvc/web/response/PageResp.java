package com.github.superkoh.mvc.web.response;

import com.github.superkoh.mvc.type.PageList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PageResp<T extends BizResp> extends SimplePageResp<T> {

  private long totalCnt;

  public PageResp(List<T> list, Integer pageSize, Integer pageNo, long totalCnt) {
    super(list, pageSize, pageNo);
    this.totalCnt = totalCnt;
  }

  public <I> PageResp(PageList<I> pageList, Function<? super I, ? extends T> mapper) {
    this(pageList.getList().stream().map(mapper).collect(Collectors.toList()),
        pageList.getPageSize(),
        pageList.getPageNo(),
        pageList.getTotalCnt());
  }

  public <I> PageResp(PageList<I> pageList, Predicate<? super I> filter,
      Function<? super I, ? extends T> mapper) {
    this(pageList.getList().stream().filter(filter).map(mapper).collect(Collectors.toList()),
        pageList.getPageSize(),
        pageList.getPageNo(),
        pageList.getTotalCnt());
  }
}

