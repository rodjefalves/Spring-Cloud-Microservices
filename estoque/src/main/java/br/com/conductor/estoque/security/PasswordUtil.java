package br.com.conductor.estoque.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


public class PasswordUtil {

    public static final String STRENGTH_PATTERN = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,30}";

    private static PasswordEncoder encoder = new BCryptPasswordEncoder();

    public static String encode(String password) {
        validateStrength(password);
        return encoder.encode(password);
    }

    public static Boolean validate(String rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }

    public static void validateStrength(String password) {
        if (!password.matches(STRENGTH_PATTERN))
            throw new PasswordStrengthException();
    }
}