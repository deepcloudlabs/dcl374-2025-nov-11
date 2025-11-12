package com.example.world.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.simple.JdbcClient;

/**
 * 
 * @author Binnur Kurt <binnur.kurt@gmail.com>
 *
 */
@Configuration
public class AppConfig {

	@Bean
	JdbcClient jdbcClient(DataSource dataSource) {
	    return JdbcClient.create(dataSource);
	}
}
