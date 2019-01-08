package pl.kfrak.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * UserDetailsService = Core interface which loads user-specific data.
 * <p>
 * Validator =  validator for application-specific objects. This interface is totally divorced from
 * any infrastructure or context.
 */

@Service
public class UserLoginValidator implements UserDetailsService, Validator {

    @Autowired
    private User user;

    @Override
    public UserDetails loadUserByUsername(String userName) {
        if (user.getUsername().equalsIgnoreCase(userName)) {
            return (UserDetails) user;
        } else {
            throw new UsernameNotFoundException("username not found");
        }
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object user, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "field.required");
        if (((UserDTO) user).getUsername().trim().length() < 2 || ((UserDTO) user).getUsername().trim().length() > 10) {
            errors.rejectValue("username", "login.field.min.max.length");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "field.required");
        if (((UserDTO) user).getPassword().trim().length() < 2 || ((UserDTO) user).getPassword().trim().length() > 10) {
            errors.rejectValue("password", "password.field.min.max.length");
        }
    }
}