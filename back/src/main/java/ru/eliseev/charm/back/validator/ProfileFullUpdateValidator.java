package ru.eliseev.charm.back.validator;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.eliseev.charm.back.dao.ProfileDao;
import ru.eliseev.charm.back.dto.ProfileFullUpdateDto;

import static ru.eliseev.charm.utils.DateTimeUtils.isValidAge;
import static ru.eliseev.charm.utils.StringUtils.isValidEmail;
import static ru.eliseev.charm.utils.StringUtils.isValidPassword;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProfileFullUpdateValidator implements Validator<ProfileFullUpdateDto> {

	private final ProfileDao dao = ProfileDao.getInstance();

    private static final ProfileFullUpdateValidator INSTANCE = new ProfileFullUpdateValidator();

    public static ProfileFullUpdateValidator getInstance() {
        return INSTANCE;
    }

    @Override
    public ValidationResult validate(ProfileFullUpdateDto dto) {
        ValidationResult result = new ValidationResult();
        if (dto.getPassword() != null && !isValidPassword(dto.getPassword())) {
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
        if (dto.getBirthDate() != null && !isValidAge(dto.getBirthDate())) {
            result.add("error.age.invalid");
        }
        return result;
    }
}
