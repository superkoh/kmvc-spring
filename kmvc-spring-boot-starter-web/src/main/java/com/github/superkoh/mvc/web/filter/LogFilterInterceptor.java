package com.github.superkoh.mvc.web.filter;

public interface LogFilterInterceptor {

  void prepare();

  String getOutput();

  String getKey();
}
