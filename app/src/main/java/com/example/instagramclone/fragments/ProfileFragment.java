package com.example.instagramclone.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.instagramclone.Adapter.ProfileAdapter;
import com.example.instagramclone.LoginActivity;
import com.example.instagramclone.Post;
import com.example.instagramclone.R;
import com.example.instagramclone.User;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {


    private static final String TAG = "ProfileFragment";

    RecyclerView rvUsers;
    ProfileAdapter adapter;
    List<Post> posts;
    Button btnLogout;
    TextView tvUserName1;
    ImageView ivProfile_Image;

    public ProfileFragment(){}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvUsers = view.findViewById(R.id.rvUsers);
        btnLogout = view.findViewById(R.id.btnLogout);
        tvUserName1 = view.findViewById(R.id.tvUserName1);
        ivProfile_Image = view.findViewById(R.id.ivProfile_Image);


        posts = new ArrayList<>();
        adapter = new ProfileAdapter(getContext(), posts);

        rvUsers.setAdapter(adapter);
        tvUserName1.setText(ParseUser.getCurrentUser().getUsername());
        Glide.with(getContext()).load(ParseUser.getCurrentUser().getParseFile(User.KEY_Profile_Image).getUrl()).into(ivProfile_Image);


        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvUsers.setLayoutManager(layoutManager);
        queryPosts();



        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser.logOut();
                ParseUser currentUser = ParseUser.getCurrentUser();
                goLoginActivity();

            }
        });

    }


    protected void queryPosts() {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_User);
        query.whereEqualTo(Post.KEY_User, ParseUser.getCurrentUser());
        query.setLimit(20);
        query.addDescendingOrder(Post.KEY_CREATED_AT);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts1, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with getting posts", e);
                    return;
                }
                for(Post post: posts1) {
                    Log.i(TAG, "Post: " + post.getDescription() + ", username: " + post.getUser().getUsername());

                }
                posts.addAll(posts1);
                adapter.notifyDataSetChanged();

            }
        });
    }



    private void goLoginActivity() {

        Intent i = new Intent(getActivity(), LoginActivity.class);
        startActivity(i);
        ((Activity) getActivity()).overridePendingTransition(0, 0);

    }


}
