package com.example.dictionary.repositories;

import com.example.dictionary.model.DictionaryWord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Profile("jdbc")
public class DataSourceRepository extends Repository {

	JdbcTemplate jdbcTemplate;
	SimpleJdbcInsert insertWord;

	@Autowired
	public DataSourceRepository(DataSource ds) {
		jdbcTemplate = new JdbcTemplate(ds);
		insertWord = new SimpleJdbcInsert(jdbcTemplate)
			.withTableName("words")
			.usingColumns("polish_word", "english_word")
			.usingGeneratedKeyColumns("id");
	}
	
	public List<DictionaryWord> getSavedWords() {
		return jdbcTemplate.query("select * from words",
                (rs, rowNum) -> {
                    return DictionaryWord.fromPolishWord(rs.getString("polish_word"))
                            .withEnglishWord(rs.getString("english_word"))
                            .build();
                });
	}

    @Transactional
	public void addWord(DictionaryWord word) {
        Map<String, Object> parameters = new HashMap<>(2);
        parameters.put("polish_word", word.getPolishWord());
        parameters.put("english_word", word.getEnglishWord());
        insertWord.execute(parameters);		
	}

}
