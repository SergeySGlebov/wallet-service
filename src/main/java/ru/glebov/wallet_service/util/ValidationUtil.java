package ru.glebov.wallet_service.util;

import org.springframework.core.NestedExceptionUtils;
import org.springframework.lang.NonNull;
import ru.glebov.wallet_service.util.exception.NotFoundException;

import java.util.UUID;

public class ValidationUtil {

    private ValidationUtil() {
    }

    public static void checkNotFoundWithId(boolean found, UUID id) {
        checkNotFound(found, "id=" + id);
    }

    public static <T> T checkNotFoundWithId(T object, UUID id) {
        checkNotFoundWithId(object != null, id);
        return object;
    }

    public static void checkNotFound(boolean found, String msg) {
        if (!found) {
            throw new NotFoundException("Не найден кошелек с " + msg);
        }
    }

    @NonNull
    public static Throwable getRootCause(@NonNull Throwable t) {
        Throwable rootCause = NestedExceptionUtils.getRootCause(t);
        return rootCause != null ? rootCause : t;
    }
}
