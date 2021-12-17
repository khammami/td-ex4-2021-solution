package tn.khammami.app;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class SchoolViewModel extends AndroidViewModel {

    private SchoolRepository mRepository;

    private LiveData<List<School>> mAllSchools;
    
    public SchoolViewModel(@NonNull Application application) {
        super(application);
        mRepository = new SchoolRepository(application);
        mAllSchools = mRepository.getAllSchools();
    }

    LiveData<List<School>> getAllSchools() {
        return mAllSchools;
    }

    public void reloadData() {
        mRepository.reloadData();
    }

    public void deleteAll() {
        mRepository.deleteAll();
    }
}
