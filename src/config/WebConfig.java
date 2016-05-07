package config;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jms.connection.SingleConnectionFactory;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javasrc.listener.ConsumerMessageListener;


@Configuration
@PropertySource("classpath:app.properties")
@ComponentScan(basePackages = "javasrc")
@EnableTransactionManagement
@EnableScheduling
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {
	@Autowired
	private Environment env;
	/**
	 * 配置数据源（tomcat jdbc 连接池）*/
	/*@Bean
	public DataSource getDataSource(){
		System.out.println("初始化DataSource。");
        PoolProperties p = new PoolProperties();
        p.setUrl(env.getProperty("jdbc.url"));
        p.setDriverClassName(env.getProperty("jdbc.driverClassName"));
        p.setUsername(env.getProperty("jdbc.username"));
        p.setPassword(env.getProperty("jdbc.password"));
        p.setTestWhileIdle(false);
        p.setTestOnBorrow(true);
        p.setValidationQuery("SELECT 1");
        p.setValidationInterval(30000);
        p.setTimeBetweenEvictionRunsMillis(30000);
        p.setMaxActive(100);
        p.setInitialSize(10);
        p.setMaxWait(10000);
        p.setRemoveAbandonedTimeout(60);
        p.setMinEvictableIdleTimeMillis(30000);
        p.setMinIdle(10);
        p.setRemoveAbandoned(true);
        DataSource datasource = new DataSource();
        datasource.setPoolProperties(p);
		return datasource;
	}
	
	*//**
	 * 配置spring jdbc模板*//*
	@Bean
	public JdbcTemplate getJdbcTemplate(){
		System.out.println("配置JdbcTemplate。");
		JdbcTemplate jdbcTemplate=new JdbcTemplate();
		jdbcTemplate.setDataSource(getDataSource());
		return jdbcTemplate;
	}*/
	
	/**
	 * 配置文件上传*/
	@Bean(name="multipartResolver")
	public CommonsMultipartResolver getCommonsMultipartResolver(){
		System.out.println("配置上传文件解析器。");
		CommonsMultipartResolver commonsMultipartResolver=new CommonsMultipartResolver();
		commonsMultipartResolver.setMaxUploadSize(209715200);
		return commonsMultipartResolver;
	}
	
	@Bean
	public ActiveMQConnectionFactory getActiveMQConnectionFactory(){
		System.out.println("初始化activeMQConnectionFactory。");
		ActiveMQConnectionFactory activeMQConnectionFactory=new ActiveMQConnectionFactory();
		activeMQConnectionFactory.setBrokerURL("tcp://localhost:61616");
		return activeMQConnectionFactory;
	}
	
	@Bean 
	public SingleConnectionFactory getSingleConnectionFactory(){
		System.out.println("初始化singleConnectionFactory。");
		SingleConnectionFactory singleConnectionFactory=new SingleConnectionFactory();
		singleConnectionFactory.setTargetConnectionFactory(getActiveMQConnectionFactory());
		return singleConnectionFactory;
	}
	
	@Bean
	public ActiveMQQueue getActiveMQQueue(){
		System.out.println("初始化activeMQQueue。");
		ActiveMQQueue activeMQQueue=new ActiveMQQueue("queue");
		return activeMQQueue;
	}
	
	@Bean
	public ConsumerMessageListener getConsumerMessageListener(){
		System.out.println("初始化consumerMessageListener。");
		ConsumerMessageListener consumerMessageListener=new ConsumerMessageListener();
		return consumerMessageListener;
	}
	
	@Bean
	public DefaultMessageListenerContainer getDefaultMessageListenerContainer(){
		System.out.println("初始化jmsContainer。");
		DefaultMessageListenerContainer defaultMessageListenerContainer=new DefaultMessageListenerContainer();
		defaultMessageListenerContainer.setConnectionFactory(getSingleConnectionFactory());
		defaultMessageListenerContainer.setDestination(getActiveMQQueue());
		defaultMessageListenerContainer.setMessageListener(getConsumerMessageListener());
		return defaultMessageListenerContainer;
	}
}
