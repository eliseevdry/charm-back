package ru.eliseev.charm.back.validator;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.eliseev.charm.back.dao.ProfileDao;
import ru.eliseev.charm.back.dto.RegistrationDto;

import java.util.regex.Pattern;

import static ru.eliseev.charm.back.utils.StringUtils.isBlank;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RegistrationValidator implements Validator<RegistrationDto> {

    private final ProfileDao dao = ProfileDao.getInstance();

    private static final RegistrationValidator INSTANCE = new RegistrationValidator();

    public static RegistrationValidator getInstance() {
        return INSTANCE;
    }

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    
    @Override
    public ValidationResult validate(RegistrationDto dto) {
        ValidationResult result = new ValidationResult();
        if (isBlank(dto.getEmail()) || !VALID_EMAIL_ADDRESS_REGEX.matcher(dto.getEmail()).matches()) {
            result.add("error.email.invalid");
        }
        if (!isBlank(dto.getEmail()) && dao.getAllEmails().contains(dto.getEmail())) {
            result.add("error.email.duplicate");
        }
        if (isBlank(dto.getPassword())) {
            result.add("error.password.invalid");
        }
        return result;
    }
}
