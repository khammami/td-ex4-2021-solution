package tn.khammami.app;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class SchoolActivityViewModel extends AndroidViewModel {
    private final SchoolRepository mRepository;

    public SchoolActivityViewModel(@NonNull Application application) {
        super(application);
        mRepository = new SchoolRepository(application);
    }

    public LiveData<School> getSchoolById(int id) {
        return mRepository.getSchoolById(id);
    }

    public void insert(School post) {
        mRepository.insert(post);
    }

    public void update(School post) {
        mRepository.update(post);
    }

    public void delete(School post) {
        mRepository.deleteSchool(post);
    }
}
