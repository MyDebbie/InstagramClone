package com.example.instagramclone.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.instagramclone.R;
import com.example.instagramclone.models.Comment;
import com.example.instagramclone.models.Post;
import com.example.instagramclone.models.User;

import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<com.example.instagramclone.Adapter.CommentsAdapter.ViewHolder>{

        Context context;
        List<Comment> comments;


        // Pass in the context and list of posts
        public CommentsAdapter (Context context, List<Comment> posts) {
            this.context = context;
            this.comments = comments;

        }


        // For each row, inflate the layout
        @NonNull
        @Override
        public com.example.instagramclone.Adapter.CommentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false);
            return new com.example.instagramclone.Adapter.CommentsAdapter.ViewHolder(view);
        }


        // Bind values based on the position of the element
        @Override
        public void onBindViewHolder(@NonNull com.example.instagramclone.Adapter.CommentsAdapter.ViewHolder holder, int position) {

            // Get the data at position
            Comment comment = comments.get(position);
            // Bind the tweet with view holder
            holder.bind(comment);

        }

        @Override
        public int getItemCount() { return comments.size(); }

        public class ViewHolder extends RecyclerView.ViewHolder {

            ImageView ivProfile_Image2;
            TextView tvUserName2;
            TextView tvComment;


            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                ivProfile_Image2 = itemView.findViewById(R.id.ivProfile_Image2);
                tvUserName2 = itemView.findViewById(R.id.tvUserName2);
                tvComment = itemView.findViewById(R.id.tvComment);

            }
            public void bind(Comment comment) {


                tvUserName2.setText(comment.getUser().getUsername());
                tvComment.setText(comment.getComments().toString());
                Glide.with(context).load(comment.getUser().getParseFile(User.KEY_Profile_Image).getUrl()).transform(new CircleCrop()).into(ivProfile_Image2);

            }

        }

    }
