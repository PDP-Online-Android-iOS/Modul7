package dev.ogabek.java.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;

import dev.ogabek.java.R;
import dev.ogabek.java.adapter.SearchAdapter;
import dev.ogabek.java.model.User;

public class SearchFragment extends BaseFragment {

    private static final String TAG = SearchFragment.class.toString();
    RecyclerView recyclerView;
    ArrayList<User> items = new ArrayList<>();
    ArrayList<User> users = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return initViews(inflater.inflate(R.layout.fragment_search, container, false));
    }

    private View initViews(View view) {

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        EditText et_search = view.findViewById(R.id.et_search);
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String keyword = charSequence.toString().trim();
                userByKeyword(keyword);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        loadUsers();

        return view;
    }
    private void userByKeyword(String keyword) {
        if (keyword.isEmpty()) refreshAdapter(items);

        users.clear();
        for (User user: items) {
            if (user.getFullname().startsWith(keyword))
                users.add(user);
        }
        refreshAdapter(users);
    }

    private void loadUsers() {

    }

    private void refreshAdapter(ArrayList<User> items) {
        SearchAdapter adapter = new SearchAdapter(this, items);
        recyclerView.setAdapter(adapter);

    }

}