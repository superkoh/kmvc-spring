package com.github.superkoh.mvc.web.handler;

import com.github.superkoh.mvc.exception.BizException;
import com.github.superkoh.mvc.exception.BizRuntimeException;
import com.github.superkoh.mvc.web.constant.KProfiles;
import com.github.superkoh.mvc.web.exception.NeedGuestException;
import com.github.superkoh.mvc.web.exception.NotLoginException;
import com.github.superkoh.mvc.web.response.ErrorRes;
import com.github.superkoh.mvc.web.utils.KHttpUtils;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
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

  private final HttpServletRequest request;
  private final HttpServletResponse response;

  @Autowired
  public AbstractApiExceptionHandler(
      HttpServletRequest request,
      HttpServletResponse response) {
    this.request = request;
    this.response = response;
  }

  @ExceptionHandler({NotLoginException.class, NeedGuestException.class})
  @ResponseBody
  public ErrorRes notLoginExceptionHandler(BizException e) {
    response.setStatus(403);
    if (e instanceof NotLoginException) {
      KHttpUtils.clearLoginTokenCookie(response);
    }
    return new ErrorRes(e);
  }

  @ExceptionHandler(BizException.class)
  @ResponseBody
  public ErrorRes bizExceptionHandler(BizException e) {
    response.setStatus(400);
    return new ErrorRes(e);
  }

  @ExceptionHandler(BizRuntimeException.class)
  @ResponseBody
  public ErrorRes bizRuntimeExceptionHandler(BizRuntimeException e) {
    response.setStatus(400);
    return new ErrorRes(e);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseBody
  public ErrorRes constraintViolationExceptionHandler(ConstraintViolationException e) {
    response.setStatus(400);
    ErrorRes res = new ErrorRes();
    Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
    if (!violations.isEmpty()) {
      ConstraintViolation violation = violations.iterator().next();
      res.setMsg(((PathImpl) violation.getPropertyPath()).getLeafNode().getName() + " " + violation
          .getMessage());
    }
    return res;
  }

  @ExceptionHandler(BindException.class)
  @ResponseBody
  public ErrorRes bindExceptionHandler(BindException e) {
    response.setStatus(400);
    ErrorRes res = new ErrorRes();
    if (e.hasFieldErrors()) {
      res.setMsg(e.getFieldError().getField() + " " + e.getFieldError().getDefaultMessage());
      return res;
    }
    if (e.hasGlobalErrors()) {
      res.setMsg(e.getGlobalError().getDefaultMessage());
      return res;
    }
    res.setMsg(e.getAllErrors().get(0).getDefaultMessage());
    return res;
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseBody
  public ErrorRes exceptionHandler(MethodArgumentNotValidException e) {
    response.setStatus(400);
    return new ErrorRes(e.getBindingResult().getFieldError().getField() + " is illegal!");
  }

  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseBody
  public ErrorRes exceptionHandler(IllegalArgumentException e) {
    response.setStatus(400);
    return new ErrorRes(e);
  }

  @ExceptionHandler(Exception.class)
  @ResponseBody
  public ErrorRes exceptionHandler(Exception e) {
    response.setStatus(400);
    logger.error(e.getMessage(), e);
    return new ErrorRes(e);
  }
}
