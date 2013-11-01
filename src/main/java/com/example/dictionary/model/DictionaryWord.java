package com.example.dictionary.model;

public class DictionaryWord {
	private String polishWord;
	private String englishWord;

	public DictionaryWord(Builder builder) {
		this.polishWord = builder.polishWord;
		this.englishWord = builder.englishWord;
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
