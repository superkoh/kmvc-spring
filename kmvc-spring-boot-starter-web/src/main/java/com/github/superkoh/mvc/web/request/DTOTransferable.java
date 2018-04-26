package com.github.superkoh.mvc.web.request;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import org.springframework.beans.BeanUtils;

public interface DTOTransferable<T> {

  default T toDTO() {
    Type type = getClass().getGenericSuperclass();
    ParameterizedType parameterizedType = (ParameterizedType) type;
    Class<T> clazz = (Class<T>) parameterizedType.getActualTypeArguments()[0];
    try {
      T bean = clazz.newInstance();
      BeanUtils.copyProperties(this, bean);
      return bean;
    } catch (InstantiationException | IllegalAccessException ignored) {
    }
    return null;
  }
}
