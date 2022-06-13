package dev.ogabek.java.manager.handler;

public interface AuthHandler {
    void onSuccess(String uid);
    void onError(Exception exception);
}
