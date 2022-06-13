package dev.ogabek.java.manager;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import dev.ogabek.java.manager.handler.StorageHandler;

public class StorageManager {

    private static final String USER_PHOTO_PATH = "users";
    private static final String POST_PHOTO_PATH = "posts";

    private static final FirebaseStorage storage = FirebaseStorage.getInstance();
    private static final StorageReference storageRef = storage.getReference();

    public static void uploadUserPhoto(Uri uri, StorageHandler handler) {
        String filename = AuthManager.currentUser().getUid() + ".png";
        UploadTask uploadTask = storageRef.child(USER_PHOTO_PATH).child(filename).putFile(uri);
        uploadTask.addOnSuccessListener(taskSnapshot -> {
            Task<Uri> result = taskSnapshot.getMetadata().getReference().getDownloadUrl();
            result.addOnSuccessListener(it -> {
                String imgUrl = it.toString();
                handler.onSuccess(imgUrl);
            }).addOnFailureListener(handler::onError);
        }).addOnFailureListener(handler::onError);
    }

}
