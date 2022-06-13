package dev.ogabek.java.manager.handler;

import java.util.ArrayList;

import dev.ogabek.java.model.User;

public interface DBUsersHandler {
    void onSuccess(ArrayList<User> users);
    void onError(Exception exception);
}
