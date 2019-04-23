package com.ampcus.rtmclient.config;

import java.beans.PropertyVetoException;
import java.util.HashMap;
import java.util.Properties;
import javax.activation.DataSource;
import javax.swing.SwingWorker;

import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;

@Configuration
@EnableTransactionManagement
@PropertySources({
	@PropertySource("classpath:db.properties"),
    @PropertySource("classpath:ui.properties")
})

@ComponentScan({ "com.ampcus.rtmclient, "
		+ "com.ampcus.rtmclient.config, "
		+ "com.ampcus.rtmclient.controller, "
		+ "com.ampcus.rtmclient.core.entity, "
		+ "com.ampcus.rtmclient.core.dao, "
		+ "com.ampcus.rtmclient.ui.views, "
		+ "com.ampcus.rtmclient.ui.views.helper,"
		+ "com.ampcus.rtmclient.ui.service"})
public class ClientConfiguration{

	private ClientConfiguration config;
	
	@Autowired
	private Environment env;

	private HashMap<String, String> uiPropertiesMap = new HashMap();
	@Bean("uiPropertiesMap")
	public HashMap getUiPropertiesMap() {
		uiPropertiesMap.put("main.frame.title",env.getProperty("main.frame.title"));
		uiPropertiesMap.put("opening.logo.path",env.getProperty("opening.logo.path"));
		uiPropertiesMap.put("button.screen.area.selector.label",env.getProperty("button.screen.area.selector.label"));
		uiPropertiesMap.put("button.save.selected.area.label",env.getProperty("button.save.selected.area.label"));
		uiPropertiesMap.put("label.instructions.extractedtext",env.getProperty("label.instructions.extractedtext"));
		uiPropertiesMap.put("button.complete.crop.selection",env.getProperty("button.complete.crop.selection"));
		uiPropertiesMap.put("info.message.requirement.saved",env.getProperty("info.message.requirement.saved"));
		uiPropertiesMap.put("error.message.requirement.text.required",env.getProperty("error.message.requirement.text.required"));
		return uiPropertiesMap;
	}
	
	private HashMap<String, String> serverPropertiesMap = new HashMap();
	@Bean("serverPropertiesMap")
    public HashMap<String, String> getServerPropertiesMap() {
		serverPropertiesMap.put("server.url.new.reqt", env.getProperty("server.url.new.reqt"));
		return serverPropertiesMap;
	}
	
	
	
	public ClientConfiguration() {
		//empty
	}
	
/**
 * 
	@Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
       return new PersistenceExceptionTranslationPostProcessor();
    }	

	@Bean
	@Autowired
	public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
		
		HibernateTransactionManager txManager = new HibernateTransactionManager();
		txManager.setSessionFactory(sessionFactory);
		return txManager;
	}
	
	
	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		try
		{
			sessionFactory.setDataSource(getDataSource());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		sessionFactory.setPackagesToScan(new String[] {"com.ampcus.rtmclient.core.dao, com.ampcus.rtmclient.core.entity" });
		sessionFactory.setHibernateProperties(hibernateProperties());
		return sessionFactory;		
	}
	
	
	@Bean
	public ComboPooledDataSource getDataSource() throws PropertyVetoException {
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		dataSource.setDriverClass(env.getProperty("hibernate.connection.driver_class"));
		dataSource.setJdbcUrl(env.getProperty("hibernate.connection.url"));
		dataSource.setUser(env.getProperty("hibernate.connection.username"));
		dataSource.setPassword(env.getProperty("hibernate.connection.password"));
		return dataSource;
	}
	
	private Properties hibernateProperties()
	{
		Properties props = new Properties();
		
		props.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
		props.setProperty("hibernate.id.new_generator_mappings", env.getProperty("hibernate.id.new_generator_mappings"));
		props.setProperty("hibernate.connection.provider_class", env.getProperty("hibernate.connection.provider_class"));
		props.setProperty("hibernate.cache.use_second_level_cache", env.getProperty("hibernate.cache.use_second_level_cache"));
		props.setProperty("hibernate.cache.use_query_cache", env.getProperty("hibernate.cache.use_query_cache"));
		props.setProperty("hibernate.c3p0.min_size", env.getProperty("hibernate.c3p0.min_size"));
		props.setProperty("hibernate.c3p0.max_size", env.getProperty("hibernate.c3p0.max_size"));
		props.setProperty("hibernate.c3p0.timeout", env.getProperty("hibernate.c3p0.timeout"));
		props.setProperty("hibernate.c3p0.max_statements", env.getProperty("hibernate.c3p0.max_statements"));
		props.setProperty("hibernate.c3p0.idle_test_period", env.getProperty("hibernate.c3p0.idle_test_period"));
		props.setProperty("preferredTestQuery", env.getProperty("preferredTestQuery"));
		props.setProperty("testConnectionOnCheckin", env.getProperty("testConnectionOnCheckin"));
		props.setProperty("testConnectionOnCheckout", env.getProperty("testConnectionOnCheckout"));
		props.setProperty("idleConnectionTestPeriod", env.getProperty("idleConnectionTestPeriod"));
		props.setProperty("show_sql", env.getProperty("show_sql"));
		props.setProperty("format_sql", env.getProperty("format_sql"));
		
		return props;
	}
 * 
 */

}
