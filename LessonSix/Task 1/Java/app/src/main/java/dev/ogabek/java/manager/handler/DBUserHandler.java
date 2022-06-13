package dev.ogabek.java.manager.handler;

import dev.ogabek.java.model.User;

public interface DBUserHandler {
    void onSuccess(User user);
    void onSuccess();
    void onError(Exception exception);
}
