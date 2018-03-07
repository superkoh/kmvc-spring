package com.github.superkoh.mvc.web.request;

import com.github.superkoh.mvc.utils.ACU;
import com.google.common.base.CaseFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper = true)
@ApiModel
@Data
public class PageReq extends SimplePageReq {

  private static final Boolean mapUnderscoreToCamelCase = ACU
      .property("mybatis.configuration.map-underscore-to-camel-case",
          Boolean.class, false);

  @ApiModelProperty("排序字段")
  private String orderBy;

  public String getOrderBy() {
    if (null == orderBy) {
      return null;
    }
    if (mapUnderscoreToCamelCase) {
      return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, orderBy);
    } else {
      return orderBy;
    }
  }

}
