package ru.eliseev.charm.back.dto;

import ru.eliseev.charm.back.model.Gender;
import ru.eliseev.charm.back.model.Status;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProfileUpdateQueryBuilder {
    //language=POSTGRES-PSQL
    public static final String UPDATE_BASE = "UPDATE profile SET id = id";

    private final StringBuilder sb;
    private final List<Object> args;

    public ProfileUpdateQueryBuilder() {
        this.sb = new StringBuilder(UPDATE_BASE);
        this.args = new ArrayList<>();
    }

    public ProfileUpdateQueryBuilder addEmail(String email) {
        if (email == null) {
            return this;
        }
        sb.append(", email = ?");
        args.add(email);
        return this;
    }

    public ProfileUpdateQueryBuilder addPassword(String password) {
        if (password == null) {
            return this;
        }
        sb.append(", password = ?");
        args.add(password);
        return this;
    }

    public ProfileUpdateQueryBuilder addName(String name) {
        if (name == null) {
            return this;
        }
        sb.append(", name = ?");
        args.add(name);
        return this;
    }

    public ProfileUpdateQueryBuilder addSurname(String surname) {
        if (surname == null) {
            return this;
        }
        sb.append(", surname = ?");
        args.add(surname);
        return this;
    }

    public ProfileUpdateQueryBuilder addBirthDate(LocalDate birthDate) {
        if (birthDate == null) {
            return this;
        }
        sb.append(", birth_date = ?");
        args.add(Date.valueOf(birthDate));
        return this;
    }

    public ProfileUpdateQueryBuilder addAbout(String about) {
        if (about == null) {
            return this;
        }
        sb.append(", about = ?");
        args.add(about);
        return this;
    }

    public ProfileUpdateQueryBuilder addGender(Gender gender) {
        if (gender == null) {
            return this;
        }
        sb.append(", gender = ?");
        args.add(gender.toString());
        return this;
    }

    public ProfileUpdateQueryBuilder addStatus(Status status) {
        if (status == null) {
            return this;
        }
        sb.append(", status = ?");
        args.add(status.toString());
        return this;
    }

    public ProfileUpdateQueryBuilder addPhoto(String photo) {
        if (photo == null) {
            return this;
        }
        sb.append(", photo = ?");
        args.add(photo);
        return this;
    }

    public Query build(Long id, int version) {
        sb.append(", version = ? WHERE id = ? AND version = ?");
        args.add(version + 1);
        args.add(id);
        args.add(version);
        return new Query(sb.toString(), args);
    }
}
