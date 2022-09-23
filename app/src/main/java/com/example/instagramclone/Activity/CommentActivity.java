package com.example.instagramclone.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.instagramclone.R;

public class CommentActivity extends AppCompatActivity {

    EditText edComment;
    Button btonPost;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        edComment = findViewById(R.id.edComment);
        btonPost = findViewById(R.id.btonPost);
    }
}