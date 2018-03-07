package com.github.superkoh.mvc.web.request;

import com.github.superkoh.mvc.utils.ACU;
import com.google.common.base.CaseFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class PageReq extends SimplePageReq {

  private static Boolean mapUnderscoreToCamelCase;

  @ApiModelProperty("排序字段")
  private String orderBy;

  public String getOrderBy() {
    if (null == orderBy) {
      return null;
    }
    if (mapUnderscoreToCamelCase()) {
      return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, orderBy);
    } else {
      return orderBy;
    }
  }

  public void setOrderBy(String orderBy) {
    this.orderBy = orderBy;
  }

  private static boolean mapUnderscoreToCamelCase() {
    if (null == mapUnderscoreToCamelCase) {
      mapUnderscoreToCamelCase = ACU
          .property("mybatis.configuration.map-underscore-to-camel-case", Boolean.class, false);
    }
    return mapUnderscoreToCamelCase;
  }

}
