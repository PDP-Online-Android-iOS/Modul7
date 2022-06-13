package dev.ogabek.java.manager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import dev.ogabek.java.manager.handler.AuthHandler;

public class AuthManager {

    private static FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    public static boolean isSignedIn() {
        return currentUser() != null;
    }

    public static FirebaseUser currentUser() {
        return firebaseAuth.getCurrentUser();
    }

    public static void signIn(String email, String password, AuthHandler handler) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener( task -> {
            if (task.isSuccessful()) {
                handler.onSuccess(currentUser().getUid());
            } else {
                handler.onError(task.getException());
            }
        });
    }

    public static void signUp(String email, String password, AuthHandler handler) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener( task -> {
            if (task.isSuccessful()) {
                handler.onSuccess(currentUser().getUid());
            } else {
                handler.onError(task.getException());
            }
        });
    }

    public static void signOut() {
        firebaseAuth.signOut();
    }
}
