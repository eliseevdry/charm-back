package ru.eliseev.charm.back.validator;

import ru.eliseev.charm.back.dto.ProfileUpdateDto;

import static ru.eliseev.charm.utils.DateTimeUtils.isValidAge;

public class ProfileUpdateValidator implements Validator<ProfileUpdateDto> {

    @Override
    public ValidationResult validate(ProfileUpdateDto dto) {
        ValidationResult result = new ValidationResult();
        if (dto.getBirthDate() != null && !isValidAge(dto.getBirthDate())) {
            result.add("error.age.invalid");
        }
        return result;
    }
}
