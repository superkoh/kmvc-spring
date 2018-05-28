package com.github.superkoh.mvc.web.handler;

import com.github.superkoh.mvc.config.profile.KProfiles;
import com.github.superkoh.mvc.exception.BizException;
import com.github.superkoh.mvc.exception.BizRuntimeException;
import com.github.superkoh.mvc.web.response.ErrResp;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Profile(KProfiles.RT_WEB)
abstract public class AbstractApiExceptionHandler {

  private static final Logger logger = LoggerFactory.getLogger(AbstractApiExceptionHandler.class);

  private final HttpServletResponse response;

  @Autowired
  public AbstractApiExceptionHandler(
      HttpServletResponse response) {
    this.response = response;
  }

  @ExceptionHandler(BizException.class)
  @ResponseBody
  public ErrResp bizExceptionHandler(BizException e) {
    response.setStatus(400);
    return new ErrResp(e);
  }

  @ExceptionHandler(BizRuntimeException.class)
  @ResponseBody
  public ErrResp bizRuntimeExceptionHandler(BizRuntimeException e) {
    response.setStatus(400);
    return new ErrResp(e);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseBody
  public ErrResp constraintViolationExceptionHandler(ConstraintViolationException e) {
    response.setStatus(400);
    String msg = null;
    Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
    if (!violations.isEmpty()) {
      ConstraintViolation violation = violations.iterator().next();
      msg = ((PathImpl) violation.getPropertyPath()).getLeafNode().getName() + " " + violation
          .getMessage();
    }
    return new ErrResp(msg);
  }

  @ExceptionHandler(BindException.class)
  @ResponseBody
  public ErrResp bindExceptionHandler(BindException e) {
    response.setStatus(400);
    if (e.hasFieldErrors()) {
      return new ErrResp(
          e.getFieldError().getField() + " " + e.getFieldError().getDefaultMessage());
    }
    if (e.hasGlobalErrors()) {
      return new ErrResp(e.getGlobalError().getDefaultMessage());
    }
    return new ErrResp(e.getAllErrors().get(0).getDefaultMessage());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseBody
  public ErrResp exceptionHandler(MethodArgumentNotValidException e) {
    response.setStatus(400);
    return new ErrResp(e.getBindingResult().getFieldError().getField() + " is illegal!");
  }

  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseBody
  public ErrResp exceptionHandler(IllegalArgumentException e) {
    response.setStatus(400);
    return new ErrResp(e);
  }

  @ExceptionHandler(Exception.class)
  @ResponseBody
  public ErrResp exceptionHandler(Exception e) {
    response.setStatus(500);
    logger.error(e.getMessage(), e);
    return new ErrResp(e);
  }
}
