package com.example.dictionary.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import com.example.dictionary.model.DictionaryWord;

@Component
@Qualifier("datasource")
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
				new RowMapper<DictionaryWord>() {

					public DictionaryWord mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return DictionaryWord.fromPolishWord(rs.getString("polish_word"))
								.withEnglishWord(rs.getString("english_word"))
								.build();
					}
			
		});
	}

	public void addWord(DictionaryWord word) {
        Map<String, Object> parameters = new HashMap<String, Object>(2);
        parameters.put("polish_word", word.getPolishWord());
        parameters.put("english_word", word.getEnglishWord());
        insertWord.execute(parameters);		
	}

}
