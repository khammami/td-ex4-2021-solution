package tn.khammami.app;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<School> mSchools = new ArrayList<>();
    private SchoolListAdapter mSchoolListAdapter;

    private SchoolViewModel mSchoolViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView mRecyclerView = findViewById(R.id.recyclerview);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mSchoolListAdapter = new SchoolListAdapter();
        mRecyclerView.setAdapter(mSchoolListAdapter);

        mSchoolListAdapter.setOnSchoolClickListener(new SchoolClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getApplicationContext(), SchoolActivity.class);

                School mSchool = mSchools.get(position);

                intent.putExtra(SchoolActivity.SCHOOL_NAME_KEY, mSchool.getName());
                intent.putExtra(SchoolActivity.SCHOOL_DESCRIPTION_KEY, mSchool.getDescription());
                intent.putExtra(SchoolActivity.SCHOOL_LOGO_KEY, mSchool.getLogo());

                startActivity(intent);
            }
        });

        mSchoolViewModel = new ViewModelProvider(this).get(SchoolViewModel.class);
        mSchoolViewModel.getAllSchools().observe(this, new Observer<List<School>>() {
            @Override
            public void onChanged(List<School> schools) {
                mSchools = schools;
                mSchoolListAdapter.setSchools(schools);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.delete_all) {
            Toast.makeText(this, R.string.delete_all_data_message, Toast.LENGTH_LONG).show();
            mSchoolViewModel.deleteAll();
            return true;
        } else if (id == R.id.reload){
            Toast.makeText(this, R.string.reload_data_message, Toast.LENGTH_LONG).show();
            mSchoolViewModel.reloadData();
        }
        return super.onOptionsItemSelected(item);
    }
}