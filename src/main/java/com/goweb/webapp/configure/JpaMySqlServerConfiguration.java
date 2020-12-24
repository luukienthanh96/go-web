package com.goweb.webapp.configure;

import java.util.Properties;

import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import com.zaxxer.hikari.HikariDataSource;


@Configuration
@EnableJpaRepositories(
		basePackages = {"com.goweb.webapp.repository.dao" }, 
		entityManagerFactoryRef = "sqlServerEntityManagerFactory", 
		transactionManagerRef = "sqlServerTransactionManager")
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
public class JpaMySqlServerConfiguration {

	@Autowired
	private Environment environment;

	@Value("${spring.datasource.hikari.maximum-pool-size}")
	private int maxPoolSize;
	
	@Value("${spring.datasource.hikari.minimum-idle}")
	private int minIdleSize;

	@Bean(name = "sqlServerDataSourceProperties")
	@Primary
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSourceProperties sqlServerDataSourceProperties(){
		return new DataSourceProperties();
	}

	/*
	 * Configure HikariCP pooled DataSource.
	 */
	@Bean(name = "sqlServerDataSource")
	public DataSource sqlServerDataSource() {
		DataSourceProperties dataSourceProperties = sqlServerDataSourceProperties();
			HikariDataSource dataSource = (HikariDataSource) DataSourceBuilder
					.create(dataSourceProperties.getClassLoader())
					.driverClassName(dataSourceProperties.getDriverClassName())
					.url(dataSourceProperties.getUrl())
					.username(dataSourceProperties.getUsername())
					.password(dataSourceProperties.getPassword())
					.type(HikariDataSource.class)
					.build();
			dataSource.setMaximumPoolSize(maxPoolSize);
			dataSource.setMinimumIdle(minIdleSize);
			dataSource.setConnectionTestQuery("Select 1");
			return dataSource;
	}

	/*
	 * Entity Manager Factory setup.
	 */
	@Bean(name = "sqlServerEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean sqlServerEntityManagerFactory() throws NamingException {
		HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		factoryBean.setDataSource(sqlServerDataSource());
		factoryBean.setPackagesToScan(new String[] { "com.goweb.webapp.repository.model" });
		factoryBean.setJpaVendorAdapter(hibernateJpaVendorAdapter);
		factoryBean.setJpaProperties(jpaProperties());
		factoryBean.setPersistenceUnitName("entityManagerSqlServer");
		return factoryBean;
	}

	/*
	 * Here you can specify any provider specific properties.
	 */
	private Properties jpaProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.dialect", environment.getRequiredProperty("spring.datasource.hibernate.dialect"));
		properties.put("hibernate.hbm2ddl.auto", environment.getRequiredProperty("spring.datasource.hibernate.hbm2ddl.method"));
		properties.put("hibernate.show_sql", environment.getRequiredProperty("spring.datasource.hibernate.show_sql"));
		properties.put("hibernate.format_sql", environment.getRequiredProperty("spring.datasource.hibernate.format_sql"));
		if(StringUtils.isNotEmpty(environment.getRequiredProperty("spring.jpa.properties.hibernate.default_schema"))){
			properties.put("hibernate.default_schema", environment.getRequiredProperty("spring.jpa.properties.hibernate.default_schema"));
		}
		return properties;
	}

	@Bean(name = "sqlServerTransactionManager")
	@Autowired
	public PlatformTransactionManager sqlServerTransactionManager(@Qualifier("sqlServerEntityManagerFactory") EntityManagerFactory emf) {
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(emf);
		return txManager;
	}

}
