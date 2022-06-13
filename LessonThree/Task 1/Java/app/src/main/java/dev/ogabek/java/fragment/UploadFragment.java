package dev.ogabek.java.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;

import com.sangcomz.fishbun.FishBun;
import com.sangcomz.fishbun.adapter.image.impl.GlideAdapter;

import java.util.ArrayList;
import dev.ogabek.java.R;
import dev.ogabek.java.utils.Logger;
import dev.ogabek.java.utils.Utils;

public class UploadFragment extends BaseFragment {

    private static final String TAG = UploadFragment.class.toString();
    FrameLayout fl_photo;
    ImageView iv_photo;
    EditText et_caption;
    Uri pickedPhoto = null;
    ArrayList<Uri> allPhotos = new ArrayList<>();
    UploadListener listener = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upload, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {

        FrameLayout fl_view = view.findViewById(R.id.fl_view);
        setViewHeight(fl_view);
        et_caption = view.findViewById(R.id.et_caption);
        fl_photo = view.findViewById(R.id.fl_photo);
        iv_photo = view.findViewById(R.id.iv_photo);

        ImageView iv_close = view.findViewById(R.id.iv_close);
        ImageView iv_pick = view.findViewById(R.id.iv_pick);
        ImageView iv_upload = view.findViewById(R.id.iv_upload);

        iv_pick.setOnClickListener(view2 -> pickFishBunPhoto());
        iv_close.setOnClickListener(view3-> hidePeckedPhoto());
        iv_upload.setOnClickListener(view4 -> uploadNewPost());

    }

    private void uploadNewPost() {

        listener.scrollToHome();
        String caption = et_caption.getText().toString().trim();
        if (caption.isEmpty() && pickedPhoto != null) {
            Logger.d(TAG, caption);
            Logger.v(TAG, pickedPhoto.getPath());
            resetAll();
        }
    }

    private void resetAll() {
        et_caption.getText().clear();
        pickedPhoto = null;
        fl_photo.setVisibility(View.GONE);
    }

    private void hidePeckedPhoto() {
        pickedPhoto = null;
        fl_photo.setVisibility(View.GONE);
    }

    /**
     * pick photo using FishBun library
     */
    private void pickFishBunPhoto() {

        FishBun.with(this)
                .setImageAdapter(new GlideAdapter())
                .setMaxCount(1)
                .setMinCount(1)
                .setSelectedImages(allPhotos)
                .startAlbumWithActivityResultCallback(photoLauncher);
    }

    /**
     * Set view height as screen width
     */

    private void setViewHeight(View view) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = Utils.screenSize(requireActivity().getApplication()).getWidth();
        view.setLayoutParams(params);
    }

    private ActivityResultLauncher<Intent> photoLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), it -> {
        if (it.getResultCode() == Activity.RESULT_OK) {
            if (it.getData().getParcelableArrayListExtra(FishBun.INTENT_PATH) != null) {
                allPhotos = it.getData().getParcelableArrayListExtra(FishBun.INTENT_PATH);
            } else allPhotos = new ArrayList<>();
            pickedPhoto = allPhotos.get(0);
            showPickedPhoto();
        }
    });

    /*
     * onAttach is for communication of Fragments
     */

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof UploadListener) {
            listener = (UploadListener) context;
        } else {
            throw new RuntimeException("$context must implement UploadListener");
        }
    }

    @Override
    public void onDetach() {
        listener = null;
        super.onDetach();
    }

    /**
     * Set view height as screen width
     */

    private void showPickedPhoto() {
        fl_photo.setVisibility(View.VISIBLE);
        iv_photo.setImageURI(pickedPhoto);
    }

    /**
     * This interface is created for communication with HomeFragment
     */

    public interface UploadListener {
        void scrollToHome();
    }

}