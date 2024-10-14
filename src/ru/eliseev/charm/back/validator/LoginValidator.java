package ru.eliseev.charm.back.validator;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.eliseev.charm.back.dao.ProfileDao;
import ru.eliseev.charm.back.dto.LoginDto;
import ru.eliseev.charm.back.model.Profile;

import java.util.Optional;

import static ru.eliseev.charm.back.utils.StringUtils.VALID_EMAIL_ADDRESS_REGEX;
import static ru.eliseev.charm.back.utils.StringUtils.isBlank;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginValidator implements Validator<LoginDto> {

    private final ProfileDao dao = ProfileDao.getInstance();

    private static final LoginValidator INSTANCE = new LoginValidator();

    public static LoginValidator getInstance() {
        return INSTANCE;
    }

    @Override
    public ValidationResult validate(LoginDto dto) {
        Optional<Profile> profile = dao.findByEmail(dto.getEmail());
        ValidationResult result = new ValidationResult();
        if (isBlank(dto.getEmail()) || !VALID_EMAIL_ADDRESS_REGEX.matcher(dto.getEmail()).matches()) {
            result.add("error.email.invalid");
        } else if (profile.isEmpty()) {
            result.add("error.email.missing");
        } else if (isBlank(dto.getPassword()) || !dto.getPassword().equals(profile.get().getPassword())) {
            result.add("error.password.invalid");
        }
        return result;
    }
}
