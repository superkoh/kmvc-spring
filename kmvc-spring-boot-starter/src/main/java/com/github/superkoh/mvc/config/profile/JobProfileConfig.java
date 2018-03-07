package com.github.superkoh.mvc.config.profile;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;

@Profile({KProfiles.RT_JOB})
@Configuration
@EnableScheduling
public class JobProfileConfig {

}
