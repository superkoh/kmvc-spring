package com.github.superkoh.mvc.web.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.github.superkoh.mvc.lang.BaseObject;

@JsonInclude(Include.NON_ABSENT)
public class BizRes extends BaseObject {

}
