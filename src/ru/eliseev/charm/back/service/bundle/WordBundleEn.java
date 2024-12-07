package ru.eliseev.charm.back.service.bundle;

import java.util.Locale;

public class WordBundleEn extends WordBundle {

    private static final WordBundleEn INSTANCE = new WordBundleEn();

    public static WordBundleEn getInstance() {
        return INSTANCE;
    }

    private WordBundleEn() {
        super(Locale.ENGLISH);
    }
}
