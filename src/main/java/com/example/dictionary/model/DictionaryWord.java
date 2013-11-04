package com.example.dictionary.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="words")
@NamedQuery(name=DictionaryWord.GET_ALL_WORD, 
		query="select w from DictionaryWord w")
public class DictionaryWord {
	
	public final static String GET_ALL_WORD = "DictionaryWord.getAllWords";
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;

	@Column(name="polish_word")
	private String polishWord;
	
	@Column(name="english_word")
	private String englishWord;

	public DictionaryWord() {
	}
	
	public DictionaryWord(Builder builder) {
		this.polishWord = builder.polishWord;
		this.englishWord = builder.englishWord;
	}

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getPolishWord() {
		return polishWord;
	}

	public void setPolishWord(String polishWord) {
		this.polishWord = polishWord;
	}

	public String getEnglishWord() {
		return englishWord;
	}

	public void setEnglishWord(String englishWord) {
		this.englishWord = englishWord;
	}

	@Override
	public String toString() {
		return "DictionaryWord [polishWord=" + polishWord + ", englishWord="
				+ englishWord + "]";
	}

	public static Builder fromPolishWord(String word) {
		return new Builder(word);
	}

	public static class Builder {
		private String polishWord;
		private String englishWord;

		public Builder(String word) {
			this.polishWord = word;
		}

		public Builder withEnglishWord(String word) {
			this.englishWord = word;
			return this;
		}

		public DictionaryWord build() {
			return new DictionaryWord(this);
		}
	}

}
