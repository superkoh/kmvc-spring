package com.github.superkoh.mvc.web.filter;

/**
 * Created by KOH on 2017/2/9.
 * <p>
 * webFramework
 */
public interface LogFilterInterceptor {

    void prepare();

    String getOutput();

    String getKey();
}
