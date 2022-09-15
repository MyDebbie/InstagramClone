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
import com.example.instagramclone.Post;
import com.example.instagramclone.R;
import com.example.instagramclone.User;
import com.parse.ParseUser;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    Context context;
    List<Post> posts;


    // Pass in the context and list of posts
    public PostsAdapter (Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }


    // For each row, inflate the layout
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
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



    // Clean all elements of the recycler
    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }



    // Add a list of items -- change to type used
    public void addAll(List<Post> posts) {

        posts.addAll(posts);
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivPost_Image;
        TextView tvDescription;
        TextView tvUserName;
        TextView tvTime;
        ImageView ivPost_Image1;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivPost_Image = itemView.findViewById(R.id.ivPost_Image);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvTime = itemView.findViewById(R.id.tvTime);
            ivPost_Image1 = itemView.findViewById(R.id.ivProfile_Image1);


        }
        public void bind(Post post) {
            tvDescription.setText(post.getDescription());
            tvUserName.setText(post.getUser().getUsername());
            tvTime.setText(post.getFormattedTimestamp());


            if (post.getImage() != null){
                Glide.with(context).load(post.getImage().getUrl()).into(ivPost_Image);
            }
            Glide.with(context).load(ParseUser.getCurrentUser().getParseFile(User.KEY_Profile_Image).getUrl()).into(ivPost_Image1);
            
        }


    }
}
