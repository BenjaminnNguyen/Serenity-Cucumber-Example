package singleton;

import java.util.Base64;

public class Utilities {

    public static String PasswordUnmasking(String password) {
        byte[] decodedBytes = Base64.getDecoder().decode(password);
        String decodedString = new String(decodedBytes);
        return decodedString;
    }
}
