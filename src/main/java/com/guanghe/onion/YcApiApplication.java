package com.guanghe.onion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class YcApiApplication extends SpringBootServletInitializer {

    //打war包时需要继承SpringBootServletInitializer，重写configure方法
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(YcApiApplication.class);
    }
	public static void main(String[] args) {
		SpringApplication.run(YcApiApplication.class, args);

	}

}

