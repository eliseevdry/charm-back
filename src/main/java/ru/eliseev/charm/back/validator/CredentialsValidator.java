package ru.eliseev.charm.back.validator;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.eliseev.charm.back.dao.ProfileDao;
import ru.eliseev.charm.back.dto.CredentialsDto;
import ru.eliseev.charm.back.model.Profile;

import static ru.eliseev.charm.back.utils.StringUtils.isValidEmail;
import static ru.eliseev.charm.back.utils.StringUtils.isValidPassword;

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
        if (dto.getEmail() != null) {
            if (!isValidEmail(dto.getEmail())) {
                result.add("error.email.invalid");
            }
            if (dao.existByEmail(dto.getId(), dto.getEmail())) {
                result.add("error.email.exist");
            }
        }
        if (!isValidPassword(dto.getNewPassword()) && !dto.getNewPassword().equals(dto.getConfirmNewPassword())) {
            result.add("error.password.invalid");
        }
        return result;
    }
}
