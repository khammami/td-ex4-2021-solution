package tn.khammami.app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class SchoolActivity extends AppCompatActivity {
    public static final String SCHOOL_ID_KEY = "school_id";
    public static String SCHOOL_NAME_KEY = "school_name";
    public static String SCHOOL_DESCRIPTION_KEY = "school_description";
    public static String SCHOOL_LOGO_KEY = "school_logo";

    public static String SCHOOL_DELETE_ACTION_KEY = "is_delete_action";

    public static final int PICK_IMAGE_REQUEST_CODE = 1;

    private EditText schoolNameEditText;
    private EditText schoolDescriptionEditText;
    private ImageView schoolLogoImageView;

    private int mId = -1;

    String schoolLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school);

        schoolNameEditText = findViewById(R.id.school_name);
        schoolDescriptionEditText = findViewById(R.id.school_description);
        schoolLogoImageView = findViewById(R.id.school_logo);

        if (mId == -1  && getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.new_school_title);
        }

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mId = extras.getInt(SCHOOL_ID_KEY, -1);
            String schoolName = extras.getString(SCHOOL_NAME_KEY, "");
            String schoolDescription = extras.getString(SCHOOL_DESCRIPTION_KEY, "");
            schoolLogo = extras.getString(SCHOOL_LOGO_KEY, "");

            if (!schoolName.isEmpty()) {
                schoolNameEditText.setText(schoolName);
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle(schoolName);
                }
            }
            if (!schoolDescription.isEmpty()) {
                schoolDescriptionEditText.setText(schoolDescription);
            }
            if (!schoolLogo.isEmpty()) {
                Picasso.get().load(schoolLogo).into(schoolLogoImageView);
            }
        }

        schoolLogoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select a Logo"), PICK_IMAGE_REQUEST_CODE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.shcool_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.save) {
            // Create a new Intent for the reply.
            Intent replyIntent = new Intent();
            if (TextUtils.isEmpty(schoolNameEditText.getText()) &&
                    TextUtils.isEmpty(schoolDescriptionEditText.getText())) {
                // No post was entered, set the result accordingly.
                setResult(RESULT_CANCELED, replyIntent);
            } else {
                // Get the new post that the user entered.
                String schoolName = schoolNameEditText.getText().toString();
                String schoolDesc = schoolDescriptionEditText.getText().toString();
                // Put the new post in the extras for the reply Intent.
                replyIntent.putExtra(SCHOOL_NAME_KEY, schoolName);
                replyIntent.putExtra(SCHOOL_DESCRIPTION_KEY, schoolDesc);
                replyIntent.putExtra(SCHOOL_LOGO_KEY, schoolLogo);
                replyIntent.putExtra(SCHOOL_ID_KEY, mId);
                // Set the result status to indicate success.
                setResult(RESULT_OK, replyIntent);
            }
            finish();
            return true;
        }

        if (id == R.id.delete){
            Intent replyIntent = new Intent();
            replyIntent.putExtra(SCHOOL_ID_KEY, mId);
            replyIntent.putExtra(SCHOOL_DELETE_ACTION_KEY, true);
            setResult(RESULT_CANCELED, replyIntent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null){
                Uri logoUri = data.getData();
                Log.d("image data", data.getData().toString());
                Picasso.get().load(logoUri).into(schoolLogoImageView);
                schoolLogo = logoUri.toString();
            }

        }
    }
}