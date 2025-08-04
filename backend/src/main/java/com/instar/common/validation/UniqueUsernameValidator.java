package com.instar.common.validation;//package com.instar.validation;
//
//import jakarta.validation.ConstraintValidator;
//import jakarta.validation.ConstraintValidatorContext;
//import org.springframework.beans.factory.annotation.Autowired;
//import com.instar.repository.UserRepository;
//
//public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Override
//    public boolean isValid(String username, ConstraintValidatorContext context) {
//        if (username == null) return true;
//        return !userRepository.existsByUsername(username);
//    }
//}
