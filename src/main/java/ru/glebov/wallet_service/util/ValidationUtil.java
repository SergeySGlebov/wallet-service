package ru.glebov.wallet_service.util;

public class ValidationUtil {

    private ValidationUtil() {
    }

    public static void checkNotFoundWithId(boolean found, int id) {
        checkNotFound(found, "id=" + id);
    }

    public static <T> T checkNotFoundWithId(T object, int id) {
        checkNotFoundWithId(object != null, id);
        return object;
    }

    public static void checkNotFound(boolean found, String msg) {
        if (!found) {
            throw new IllegalArgumentException("Not found entity with " + msg);
        }
    }

}
