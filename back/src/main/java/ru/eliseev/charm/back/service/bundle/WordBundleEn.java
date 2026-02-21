package ru.eliseev.charm.back.service.bundle;

import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class WordBundleEn extends WordBundle {

    private WordBundleEn() {
        super(Locale.ENGLISH);
    }
}
