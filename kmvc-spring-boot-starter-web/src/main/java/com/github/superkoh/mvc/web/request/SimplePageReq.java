package com.github.superkoh.mvc.web.request;

import com.github.superkoh.mvc.lang.BaseObject;
import com.github.superkoh.mvc.type.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@ApiModel
@Data
public class SimplePageReq extends BaseObject {

  @NotNull
  @Positive
  @ApiModelProperty("单页数量")
  private Integer pageSize = 10;
  @NotNull
  @Positive
  @ApiModelProperty("页码")
  private Integer pageNo = 1;

  public Page toPage() {
    return new Page(pageSize, pageNo);
  }
}
