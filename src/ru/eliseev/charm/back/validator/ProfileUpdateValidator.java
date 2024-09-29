package ru.eliseev.charm.back.validator;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.eliseev.charm.back.dao.ProfileDao;
import ru.eliseev.charm.back.dto.ProfileUpdateDto;

import java.util.regex.Pattern;

import static ru.eliseev.charm.back.utils.DateTimeUtils.getAge;
import static ru.eliseev.charm.back.utils.StringUtils.isBlank;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProfileUpdateValidator implements Validator<ProfileUpdateDto> {

    private final ProfileDao dao = ProfileDao.getInstance();

    private static final ProfileUpdateValidator INSTANCE = new ProfileUpdateValidator();

    public static ProfileUpdateValidator getInstance() {
        return INSTANCE;
    }

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    @Override
    public ValidationResult validate(ProfileUpdateDto dto) {
        ValidationResult result = new ValidationResult();
        if (!isBlank(dto.getEmail())) {
            if (isBlank(dto.getEmail()) || !VALID_EMAIL_ADDRESS_REGEX.matcher(dto.getEmail()).matches()) {
                result.add("error.email.invalid");
            }
            if (dao.getAllEmails().contains(dto.getEmail())) {
                result.add("error.email.duplicate");
            }
        }
        if (dto.getBirthDate() != null) {
            int age = getAge(dto.getBirthDate());
            if (age < 18 || age > 100) {
                result.add("error.age.invalid");
            }
        }
        return result;
    }
}
