package tn.khammami.app;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.net.URL;
import java.util.List;

public class SchoolRepository {
    private final SchoolDao mSchoolDao;
    private final LiveData<List<School>> mAllSchools;

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

    public void insert(School school) {
        new insertAsyncTask(mSchoolDao).execute(school);
    }

    public void update(School school)  {
        new updateSchoolAsyncTask(mSchoolDao).execute(school);
    }

    public void deleteSchool(School school) {
        new deleteSchoolAsyncTask(mSchoolDao).execute(school);
    }

    public void deleteSchoolById(int id) {
        new deleteSchoolByIdAsyncTask(mSchoolDao).execute(id);
    }

    private static class insertAsyncTask extends AsyncTask<School, Void, Void> {

        private final SchoolDao mAsyncTaskDao;

        insertAsyncTask(SchoolDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final School... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }


    private static class deleteAllSchoolsAsyncTask extends AsyncTask<Void, Void, Void> {
        private final SchoolDao mAsyncTaskDao;

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
        private final SchoolDao mAsyncTaskDao;

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

    private static class updateSchoolAsyncTask extends AsyncTask<School, Void, Void> {
        private final SchoolDao mAsyncTaskDao;

        updateSchoolAsyncTask(SchoolDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final School... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }

    private static class deleteSchoolAsyncTask extends AsyncTask<School, Void, Void> {
        private final SchoolDao mAsyncTaskDao;

        deleteSchoolAsyncTask(SchoolDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final School... params) {
            mAsyncTaskDao.deleteSchool(params[0]);
            return null;
        }
    }

    private static class deleteSchoolByIdAsyncTask extends AsyncTask<Integer, Void, Void> {
        private final SchoolDao mAsyncTaskDao;

        deleteSchoolByIdAsyncTask(SchoolDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Integer... params) {
            mAsyncTaskDao.deleteSchoolById(params[0]);
            return null;
        }
    }
}
