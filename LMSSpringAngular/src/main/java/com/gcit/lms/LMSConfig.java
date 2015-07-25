package com.gcit.lms;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.BorrowerDAO;
import com.gcit.lms.dao.GenreDAO;
import com.gcit.lms.dao.LibraryDAO;
import com.gcit.lms.dao.PublisherDAO;
import com.gcit.lms.service.AdministratorService;
import com.mongodb.MongoClient;

@EnableTransactionManagement
@Configuration
public class LMSConfig {

	private static final String driver = "com.mysql.jdbc.Driver";
	private static final String url = "jdbc:mysql://localhost:3306/library";
	private static final String user = "root";
	private static final String pwd = "";

	@Bean
	public AdministratorService adminService() {
		return new AdministratorService();
	}

	@Bean
	public BookDAO bkDao() {
		return new BookDAO();
	}

	@Bean
	public AuthorDAO authDao() {
		return new AuthorDAO();
	}

	@Bean
	public PublisherDAO pubDao() {
		return new PublisherDAO();
	}

	@Bean
	public GenreDAO genrreDAO() {
		return new GenreDAO();
	}

	@Bean
	public LibraryDAO libraryDAO() {
		return new LibraryDAO();
	}

	@Bean
	public BorrowerDAO borrowerDAO() {
		return new BorrowerDAO();
	}

	@Bean
	public PlatformTransactionManager txManager() {
		DataSourceTransactionManager tx = new DataSourceTransactionManager();
		tx.setDataSource(datasource());

		return tx;
	}

	@Bean
	public JdbcTemplate template() {
		JdbcTemplate jdbc = new JdbcTemplate();
		jdbc.setDataSource(datasource());

		return jdbc;
	}

	@Bean
	public BasicDataSource datasource() {
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName(driver);
		ds.setUrl(url);
		ds.setUsername(user);
		ds.setPassword(pwd);

		return ds;
	}

	@Bean
	public MongoDbFactory getMongoDbFactory() throws Exception {
		return new SimpleMongoDbFactory(new MongoClient("localhost", 27017),
				"local");
	}

	@Bean
	public MongoTemplate getMongoTemplate() throws Exception {
		MongoTemplate mongoTemplate = new MongoTemplate(getMongoDbFactory());
		return mongoTemplate;
	}
}
