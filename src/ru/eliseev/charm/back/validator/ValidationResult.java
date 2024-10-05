package ru.eliseev.charm.back.validator;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ValidationResult {
    
    private final List<String> errors = new ArrayList<>();

    public void add(String code) {
        this.errors.add(code);
    }

    public boolean isValid() {
        return errors.isEmpty();
    }
    
    public List<String> getErrors() {
        return new ArrayList<>(errors);
    }
}
