package com.example.dictionary.repositories;

import com.example.dictionary.model.DictionaryWord;
import org.hibernate.SessionFactory;
import org.hibernate.dialect.HSQLDialect;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;
import java.util.Properties;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

@ContextConfiguration(classes = {
        HibernateRepositoryTest.Config.class,
        HibernateRepository.class
})
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("hibernate")
public class HibernateRepositoryTest {

    @Autowired
    HibernateRepository hibernateRepository;

    @Autowired
    DataSource ds;

    @Test
    public void should_save_single_word() {
        //when
        hibernateRepository.addWord(DictionaryWord.fromPolishWord("pole").withEnglishWord("field").build());
        List<DictionaryWord> savedWords = hibernateRepository.getSavedWords();

        //then
        JdbcTemplate template = new JdbcTemplate(ds);
        List<DictionaryWord> words = template.query("select * from words", (rs, rowNum) -> {
            return DictionaryWord.fromPolishWord(rs.getString("polish_word"))
                    .withEnglishWord(rs.getString("english_word"))
                    .build();
        });
        assertThat(savedWords, is(equalTo(words)));
    }

    @Configuration
    @EnableTransactionManagement
    public static class Config {

        @Bean
        public HibernateTransactionManager hibernateTxManager(SessionFactory factory) {
            HibernateTransactionManager tx = new HibernateTransactionManager();
            tx.setSessionFactory(factory);
            return tx;
        }

        @Bean
        public LocalSessionFactoryBean session(DataSource ds) {
            LocalSessionFactoryBean session = new LocalSessionFactoryBean();
            session.setDataSource(ds);
            session.setPackagesToScan(new String[]{"com.example.dictionary.model"});

            Properties props = new Properties();
            props.put("hibernate.dialect", HSQLDialect.class.getName());
            props.put("hibernate.hbm2ddl.auto", "create-drop");
            props.put("show_sql", true);
            session.setHibernateProperties(props);

            return session;
        }

        @Bean
        public DataSource dataSource() {
            return new EmbeddedDatabaseBuilder()
                    .setType(EmbeddedDatabaseType.HSQL)
                    .build();
        }

    }
}
