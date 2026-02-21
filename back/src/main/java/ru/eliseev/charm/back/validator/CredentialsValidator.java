package ru.eliseev.charm.back.validator;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.eliseev.charm.back.dao.ProfileDao;
import ru.eliseev.charm.back.dto.CredentialsDto;
import ru.eliseev.charm.back.model.Profile;

import static ru.eliseev.charm.utils.StringUtils.isValidEmail;
import static ru.eliseev.charm.utils.StringUtils.isValidPassword;

@Setter
@Component
public class CredentialsValidator implements Validator<CredentialsDto> {

    @Autowired
    private ProfileDao dao;

    @Override
    public ValidationResult validate(CredentialsDto dto) {
        ValidationResult result = new ValidationResult();
        Profile profile = dao.findById(dto.getId()).orElseThrow();
        if (!dto.getCurrentPassword().equals(profile.getPassword())) {
            result.add("error.password.invalid");
        }
        if (dto.getEmail() != null) {
            if (!isValidEmail(dto.getEmail())) {
                result.add("error.email.invalid");
            }
            if (dao.existByEmail(dto.getId(), dto.getEmail())) {
                result.add("error.email.exist");
            }
        }
        if (!isValidPassword(dto.getNewPassword()) || !dto.getNewPassword().equals(dto.getConfirmNewPassword())) {
            result.add("error.password.invalid");
        }
        return result;
    }
}
