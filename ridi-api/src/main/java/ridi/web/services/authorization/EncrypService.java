package ridi.web.services.authorization;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class EncrypService {

    public @NonNull String salt() {
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    public @NonNull String hashPasswd(@NonNull String passwd, @NonNull String salt)
            throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] saltBytes = Base64.getDecoder().decode(salt);
        byte[] passwdBytes = passwd.getBytes(StandardCharsets.UTF_8);
        byte[] input = new byte[saltBytes.length + passwdBytes.length];

        System.arraycopy(saltBytes, 0, input, 0, saltBytes.length);
        System.arraycopy(passwdBytes, 0, input, saltBytes.length, passwdBytes.length);

        byte[] hash = digest.digest(input);
        StringBuilder hexString = new StringBuilder();

        for (byte b : hash) {
            hexString.append(String.format("%02X", b));
        }

        return hexString.toString();
    }
}
