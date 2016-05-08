package config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
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

import javasrc.listener.FirstQueueListener;
import javasrc.listener.SecondQueueListener;


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
	}*/
	
	/**
	 * 配置spring jdbc模板*/
	/*@Bean
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
	public ActiveMQQueue getFirstQueue(){
		System.out.println("初始化队列1。");
		String queuename="queue1";
		ActiveMQQueue activeMQQueue=new ActiveMQQueue(queuename);
		return activeMQQueue;
	}
	
	@Bean
	public FirstQueueListener getFirstQueueListener(){
		System.out.println("初始化队列1Listener。");
		FirstQueueListener consumerMessageListener=new FirstQueueListener();
		return consumerMessageListener;
	}
	
	@Bean
	public DefaultMessageListenerContainer getFirstQueueListenerContainer(){
		System.out.println("初始化队列1ListenerContainer。");
		DefaultMessageListenerContainer defaultMessageListenerContainer=new DefaultMessageListenerContainer();
		defaultMessageListenerContainer.setConnectionFactory(getSingleConnectionFactory());
		defaultMessageListenerContainer.setDestination(getFirstQueue());
		defaultMessageListenerContainer.setMessageListener(getFirstQueueListener());
		return defaultMessageListenerContainer;
	}
	
	@Bean
	public ActiveMQQueue getSecondQueue(){
		System.out.println("初始化队列2。");
		String queuename="queue2";
		ActiveMQQueue activeMQQueue=new ActiveMQQueue(queuename);
		return activeMQQueue;
	}
	
	@Bean
	public SecondQueueListener getSecondQueueListener(){
		System.out.println("初始化队列2Listener。");
		SecondQueueListener secondQueueListener=new SecondQueueListener();
		return secondQueueListener;
	}
	
	@Bean
	public DefaultMessageListenerContainer getSecondQueueListenerContainer(){
		System.out.println("初始化队列2ListenerContainer。");
		DefaultMessageListenerContainer defaultMessageListenerContainer=new DefaultMessageListenerContainer();
		defaultMessageListenerContainer.setConnectionFactory(getSingleConnectionFactory());
		defaultMessageListenerContainer.setDestination(getSecondQueue());
		defaultMessageListenerContainer.setMessageListener(getSecondQueueListener());
		return defaultMessageListenerContainer;
	}
}
