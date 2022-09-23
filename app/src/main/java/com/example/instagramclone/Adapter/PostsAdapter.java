package com.example.instagramclone.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.RoundedCorner;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.instagramclone.Activity.CommentActivity;
import com.example.instagramclone.Activity.DetailActivity;
import com.example.instagramclone.Activity.MainActivity;
import com.example.instagramclone.models.Post;
import com.example.instagramclone.R;
import com.example.instagramclone.TimeFormatter;
import com.example.instagramclone.models.User;

import org.parceler.Parcels;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    Context context;
    List<Post> posts;
    public static int like;
    public static List<String> likers;


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
        ImageView ivProfile_Image1;
        ImageView ivheart;
        ImageView ivcomment;
        TextView tvNumLikes;
        TextView tvComment;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivPost_Image = itemView.findViewById(R.id.ivPost_Image);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvTime = itemView.findViewById(R.id.tvTime);
            ivProfile_Image1 = itemView.findViewById(R.id.ivProfile_Image1);
            ivheart = itemView.findViewById(R.id.ivheart);
            ivcomment = itemView.findViewById(R.id.ivcomment);
            tvNumLikes = itemView.findViewById(R.id.tvNumLikes);
            tvComment = itemView.findViewById(R.id.tvComment);


        }
        public void bind(Post post) {
            tvDescription.setText(post.getDescription());
            tvUserName.setText(post.getUser().getUsername());
            String timeformat = TimeFormatter.getTimeDifference(post.getCreatedAt().toString());
            tvTime.setText(timeformat + " ago");

            if (post.getImage() != null){
                Glide.with(context).load(post.getImage().getUrl()).into(ivPost_Image);
            }

            ivPost_Image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("Post", Parcels.wrap(post));
                    context.startActivity(intent);
                }
            });

            ivheart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //like = post.getLikes();


                        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.red_heart);
                        drawable.setBounds(0,0, drawable.getMinimumHeight(), drawable.getMinimumWidth());
                        tvNumLikes.setText(String.valueOf(post.getLikes()));

                    //if (post.removeLikes(null))


                    {
                        drawable = ContextCompat.getDrawable(context, R.drawable.heart);
                        drawable.setBounds(0,0, drawable.getMinimumHeight(), drawable.getMinimumWidth());

                        tvNumLikes.setText(String.valueOf(post.getLikes()));
                    }

                }
            });


            ivcomment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goCommentActivity();
                }
            });


            Glide.with(context).load(post.getUser().getParseFile(User.KEY_Profile_Image).getUrl()).transform(new CircleCrop()).into(ivProfile_Image1);

        }

    }

    private void goCommentActivity() {
        Intent intent = new Intent(context, CommentActivity.class);
        context.startActivity(intent);
    }

}

