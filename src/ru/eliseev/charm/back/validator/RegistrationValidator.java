package ru.eliseev.charm.back.validator;

import static ru.eliseev.charm.back.utils.StringUtils.isValidEmail;
import static ru.eliseev.charm.back.utils.StringUtils.isValidPassword;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.eliseev.charm.back.dao.ProfileDao;
import ru.eliseev.charm.back.dto.RegistrationDto;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RegistrationValidator implements Validator<RegistrationDto> {

	private final ProfileDao dao = ProfileDao.getInstance();

    private static final RegistrationValidator INSTANCE = new RegistrationValidator();

    public static RegistrationValidator getInstance() {
        return INSTANCE;
    }

    @Override
    public ValidationResult validate(RegistrationDto dto) {
        ValidationResult result = new ValidationResult();
        if (!isValidEmail(dto.getEmail())) {
            result.add("error.email.invalid");
        } else if (dao.existByEmail(dto.getEmail())) {
            result.add("error.email.exist");
        }
        if (!isValidPassword(dto.getPassword()) || !dto.getPassword().equals(dto.getConfirm())) {
            result.add("error.password.invalid");
        }
        return result;
    }
}
