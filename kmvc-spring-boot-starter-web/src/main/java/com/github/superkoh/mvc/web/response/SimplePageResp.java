package com.github.superkoh.mvc.web.response;

import com.github.superkoh.mvc.type.SimplePageList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SimplePageResp<T extends BizResp> extends ListResp<T> {

  private int pageSize;
  private int pageNo;

  public SimplePageResp(List<T> list, int pageSize, int pageNo) {
    super(list);
    this.pageSize = pageSize;
    this.pageNo = pageNo;
  }

  public <I> SimplePageResp(SimplePageList<I> pageList, Function<? super I, ? extends T> mapper) {
    this(pageList.getList().stream().map(mapper).collect(Collectors.toList()),
        pageList.getPageSize(),
        pageList.getPageNo());
  }

  public <I> SimplePageResp(SimplePageList<I> pageList, Predicate<? super I> filter,
      Function<? super I, ? extends T> mapper) {
    this(pageList.getList().stream().filter(filter).map(mapper).collect(Collectors.toList()),
        pageList.getPageSize(),
        pageList.getPageNo());
  }
}
