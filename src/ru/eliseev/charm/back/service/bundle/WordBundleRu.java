package ru.eliseev.charm.back.service.bundle;

import java.util.Locale;

public class WordBundleRu extends WordBundle {
    private static final WordBundleRu INSTANCE = new WordBundleRu();

    public static WordBundleRu getInstance() {
        return INSTANCE;
    }

    private WordBundleRu() {
        super(Locale.of("ru"));
    }
}
