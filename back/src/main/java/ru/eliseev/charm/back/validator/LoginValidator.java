package ru.eliseev.charm.back.validator;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.eliseev.charm.back.dao.ProfileDao;
import ru.eliseev.charm.back.dto.LoginDto;

import static ru.eliseev.charm.utils.StringUtils.isValidEmail;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginValidator implements Validator<LoginDto> {

	private final ProfileDao dao = ProfileDao.getInstance();

    private static final LoginValidator INSTANCE = new LoginValidator();

    public static LoginValidator getInstance() {
        return INSTANCE;
    }

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
