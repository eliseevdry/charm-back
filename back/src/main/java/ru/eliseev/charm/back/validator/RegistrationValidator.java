package ru.eliseev.charm.back.validator;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.eliseev.charm.back.dao.ProfileDao;
import ru.eliseev.charm.back.dto.RegistrationDto;

import static ru.eliseev.charm.utils.StringUtils.isValidEmail;
import static ru.eliseev.charm.utils.StringUtils.isValidPassword;

@Setter
@Component
public class RegistrationValidator implements Validator<RegistrationDto> {

    @Autowired
    private ProfileDao dao;

    @Override
    public ValidationResult validate(RegistrationDto dto) {
        ValidationResult result = new ValidationResult();
        if (!isValidEmail(dto.getEmail())) {
            result.add("error.email.invalid");
        } else if (dao.existByEmail(null, dto.getEmail())) {
            result.add("error.email.exist");
        }
        if (!isValidPassword(dto.getPassword()) || !dto.getPassword().equals(dto.getConfirm())) {
            result.add("error.password.invalid");
        }
        return result;
    }
}
