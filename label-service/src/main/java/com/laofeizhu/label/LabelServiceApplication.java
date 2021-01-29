package com.laofeizhu.label;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.laofeizhu.label.dao")
public class LabelServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LabelServiceApplication.class, args);
	}

}
