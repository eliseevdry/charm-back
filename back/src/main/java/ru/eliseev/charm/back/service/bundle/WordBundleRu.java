package ru.eliseev.charm.back.service.bundle;

import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class WordBundleRu extends WordBundle {

    private WordBundleRu() {
        super(Locale.of("ru"));
    }
}
