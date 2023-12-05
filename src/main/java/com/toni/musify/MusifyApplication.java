package com.toni.musify;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Properties;

@SpringBootApplication
public class MusifyApplication {

	public static void main(String[] args) {
		SpringApplication.run(MusifyApplication.class, args);
	}

	@Bean
	public HikariDataSource hikariDataSource(@Value("${spring.datasource.url}") String jdbcUrl,
											 @Value("${spring.datasource.username}") String username,
											 @Value("${spring.datasource.password}") String password){
		HikariConfig config=new HikariConfig();
		config.setJdbcUrl(jdbcUrl);
		config.setUsername(username);
		config.setPassword(password);
		config.setDriverClassName("org.mariadb.jdbc.Driver");
		return new HikariDataSource(config);
	}

}
