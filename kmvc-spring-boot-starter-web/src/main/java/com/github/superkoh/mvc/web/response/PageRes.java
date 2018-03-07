package com.github.superkoh.mvc.web.response;

import com.github.superkoh.mvc.type.PageList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PageRes<T extends BizRes> extends SimplePageRes<T> {

  private Long totalCnt;

  public PageRes(List<T> list, Integer pageSize, Integer pageNo, Long totalCnt) {
    super(list, pageSize, pageNo);
    this.totalCnt = totalCnt;
  }

  public <I> PageRes(PageList<I> pageList, Function<? super I, ? extends T> mapper) {
    this(pageList.getList().stream().map(mapper).collect(Collectors.toList()),
        pageList.getPageSize(),
        pageList.getPageNo(),
        pageList.getTotalCnt());
  }

  public <I> PageRes(PageList<I> pageList, Predicate<? super I> filter,
      Function<? super I, ? extends T> mapper) {
    this(pageList.getList().stream().filter(filter).map(mapper).collect(Collectors.toList()),
        pageList.getPageSize(),
        pageList.getPageNo(),
        pageList.getTotalCnt());
  }

  public Long getTotalCnt() {
    return totalCnt;
  }

  public void setTotalCnt(Long totalCnt) {
    this.totalCnt = totalCnt;
  }
}

