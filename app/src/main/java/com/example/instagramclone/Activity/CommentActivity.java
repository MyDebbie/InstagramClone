package com.example.instagramclone.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.instagramclone.R;
import com.example.instagramclone.models.Comment;
import com.example.instagramclone.models.Post;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.parceler.Parcels;

import java.util.Objects;

public class CommentActivity extends AppCompatActivity {

    EditText edComment;
    Button btonPost;
    Post post;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ParseUser parseUser = ParseUser.getCurrentUser();
        post = Parcels.unwrap(getIntent().getParcelableExtra("Post"));

        setContentView(R.layout.activity_comment);

        edComment = findViewById(R.id.edComment);
        btonPost = findViewById(R.id.btonPost);

        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_left);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Comment");


        btonPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comment = edComment.getText().toString();
                if(comment.isEmpty()){
                    Toast.makeText(CommentActivity.this, "Comment cannot be Empty!!!!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                saveComment(comment, parseUser);
            }
        });
    }

    private void saveComment(String description, ParseUser currentUser) {

        Comment comment = new Comment();
        comment.setComments(description);
        comment.setUser(currentUser);
        post.setComment(comment);

        post.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {

                    return;
                }
                edComment.setText("");
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {
        Intent intent = new Intent(CommentActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivityIfNeeded(intent, 0);
        return true;
    }
}