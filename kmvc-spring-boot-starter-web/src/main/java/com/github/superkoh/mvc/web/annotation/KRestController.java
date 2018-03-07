package com.github.superkoh.mvc.web.annotation;

import com.github.superkoh.mvc.web.constant.KProfiles;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RestController;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@RestController
@Profile(KProfiles.RT_WEB)
public @interface KRestController {

  String value() default "";
}
