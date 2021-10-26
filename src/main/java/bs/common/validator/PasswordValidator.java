package bs.common.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import bs.common.util.ValidatorUtil;

import java.util.List;

public class PasswordValidator {
    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean isValid(final String password, final String oldPassword) {
        if(ValidatorUtil.isNotEmpty(password) && ValidatorUtil.isNotEmpty(oldPassword)) {
            return passwordEncoder.matches(password, oldPassword);
        }
        return false;
    }

    public boolean isValid(final String password, final List<String> oldPasswords) {
        if(ValidatorUtil.isNotEmpty(password) && !oldPasswords.isEmpty()) {
            return oldPasswords.stream()
                               .anyMatch(item -> passwordEncoder.matches(password, item));
        }
        return false;
    }
}
