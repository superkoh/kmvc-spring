package com.github.superkoh.mvc.web.annotation;

import com.github.superkoh.mvc.web.constant.KProfiles;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Controller
@Profile(KProfiles.RT_WEB)
public @interface KController {

  String value() default "";
}
