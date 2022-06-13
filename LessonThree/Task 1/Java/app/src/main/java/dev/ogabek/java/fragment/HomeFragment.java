package dev.ogabek.java.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import dev.ogabek.java.R;

public class HomeFragment extends BaseFragment {

    RecyclerView recyclerView;
    HomeListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        initViews(view);

        return view;
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        ImageView iv_camera = view.findViewById(R.id.iv_camera);
        iv_camera.setOnClickListener( view1 -> listener.scrollToUpload());

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof HomeListener){
            listener = (HomeListener) context;
        } else {
            throw new RuntimeException("$context must implement HomeListener");
        }
    }

    @Override
    public void onDetach() {
        listener = null;
        super.onDetach();
    }

    /**
     * This interface is created for communication with UploadFragment
     */
    public interface HomeListener {
        void scrollToUpload();
    }

}