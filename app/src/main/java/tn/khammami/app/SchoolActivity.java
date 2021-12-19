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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

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

    private SchoolActivityViewModel mViewModel;
    private School mSchool;

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

        mViewModel = new ViewModelProvider(this).get(SchoolActivityViewModel.class);

        Bundle extras = getIntent().getExtras();

        // If we are passed content, fill it in for the user to edit.
        if (extras != null) {
            mId = extras.getInt(SCHOOL_ID_KEY, -1);
        }

        if (mId == -1){
            mSchool = new School();
            updateUI(mSchool);
        }else {
            mViewModel.getSchoolById(mId).observe(this, new Observer<School>() {
                @Override
                public void onChanged(School school) {
                    mSchool = school;
                    updateUI(school);
                }
            });
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

    private void updateUI(School school) {
        if (school != null) {
            if (school.getName() != null && !school.getName().isEmpty()) {
                schoolNameEditText.setText(school.getName());
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle(school.getName());
                }
            }
            if (school.getDescription() != null && !school.getDescription().isEmpty()) {
                schoolDescriptionEditText.setText(school.getDescription());
            }
            if (school.getLogo() != null && !school.getLogo().isEmpty()) {
                Picasso.get().load(school.getLogo()).into(schoolLogoImageView);
            }
        }
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
            Intent replyIntent = new Intent();
            if (TextUtils.isEmpty(schoolNameEditText.getText()) &&
                    TextUtils.isEmpty(schoolDescriptionEditText.getText())) {
                // No post was entered, set the result accordingly.
                setResult(RESULT_CANCELED, replyIntent);
            } else {
                // Get the new post that the user entered.
                mSchool.setName(schoolNameEditText.getText().toString());
                mSchool.setDescription(schoolDescriptionEditText.getText().toString());

                if (mId == -1 ){
                    mViewModel.insert(mSchool);
                }else {
                    mViewModel.update(mSchool);
                }
                // Set the result status to indicate success.
                setResult(RESULT_OK, replyIntent);
            }
            finish();
            return true;
        }

        if (id == R.id.delete){
            if (mId != -1){
                mViewModel.delete(mSchool);
            }
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
                mSchool.setLogo(logoUri.toString());
                updateUI(mSchool);
            }

        }
    }
}