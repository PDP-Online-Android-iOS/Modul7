package dev.ogabek.java.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import dev.ogabek.java.R;
import dev.ogabek.java.adapter.SearchAdapter;
import dev.ogabek.java.manager.AuthManager;
import dev.ogabek.java.manager.DatabaseManager;
import dev.ogabek.java.manager.handler.DBFollowHandler;
import dev.ogabek.java.manager.handler.DBUserHandler;
import dev.ogabek.java.manager.handler.DBUsersHandler;
import dev.ogabek.java.model.User;
import dev.ogabek.java.utils.Logger;

public class SearchFragment extends BaseFragment {

    private static final String TAG = SearchFragment.class.toString();
    RecyclerView recyclerView;
    ArrayList<User> items = new ArrayList<>();
    ArrayList<User> users = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return initViews(inflater.inflate(R.layout.fragment_search, container, false));
    }

    private View initViews(View view) {

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
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
        String uid = AuthManager.currentUser().getUid();
        DatabaseManager.loadUsers(new DBUsersHandler() {
            @Override
            public void onSuccess(ArrayList<User> users) {
                DatabaseManager.loadFollowing(uid, new DBUsersHandler() {
                    @Override
                    public void onSuccess(ArrayList<User> following) {
                        items.clear();
                        items.addAll(mergedUsers(uid, users, following));
                        refreshAdapter(items);
                    }

                    @Override
                    public void onError(Exception exception) {

                    }
                });
            }

            @Override
            public void onError(Exception exception) {

            }
        });
    }

    private void refreshAdapter(ArrayList<User> items) {
        SearchAdapter adapter = new SearchAdapter(this, items);
        recyclerView.setAdapter(adapter);

    }

    private ArrayList<User> mergedUsers(String uid, ArrayList<User> users, ArrayList<User> following) {
        ArrayList<User> items = new ArrayList<>();

        for (User u: users) {
            User user = u;
            for (User f: following) {
                if (u.getUid().equals(f.getUid())) {
                    user.setFollowed(true);
                    break;
                }
            }
            if (!uid.equals(user.getUid())) {
                items.add(user);
            }
        }

        return items;
    }

    public void onFollowOrUnfollow(User to) {
        String uid = AuthManager.currentUser().getUid();
        if (!to.isFollowed()) {
            followUser(uid, to);
        } else {
            unFollowUser(uid, to);
        }
    }

    private void unFollowUser(String uid, User to) {
        DatabaseManager.loadUser(uid, new DBUserHandler() {
            @Override
            public void onSuccess(User me) {
                DatabaseManager.unFollowUser(me, to, new DBFollowHandler() {
                    @Override
                    public void onSuccess(boolean isFollowed) {
                        to.setFollowed(false);
                        DatabaseManager.removePostsFromMyFeed(uid, to);
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
            }

            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(Exception exception) {

            }
        });
    }

    private void followUser(String uid, User to) {
        DatabaseManager.loadUser(uid, new DBUserHandler() {
            @Override
            public void onSuccess(User me) {
                DatabaseManager.followUser(me, to, new DBFollowHandler() {
                    @Override
                    public void onSuccess(boolean isFollowed) {
                        to.setFollowed(false);
                        DatabaseManager.storePostsFromMyFeed(uid, to);
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
            }

            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(Exception exception) {

            }
        });
    }

}