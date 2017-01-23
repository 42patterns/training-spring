package com.example.dictionary.translation;

public class DictionaryWord {
    public final String polishWord;
    public final String englishWord;

    public DictionaryWord(String polishWord, String englishWord) {
        this.polishWord = polishWord;
        this.englishWord = englishWord;
    }

    @Override
    public String toString() {
        return "DictionaryWord{" +
                "polishWord='" + polishWord + '\'' +
                ", englishWord='" + englishWord + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DictionaryWord that = (DictionaryWord) o;

        if (polishWord != null ? !polishWord.equals(that.polishWord) : that.polishWord != null) return false;
        return englishWord != null ? englishWord.equals(that.englishWord) : that.englishWord == null;
    }

    @Override
    public int hashCode() {
        int result = polishWord != null ? polishWord.hashCode() : 0;
        result = 31 * result + (englishWord != null ? englishWord.hashCode() : 0);
        return result;
    }
}
