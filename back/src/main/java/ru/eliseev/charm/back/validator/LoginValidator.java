package ru.eliseev.charm.back.validator;

import lombok.Setter;
import ru.eliseev.charm.back.dao.ProfileDao;
import ru.eliseev.charm.back.dto.LoginDto;

import static ru.eliseev.charm.utils.StringUtils.isValidEmail;

@Setter
public class LoginValidator implements Validator<LoginDto> {

    private ProfileDao dao;

    @Override
    public ValidationResult validate(LoginDto dto) {
        ValidationResult result = new ValidationResult();
        if (!isValidEmail(dto.getEmail())) {
            result.add("error.email.invalid");
        }
        if (!dao.existByEmail(null, dto.getEmail())) {
            result.add("error.email.missing");
        }
        return result;
    }
}
