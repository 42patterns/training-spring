package com.example;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.hibernate.SessionFactory;
import org.hibernate.dialect.MySQL5Dialect;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.jms.ConnectionFactory;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ComponentScan(value = {"com.example.dictionary", "com.example.helloworld"},
        excludeFilters = @ComponentScan.Filter(
                value = Configuration.class,
                type = FilterType.ANNOTATION)
)
@PropertySource("classpath:META-INF/spring/dict.properties")
@EnableAspectJAutoProxy
@EnableTransactionManagement
@Import({AppConfiguration.JdbcConfiguration.class,
        AppConfiguration.HibernateConfiguration.class,
        AppConfiguration.JpaConfiguration.class,
        AppConfiguration.MessagingConfiguration.class})
public class AppConfiguration {

    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean(name = "validator")
    public LocalValidatorFactoryBean validator() {
        return new LocalValidatorFactoryBean();
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName(org.hsqldb.jdbc.JDBCDriver.class.getName());
//        ds.setUrl("jdbc:hsqldb:file:/tmp/testdb");
        ds.setUrl("jdbc:hsqldb:mem:testmemdb");
        ds.setUsername("SA");
        ds.setPassword("");
        return ds;
    }

    @Configuration
    @Profile("jdbc")
    public static class JdbcConfiguration {

        @Bean
        public DataSourceTransactionManager transactionManager(DataSource ds) {
            return new DataSourceTransactionManager(ds);
        }

    }

    @Configuration
    @Profile("jpa")
    public static class JpaConfiguration {

        @Bean
        public JpaVendorAdapter adapter() {
            HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
            vendorAdapter.setShowSql(Boolean.TRUE);
            vendorAdapter.setGenerateDdl(true);
            return vendorAdapter;
        }

        @Bean
        public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource ds, JpaVendorAdapter vendorAdapter) {
            LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
            emf.setJpaVendorAdapter(vendorAdapter);
            emf.setJpaDialect(new HibernateJpaDialect());
            emf.setPackagesToScan("com.example.dictionary.model");
            emf.setDataSource(ds);
            return emf;
        }

        @Bean
        public JpaTransactionManager jpaTxManager(EntityManagerFactory emf) {
            JpaTransactionManager tx = new JpaTransactionManager();
            tx.setEntityManagerFactory(emf);
            return tx;
        }

    }

    @Configuration
    @Profile("hibernate")
    public static class HibernateConfiguration {

        @Bean
        public LocalSessionFactoryBean session(DataSource ds) {
            LocalSessionFactoryBean session = new LocalSessionFactoryBean();
            session.setDataSource(ds);
            session.setPackagesToScan(new String[]{"com.example.dictionary.model"});

            Properties props = new Properties();
            props.put("hibernate.dialect", MySQL5Dialect.class.getName());
            props.put("hibernate.show_sql", true);
            props.put("hibernate.hbm2ddl.auto", "create-drop");
            session.setHibernateProperties(props);

            return session;
        }

        @Bean
        public HibernateTransactionManager hibernateTxManager(SessionFactory factory) {
            HibernateTransactionManager tx = new HibernateTransactionManager();
            tx.setSessionFactory(factory);
            return tx;
        }
    }

    @Configuration
    public static class MessagingConfiguration {

        private static final String DEFAULT_BROKER_URL = "tcp://localhost:61616";

        private static final String ORDER_QUEUE = "words-queue";

        @Bean
        public ActiveMQConnectionFactory connectionFactory(){
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
            connectionFactory.setBrokerURL(DEFAULT_BROKER_URL);
            connectionFactory.setTrustAllPackages(true);
            return connectionFactory;
        }

        @Bean
        public JmsTemplate jmsTemplate(ConnectionFactory cf){
            JmsTemplate template = new JmsTemplate();
            template.setConnectionFactory(cf);
            template.setDefaultDestinationName(ORDER_QUEUE);
            return template;
        }

    }
}

