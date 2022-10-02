package com.example.instagramclone.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.instagramclone.Adapter.CommentsAdapter;
import com.example.instagramclone.R;
import com.example.instagramclone.TimeFormatter;
import com.example.instagramclone.models.Comment;
import com.example.instagramclone.models.Post;
import com.example.instagramclone.models.User;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;


import org.json.JSONException;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DetailActivity extends AppCompatActivity {

    public static final String TAG = "DetailActivity";


    RecyclerView rvComments;
    CommentsAdapter adapter;
    List<Comment> comments;
    List<String> Comments;



    ImageView ivPost_Image2;
    TextView tvDescription2;
    TextView tvUserName;
    ImageView ivProfile_Image1;
    TextView tvTime2;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Post post = Parcels.unwrap(getIntent().getParcelableExtra("Post"));
        try {
            Comments = Comment.fromJsonArray(post.getComment());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_left);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rvComments = findViewById(R.id.rvComments);
        comments = new ArrayList<>();
        adapter = new CommentsAdapter(context, comments);

        rvComments.setAdapter(adapter);
        rvComments.setLayoutManager(new LinearLayoutManager(context));



        ivPost_Image2 = findViewById(R.id.ivPost_Image2);
        tvDescription2 = findViewById(R.id.tvDescription2);
        tvTime2 = findViewById(R.id.tvTime2);
        tvUserName = findViewById(R.id.tvUserName);
        ivProfile_Image1 = findViewById(R.id.ivProfile_Image1);



        String timeformat2 = TimeFormatter.getTimeDifference(post.getCreatedAt().toString());





        tvDescription2.setText(post.getDescription());
        tvTime2.setText(timeformat2 + " ago");
        tvUserName.setText(post.getUser().getUsername());

        Glide.with(DetailActivity.this).load(post.getImage().getUrl()).into(ivPost_Image2);
        Glide.with(DetailActivity.this).load(post.getUser().getParseFile(User.KEY_Profile_Image).getUrl()).transform(new CircleCrop()).into(ivProfile_Image1);

        queryPosts();


    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {
        Intent intent = new Intent(DetailActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivityIfNeeded(intent, 0);
        return true;
    }

    protected void queryPosts() {
        ParseQuery<Comment> query = ParseQuery.getQuery(Comment.class);
        query.include(Comment.KEY_User);
        query.addDescendingOrder(Comment.KEY_CREATED_AT);
        query.findInBackground(new FindCallback<Comment>() {
            @Override
            public void done(List<Comment> comments1, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with getting posts", e);
                    return;
                }
                for(Comment post: comments1) {
                    Log.i(TAG, "username: " + post.getUser().getUsername());

                }
                comments.addAll(comments1);
                adapter.notifyDataSetChanged();

            }
        });


    }

}