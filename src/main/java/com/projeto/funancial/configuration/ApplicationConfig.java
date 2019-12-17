package com.projeto.funancial.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Configuration
public class ApplicationConfig {
	@Getter
	@Value("${jwt.secret}")
	private String jwtSecret;
}
