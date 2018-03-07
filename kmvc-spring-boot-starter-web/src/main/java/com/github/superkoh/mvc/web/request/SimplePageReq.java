package com.github.superkoh.mvc.web.request;

import com.github.superkoh.mvc.type.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;

@ApiModel
public class SimplePageReq {

  @NotNull
  @ApiModelProperty("单页数量")
  private Integer pageSize = 10;
  @NotNull
  @ApiModelProperty("页码")
  private Integer pageNo = 1;

  public Page toPage() {
    return new Page(pageSize, pageNo);
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
