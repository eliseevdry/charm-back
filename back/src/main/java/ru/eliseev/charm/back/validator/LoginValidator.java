package ru.eliseev.charm.back.validator;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.eliseev.charm.back.dao.ProfileDao;
import ru.eliseev.charm.back.dto.LoginDto;

import static ru.eliseev.charm.utils.StringUtils.isValidEmail;

@Setter
@Component
public class LoginValidator implements Validator<LoginDto> {

    @Autowired
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
