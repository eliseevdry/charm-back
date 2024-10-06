package ru.eliseev.charm.back.validator;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.eliseev.charm.back.dao.ProfileDao;
import ru.eliseev.charm.back.dto.CredentialsDto;
import ru.eliseev.charm.back.model.Profile;

import static ru.eliseev.charm.back.utils.StringUtils.VALID_EMAIL_ADDRESS_REGEX;
import static ru.eliseev.charm.back.utils.StringUtils.isBlank;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CredentialsValidator implements Validator<CredentialsDto> {

    private final ProfileDao dao = ProfileDao.getInstance();

    private static final CredentialsValidator INSTANCE = new CredentialsValidator();

    public static CredentialsValidator getInstance() {
        return INSTANCE;
    }

    @Override
    public ValidationResult validate(CredentialsDto dto) {
        ValidationResult result = new ValidationResult();
        Profile profile = dao.findById(dto.getId()).orElseThrow();
        if (!dto.getCurrentPassword().equals(profile.getPassword())) {
            result.add("error.password.invalid");
        }
        if (!isBlank(dto.getEmail())) {
            if (!VALID_EMAIL_ADDRESS_REGEX.matcher(dto.getEmail()).matches()) {
                result.add("error.email.invalid");
            }
            if (dao.getAllEmails().contains(dto.getEmail())) {
                result.add("error.email.exist");
            }
        }
        if (!isBlank(dto.getNewPassword()) && !dto.getNewPassword().equals(dto.getConfirmNewPassword())) {
            result.add("error.password.invalid");
        }
        return result;
    }
}
