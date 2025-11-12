package com.example.world.config;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManagerFactory;


/**
 * 
 * @author Binnur Kurt <binnur.kurt@gmail.com>
 *
 */
@Configuration
@ComponentScan(basePackages = { "com.example.world.repository", "com.example.world.service" })
@EnableTransactionManagement
public class AppConfig {
	@Value("${jdbc.url}")
	private String url;
	@Value("${user}")
	private String username;
	@Value("${password}")
	private String password;
	@Value("${initialSize}")
	private int initialSize;
	@Value("${maxTotal}")
	private int maxTotal;

	@Bean
	DataSource dataSource() {
		BasicDataSource ds = new BasicDataSource();
		ds.setUrl(url);
		ds.setUsername(username);
		ds.setPassword(password);
		ds.setInitialSize(initialSize);
		// ds.setMinIdle(30_000);
		ds.setMaxTotal(maxTotal);
		return ds;
	}

	@Bean
	JpaTransactionManager jpaTransactionManager(EntityManagerFactory emf) {
		return new JpaTransactionManager(emf);
	}

	@Bean
	LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        var factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setPackagesToScan("com.example.world.entity");
        var vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setShowSql(true);
        factoryBean.setJpaVendorAdapter(vendorAdapter);
        return factoryBean;
	}
}
