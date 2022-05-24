package com.cs.analyser;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@SpringBootApplication
//@EnableConfigurationProperties
//@ImportResource(value="classpath:hsql_cfg.xml")
public class LogAnalysisSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogAnalysisSystemApplication.class, args);
	}
	/*

	@Bean(initMethod = "start", destroyMethod = "stop")
	public Server hsqlServer(@Value("classpath:/hsqldb.properties") Resource props)
			throws IOException{
		Server bean = new Server();
		
		return bean;
	}
*/
	@Bean
	//@DependsOn("hsqlServer")
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.hsqldb.jdbcDriver");
		dataSource.setUrl("jdbc:hsqldb:hsql://localhost/loganalysisdb");
		dataSource.setUsername("SA");
		dataSource.setPassword("");
		return dataSource;
	}

}
