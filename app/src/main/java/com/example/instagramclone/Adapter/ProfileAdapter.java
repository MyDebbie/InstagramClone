package com.example.instagramclone.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.instagramclone.models.Post;
import com.example.instagramclone.R;

import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder> {

    Context context;
    List<Post> posts;


    // Pass in the context and list of posts
    public ProfileAdapter (Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;

    }


    // For each row, inflate the layout
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.post, parent, false);
        return new ViewHolder(view);
    }


    // Bind values based on the position of the element
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // Get the data at position
        Post post = posts.get(position);
        // Bind the tweet with view holder
        holder.bind(post);

    }

    @Override
    public int getItemCount() { return posts.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivPost_Image1;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivPost_Image1 = itemView.findViewById(R.id.ivPost_Image1);

        }
        public void bind(Post post) {


                Glide.with(context).load(post.getImage().getUrl()).into(ivPost_Image1);

        }

    }
}

