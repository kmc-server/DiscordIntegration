package nl.bramkoene.discordintegration.discord;

import net.dv8tion.jda.api.entities.User;
import org.apache.commons.lang.RandomStringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AccountLinker {
    private static HashMap<User, String> verifyCodes = new HashMap<>();

    public static String generateVerifyCode(User user){
        String generatedString = RandomStringUtils.randomAlphanumeric(10);

        verifyCodes.put(user, generatedString);
        return generatedString;
    }

    public static User getUserFromCode(String code){
        return getKeyByValue(verifyCodes, code);
    }

    public static boolean verifyVerifyCode(User user, String code){
        return verifyCodes.get(user) == code;
    }

    public static boolean validVerifyCode(String code){
        return verifyCodes.containsValue(code);
    }

    public static <T, E> T getKeyByValue(Map<T, E> map, E value) {
        for (Map.Entry<T, E> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }
}
