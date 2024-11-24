package ru.eliseev.charm.back.service.bundle;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public abstract class WordBundle {

    private final ResourceBundle resourceBundle;

    protected WordBundle(Locale locale) {
        this.resourceBundle = ResourceBundle.getBundle("words", locale);
    }

    public String getWord(String key) {
        String result;
        try {
            result = resourceBundle.getString(key.toLowerCase());
        } catch (MissingResourceException | ClassCastException e) {
            String[] pathArray = key.split("\\.");
            result = pathArray[pathArray.length - 1];
        } catch (Exception e) {
            return "* empty *";
        }
        return result;
    }
}
