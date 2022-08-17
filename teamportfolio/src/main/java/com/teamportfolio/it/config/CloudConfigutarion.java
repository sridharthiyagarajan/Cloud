package com.teamportfolio.it.config;

import java.util.Properties;

import javax.sql.DataSource;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@PropertySource("classpath:application.properties")
public class CloudConfigutarion extends AbstractCloudConfig {

	private static final String HIBERNATE_DIALECT = "hibernate.dialect";

	private static final String HIBERNATE_TO_DDL = "hibernate.hbm2ddl.auto";

	@Value("${db.service.id}")
	private String serviceId;

	@Value("${hibernate.scan.packages}")
	private String packagesToBeScanned;

	@Value("${hibernate.dialect}")
	private String hibernateDialect;

	@Value("${hibernate.hbm2ddl.auto}")
	private String hibernateToDDL;

	@Bean
	public DataSource dataSource() {

		return connectionFactory().dataSource(serviceId);
	}

	@Bean
	public LocalSessionFactoryBean sessionFactory() {

		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setPackagesToScan(packagesToBeScanned);

		Properties hibernateProperties = new Properties();
		hibernateProperties.put(HIBERNATE_DIALECT, hibernateDialect);
		hibernateProperties.put(HIBERNATE_TO_DDL, hibernateToDDL);

		sessionFactory.setHibernateProperties(hibernateProperties);

		return sessionFactory;
	}

	@Bean
	public HibernateTransactionManager transactionManager() {

		HibernateTransactionManager hibernateTransactionManager = new HibernateTransactionManager();
		hibernateTransactionManager.setSessionFactory(sessionFactory().getObject());

		return hibernateTransactionManager;
	}
	
	@Bean
	public Validator validator() {
		return new LocalValidatorFactoryBean();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
    public FilterRegistrationBean corsFilter() {
		
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(0);
        
        return bean;
    }

}