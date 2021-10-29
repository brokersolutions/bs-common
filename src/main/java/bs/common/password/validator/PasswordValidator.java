package bs.common.password.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import bs.common.util.ValidatorUtil;

import java.util.List;

@Service
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