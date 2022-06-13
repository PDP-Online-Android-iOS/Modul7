package dev.ogabek.java.manager;


import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

import dev.ogabek.java.manager.handler.DBFollowHandler;
import dev.ogabek.java.manager.handler.DBPostHandler;
import dev.ogabek.java.manager.handler.DBPostsHandler;
import dev.ogabek.java.manager.handler.DBUserHandler;
import dev.ogabek.java.manager.handler.DBUsersHandler;
import dev.ogabek.java.model.Post;
import dev.ogabek.java.model.User;

public class DatabaseManager {

    private static final String USER_PATH = "users";
    private static final String POST_PATH = "posts";
    private static final String FEED_PATH = "feeds";
    private static final String FOLLOWING_PATH = "following";
    private static final String FOLLOWERS_PATH = "followers";

    private static FirebaseFirestore database = FirebaseFirestore.getInstance();

    public static void storeUser(User user, DBUserHandler handler) {
        database.collection(USER_PATH).document(user.getUid()).set(user).addOnSuccessListener(unused -> {
            handler.onSuccess();
        }).addOnFailureListener(handler::onError);
    }

    public static void loadUser(String uid, DBUserHandler handler) {
        database.collection(USER_PATH).document(uid).get().addOnCompleteListener(it -> {
            if (it.getResult().exists()) {
                String fullname = it.getResult().getString("fullname");
                String email = it.getResult().getString("email");
                String userImg = it.getResult().getString("userImg");

                User user = new User(fullname, email, userImg);
                user.setUid(uid);
                handler.onSuccess(user);
            } else {
                handler.onSuccess(null);
            }
        }).addOnFailureListener(handler::onError);
    }

    public static void updateUserImage(String userImg) {
        String uid = AuthManager.currentUser().getUid();
        database.collection(USER_PATH).document(uid).update("userImg", userImg);
    }

    public static void loadUsers(DBUsersHandler handler) {
        database.collection(USER_PATH).get().addOnCompleteListener(it -> {
            ArrayList<User> users = new ArrayList<>();
            if (it.isSuccessful()) {
                for (DocumentSnapshot document : it.getResult()) {
                    String uid = document.getString("uid");
                    String fullname = document.getString("fullname");
                    String email = document.getString("email");
                    String userImg = document.getString("userImg");
                    User user = new User(fullname, email, userImg);
                    user.setUid(uid);
                    users.add(user);
                }
                handler.onSuccess(users);
            } else {
                handler.onError(it.getException());
            }
        }).addOnFailureListener(handler::onError);
    }

    public static void storePosts(Post post, DBPostHandler handler) {
        CollectionReference reference = database.collection(USER_PATH).document(post.getUid()).collection(POST_PATH);
        String id = reference.document().getId();
        post.setId(id);

        reference.document(post.getId()).set(post).addOnSuccessListener(unused -> {
            handler.onSuccess(post);
        }).addOnFailureListener(handler::onError);
    }

    public static void loadPosts(String uid, DBPostsHandler handler) {
        CollectionReference reference = database.collection(USER_PATH).document(uid).collection(POST_PATH);
        reference.get().addOnCompleteListener(it -> {
            ArrayList<Post> posts = new ArrayList<>();
            if (it.isSuccessful()) {
                for (QueryDocumentSnapshot document : it.getResult()) {
                    String id = document.getString("id");
                    String caption = document.getString("caption");
                    String postImg = document.getString("postImg");
                    String fullname = document.getString("fullname");
                    String userImg = document.getString("userImg");

                    Post post = new Post(id, caption, postImg);
                    post.setUid(uid);
                    post.setFullname(fullname);
                    post.setUserImg(userImg);
                    posts.add(post);
                }
                handler.onSuccess(posts);
            } else {
                handler.onError(it.getException());
            }
        });
    }

    public static void storeFeeds(Post post, DBPostHandler handler) {
        CollectionReference reference = database.collection(USER_PATH).document(post.getUid()).collection(FEED_PATH);

        reference.document(post.getId()).set(post).addOnSuccessListener(unused -> {
            handler.onSuccess(post);
        }).addOnFailureListener(handler::onError);
    }

    public static void loadFeeds(String uid, DBPostsHandler handler) {
        CollectionReference reference = database.collection(USER_PATH).document(uid).collection(FEED_PATH);
        reference.get().addOnCompleteListener(it -> {
            ArrayList<Post> posts = new ArrayList<>();
            if (it.isSuccessful()) {
                for (QueryDocumentSnapshot document : it.getResult()) {
                    String id = document.getString("id");
                    String caption = document.getString("caption");
                    String postImg = document.getString("postImg");
                    String fullname = document.getString("fullname");
                    String userImg = document.getString("userImg");

                    Post post = new Post(id, caption, postImg);
                    post.setUid(uid);
                    post.setFullname(fullname);
                    post.setUserImg(userImg);
                    posts.add(post);
                }
                handler.onSuccess(posts);
            } else {
                handler.onError(it.getException());
            }
        });
    }

    public static void storePostsFromMyFeed(String uid, User to) {
        loadPosts(to.getUid(), new DBPostsHandler() {
            @Override
            public void onSuccess(ArrayList<Post> posts) {
                for (Post post : posts) {
                    storeFeed(uid, post);
                }
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    public static void storeFeed(String uid, Post post) {
        CollectionReference reference = database.collection(USER_PATH).document(uid).collection(FEED_PATH);
        reference.document(post.getId()).set(post);
    }

    public static void removePostsFromMyFeed(String uid, User to) {
        loadPosts(to.getUid(), new DBPostsHandler() {
            @Override
            public void onSuccess(ArrayList<Post> posts) {
                for (Post post : posts) {
                    removeFeed(uid, post);
                }
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    public static void removeFeed(String uid, Post post) {
        CollectionReference reference = database.collection(USER_PATH).document(uid).collection(FEED_PATH);
        reference.document(post.getId()).delete();
    }

    public static void loadFollowing(String uid, DBUsersHandler handler) {
        database.collection(USER_PATH).document(uid).collection(FOLLOWING_PATH).get().addOnCompleteListener(it -> {
            ArrayList<User> users = new ArrayList<>();
            if (it.isSuccessful()) {
                for (DocumentSnapshot document : it.getResult()) {
                    String uId = document.getString("uid");
                    String fullname = document.getString("fullname");
                    String email = document.getString("email");
                    String userImg = document.getString("userImg");
                    User user = new User(fullname, email, userImg);

                    user.setUid(uId);
                    users.add(user);
                }
                handler.onSuccess(users);
            } else {
                handler.onError(it.getException());
            }
        });
    }

    public static void loadFollowers(String uid, DBUsersHandler handler) {
        database.collection(USER_PATH).document(uid).collection(FOLLOWERS_PATH).get().addOnCompleteListener(it -> {
            ArrayList<User> users = new ArrayList<>();
            if (it.isSuccessful()) {
                for (DocumentSnapshot document : it.getResult()) {
                    String uId = document.getString("uid");
                    String fullname = document.getString("fullname");
                    String email = document.getString("email");
                    String userImg = document.getString("userImg");
                    User user = new User(fullname, email, userImg);

                    user.setUid(uId);
                    users.add(user);
                }
                handler.onSuccess(users);
            } else {
                handler.onError(it.getException());
            }
        });
    }

    public static void followUser(User me, User to, DBFollowHandler handler) {
        // User (to) is in my following
        database.collection(USER_PATH).document(me.getUid()).collection(FOLLOWING_PATH).document(to.getUid()).set(to).addOnSuccessListener(it -> {
            // User (Me) is in his/her followers
            database.collection(USER_PATH).document(to.getUid()).collection(FOLLOWERS_PATH).document(me.getUid()).set(me).addOnSuccessListener(unused -> {
                handler.onSuccess(true);
            }).addOnFailureListener(handler::onError);
        }).addOnFailureListener(handler::onError);
    }


    public static void unFollowUser(User me, User to, DBFollowHandler handler) {
        // User(To) is in my following
        database.collection(USER_PATH).document(me.getUid()).collection(FOLLOWING_PATH).document(to.getUid()).delete().addOnSuccessListener(unused ->  {
            database.collection(USER_PATH).document(to.getUid()).collection(FOLLOWERS_PATH).document(me.getUid()).delete().addOnSuccessListener(unused2 -> {
                handler.onSuccess(true);
            }).addOnFailureListener(handler::onError);
        }).addOnFailureListener(handler::onError);
    }
}
