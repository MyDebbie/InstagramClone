package com.example.instagramclone.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.instagramclone.Adapter.CommentsAdapter;
import com.example.instagramclone.Adapter.ProfileAdapter;
import com.example.instagramclone.R;
import com.example.instagramclone.TimeFormatter;
import com.example.instagramclone.models.Comment;
import com.example.instagramclone.models.Post;
import com.example.instagramclone.models.User;

import org.json.JSONException;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String TAG = "DetailActivity";


    RecyclerView rvComments;
    CommentsAdapter adapter;
    List<Comment> comments;
    List<String> Comments;



    ImageView ivPost_Image2;
    TextView tvDescription2;
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


        rvComments = findViewById(R.id.rvComments);

        ivPost_Image2 = findViewById(R.id.ivPost_Image2);
        tvDescription2 = findViewById(R.id.tvDescription2);
        tvTime2 = findViewById(R.id.tvTime2);

        comments = new ArrayList<>();
        adapter = new CommentsAdapter(context, comments);

        rvComments.setAdapter(adapter);
        rvComments.setLayoutManager(new LinearLayoutManager(context));



        String timeformat2 = TimeFormatter.getTimeDifference(post.getCreatedAt().toString());





        tvDescription2.setText(post.getDescription());
        tvTime2.setText(timeformat2 + " ago");

        Glide.with(context).load(post.getImage().getUrl()).into(ivPost_Image2);


    }
}