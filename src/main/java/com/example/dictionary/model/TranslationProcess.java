package com.example.dictionary.model;

import java.util.List;

public class TranslationProcess {

    private List<DictionaryWord> words;
    private boolean running = true;

    public List<DictionaryWord> getWords() {
        return words;
    }

    public void setWords(List<DictionaryWord> words) {
        this.words = words;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

}
