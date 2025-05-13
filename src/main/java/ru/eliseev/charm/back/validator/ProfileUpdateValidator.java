package ru.eliseev.charm.back.validator;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.eliseev.charm.back.dto.ProfileUpdateDto;

import static ru.eliseev.charm.back.utils.DateTimeUtils.isValidAge;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProfileUpdateValidator implements Validator<ProfileUpdateDto> {

    private static final ProfileUpdateValidator INSTANCE = new ProfileUpdateValidator();

    public static ProfileUpdateValidator getInstance() {
        return INSTANCE;
    }

    @Override
    public ValidationResult validate(ProfileUpdateDto dto) {
        ValidationResult result = new ValidationResult();
        if (dto.getBirthDate() != null && !isValidAge(dto.getBirthDate())) {
            result.add("error.age.invalid");
        }
        return result;
    }
}
