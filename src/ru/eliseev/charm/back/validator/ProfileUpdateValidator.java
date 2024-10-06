package ru.eliseev.charm.back.validator;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.eliseev.charm.back.dao.ProfileDao;
import ru.eliseev.charm.back.dto.ProfileUpdateDto;

import static ru.eliseev.charm.back.utils.DateTimeUtils.getAge;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProfileUpdateValidator implements Validator<ProfileUpdateDto> {

    private final ProfileDao dao = ProfileDao.getInstance();

    private static final ProfileUpdateValidator INSTANCE = new ProfileUpdateValidator();

    public static ProfileUpdateValidator getInstance() {
        return INSTANCE;
    }

    @Override
    public ValidationResult validate(ProfileUpdateDto dto) {
        ValidationResult result = new ValidationResult();
        if (dto.getBirthDate() != null) {
            int age = getAge(dto.getBirthDate());
            if (age < 18 || age > 100) {
                result.add("error.age.invalid");
            }
        }
        return result;
    }
}
