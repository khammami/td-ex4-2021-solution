package tn.khammami.app;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.net.URL;
import java.util.List;

public class SchoolRepository {
    private SchoolDao mSchoolDao;
    private LiveData<List<School>> mAllSchools;

    SchoolRepository(Application application) {
        SchoolRoomDatabase db = SchoolRoomDatabase.getDatabase(application);
        mSchoolDao = db.schoolDao();
        mAllSchools = mSchoolDao.getAllSchools();
    }

    LiveData<List<School>> getAllSchools() {
        return mAllSchools;
    }

    public void deleteAll() {
        new deleteAllSchoolsAsyncTask(mSchoolDao).execute();
    }

    public void reloadData() {
        new reloadAllSchoolsAsyncTask(mSchoolDao).execute();
    }


    private static class deleteAllSchoolsAsyncTask extends AsyncTask<Void, Void, Void> {
        private SchoolDao mAsyncTaskDao;

        deleteAllSchoolsAsyncTask(SchoolDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

    private static class reloadAllSchoolsAsyncTask extends AsyncTask<Void, Void, Void> {
        private SchoolDao mAsyncTaskDao;

        reloadAllSchoolsAsyncTask(SchoolDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();

            URL schoolsRequestUrl = NetworkUtils.buildUrl();

            try {
                String jsonWeatherResponse = NetworkUtils
                        .getResponseFromHttpUrl(schoolsRequestUrl);

                List<School> mSchools = SchoolListJsonUtils
                        .getSchoolListFromJson(jsonWeatherResponse);

                if (mSchools.size() > 0){
                    mAsyncTaskDao.insertList(mSchools);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
