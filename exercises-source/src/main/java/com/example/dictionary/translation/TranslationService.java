package com.example.dictionary.translation;

import jdk.internal.util.xml.impl.Pair;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class TranslationService {
    private static Logger log = Logger.getLogger(TranslationService.class);
    private static Pattern pat = Pattern
            .compile(".*<a href=\"dict\\?words?=(.*)&lang.*");

    private String urlStringTemplate = "http://www.dict.pl/dict?word={}&words=&lang=PL";

    public TranslationService() {
    }

    public List<DictionaryWord> getTranslationsForWord(String wordToTranslate) {
        List<String> words = getWords(wordToTranslate);

        return streamOfPairs(words)
                .map(e -> new DictionaryWord(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
    }

    private List<String> getWords(String wordToFind) {
        String urlString = urlStringTemplate.replace("{}", wordToFind);
        log.info("URL: " + urlString);

        try (CloseableUrlConnection conn = new CloseableUrlConnection(urlString);
             InputStream is = conn.getInputStream()) {

            String s = new Scanner(is).useDelimiter("\\A").next();
            return Arrays.asList(s.split("\n")).stream()
                    .filter(l -> pat.asPredicate().test(l))
                    .map(l -> matchWord(l))
                    .collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }

    }

    private String matchWord(String l) {
        Matcher m = pat.matcher(l);
        m.find();
        return m.group(m.groupCount());
    }

    private <T> Stream<Entry<T, T>> streamOfPairs(List<T> words) {
        return StreamSupport.stream(new PairSpliterator<T>(words.spliterator()), false);
    }

    private static class PairSpliterator<T> extends Spliterators.AbstractSpliterator<Entry<T, T>> {
        private final Spliterator<T> wrappedSpliterator;
        private PairConsumer<T> pair = new PairConsumer<T>();

        public PairSpliterator(Spliterator<T> wrapperedSpliterator) {
            super(wrapperedSpliterator.estimateSize(), wrapperedSpliterator.characteristics());
            this.wrappedSpliterator = wrapperedSpliterator;
        }

        @Override
        public boolean tryAdvance(Consumer<? super Entry<T, T>> action) {
            this.pair = new PairConsumer<T>();

            if (wrappedSpliterator.tryAdvance(pair.acceptLeft()) &&
                    wrappedSpliterator.tryAdvance(pair.acceptRight())) {
                action.accept(new AbstractMap.SimpleEntry<T, T>(pair.get()));
                return true;
            }

            return false;

        }

        private static class PairConsumer<T> implements Supplier<Entry<T, T>> {

            private T left;
            private T right;

            public Consumer<T> acceptLeft() {
                return t -> {
                    if (Objects.nonNull(left)) {
                        throw new IllegalStateException("Not null");
                    }
                    this.left = t;
                };
            }

            public Consumer<T> acceptRight() {
                return t -> {
                    if (Objects.nonNull(right)) {
                        throw new IllegalStateException("Not null");
                    }
                    this.right = t;
                };
            }

            @Override
            public Entry<T, T> get() {
                Objects.requireNonNull(left);
                Objects.requireNonNull(right);
                return new AbstractMap.SimpleEntry<T, T>(left, right);
            }
        }
    }


    private static class CloseableUrlConnection implements AutoCloseable {
        private final URLConnection conn;

        public CloseableUrlConnection(String url) throws IOException {
            this.conn = new URL(url).openConnection();
            this.conn.connect();
        }

        public InputStream getInputStream() throws IOException {
            return this.conn.getInputStream();
        }

        @Override
        public void close() throws IOException {
            getInputStream().close();
        }
    }

}