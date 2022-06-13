package dev.ogabek.java.manager;


import android.annotation.SuppressLint;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

import dev.ogabek.java.manager.handler.DBUserHandler;
import dev.ogabek.java.manager.handler.DBUsersHandler;
import dev.ogabek.java.model.User;

public class DatabaseManager {

    private static final String USER_PATH = "users";
    private static final String POST_PATH = "posts";
    private static final String FEED_PATH = "feeds";
    private static final String FOLLOWING_PATH = "following";
    private static final String FOLLOWERS_PATH = "followers";

    @SuppressLint("StaticFieldLeak")
    private static final FirebaseFirestore database = FirebaseFirestore.getInstance();

    public static void storeUser(User user, DBUserHandler handler) {
        database.collection(USER_PATH).document(user.getUid()).set(user).addOnSuccessListener(unused -> {
            handler.onSuccess();
        }).addOnFailureListener(handler::onError);
    }

    public static void loadUser(String uid, DBUserHandler handler) {
        database.collection(USER_PATH).document(uid).get().addOnCompleteListener(task -> {
            if (task.getResult().exists()) {
                String fullname = task.getResult().getString("fullname");
                String email = task.getResult().getString("email");
                String userImg = task.getResult().getString("userImg");

                User user = new User(fullname, email, userImg);
                user.setUid(uid);
                handler.onSuccess(user);
            } else {
                handler.onSuccess();
            }
        }).addOnFailureListener(handler::onError);
    }

    public static void updateUserImage(String userImg) {
        String uid = AuthManager.currentUser().getUid();
        database.collection(USER_PATH).document(uid).update("userImg", userImg);
    }

    public static void loadUsers(DBUsersHandler handler) {
        database.collection(USER_PATH).get().addOnCompleteListener(task -> {
            ArrayList<User> users = new ArrayList<>();
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String uid = document.getString("uid");
                    String fullName = document.getString("fullname");
                    String email = document.getString("email");
                    String userImg = document.getString("userImg");
                    User user = new User(fullName, email, userImg);
                    user.setUid(uid);
                    users.add(user);
                }
                handler.onSuccess(users);
            } else {
                handler.onError(task.getException());
            }
        });
    }

}
