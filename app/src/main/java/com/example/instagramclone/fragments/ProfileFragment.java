package com.example.instagramclone.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.instagramclone.Adapter.ProfileAdapter;
import com.example.instagramclone.Activity.LoginActivity;
import com.example.instagramclone.models.Post;
import com.example.instagramclone.R;
import com.example.instagramclone.models.User;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {


    private static final String TAG = "ProfileFragment";
    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 42;
    private File photoFile;
    public String photoFileName = "photo.jpg";


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
        Glide.with(getContext()).load(ParseUser.getCurrentUser().getParseFile(User.KEY_Profile_Image).getUrl()).transform(new CircleCrop()).into(ivProfile_Image);

        ivProfile_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchCamera();
                Toast.makeText(getContext(), "Camera launch", Toast.LENGTH_SHORT).show();

                if (photoFile == null || ivProfile_Image.getDrawable() == null) {
                    Toast.makeText(getContext(), "There is no image!", Toast.LENGTH_SHORT).show();
                    return;
                }
                ParseUser currentUser = ParseUser.getCurrentUser();
                saveProfile(currentUser, photoFile);

            }
        });


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

    private void launchCamera() {

        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Create a File reference for future access
        photoFile = getPhotoFileUri(photoFileName);

        // wrap File object into a content provider
        // required for API >= 24
        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
        Uri fileProvider = FileProvider.getUriForFile(getContext(), "com.codepath.fileprovider", photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(getContext().getPackageManager()) != null) {
            // Start the image capture intent to take photo
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }


    private void startActivityForResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                // by this point we have the camera photo on disk
                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                // RESIZE BITMAP, see section below
                // Load the taken image into a preview
                ivProfile_Image.setImageBitmap(takenImage);
            } else { // Result was a failure
                Toast.makeText(getContext(), "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        }
    }



    // Returns the File for a photo stored on disk given the fileName
    private File getPhotoFileUri(String fileName) {

        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(TAG, "failed to create directory");
        }

        // Return the file target for the photo based on filename
        return new File (mediaStorageDir.getPath() + File.separator + fileName);


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


    private void saveProfile( ParseUser currentUser, File photoFile) {
        User user = new User();


        user.setImage(new ParseFile(photoFile));
        user.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Error while saving", e);
                    Toast.makeText(getContext(), "Error while saving!", Toast.LENGTH_SHORT).show();

                    return;
                }

                Log.i(TAG, "Profile save was successful!!!");

                ivProfile_Image.setImageResource(0);
            }
        });
    }



    private void goLoginActivity() {

        Intent i = new Intent(getActivity(), LoginActivity.class);
        startActivity(i);
        ((Activity) getActivity()).overridePendingTransition(0, 0);

    }


}
