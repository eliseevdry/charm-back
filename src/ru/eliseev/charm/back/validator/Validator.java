package ru.eliseev.charm.back.validator;

public interface Validator<T> {

    ValidationResult validate(T object);
}
