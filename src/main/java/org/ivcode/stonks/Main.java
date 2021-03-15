package org.ivcode.stonks;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class Main {
	public static void main(String... args) {
		ToStringBuilder.setDefaultStyle(ToStringStyle.JSON_STYLE);
		SpringApplication.run(Main.class, args);
	}
}
