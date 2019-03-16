package com.tcxx.serve.web.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource(locations = { "classpath:quartz/quartz-spring.xml" })
public class QuartzConfiguration {

}
