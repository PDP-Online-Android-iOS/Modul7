package dev.ogabek.java.manager.handler;

public interface StorageHandler {
    void onSuccess(String imageUrl);
    void onError(Exception exception);
}
