package tn.khammami.app;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.net.URL;
import java.util.List;

@Database(entities = {School.class}, version = 1, exportSchema = false)
public abstract class SchoolRoomDatabase extends RoomDatabase {

    public abstract SchoolDao schoolDao();

    public static SchoolRoomDatabase INSTANCE;

    public static SchoolRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (SchoolRoomDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            SchoolRoomDatabase.class, "word_database")
                            // Wipes and rebuilds instead of migrating if no Migration object.
                            // Migration is not part of this practical.
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();

                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final SchoolDao mDao;

        PopulateDbAsync(SchoolRoomDatabase db) {
            mDao = db.schoolDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // If we have no words, then create the initial list of words
            if (mDao.getAnySchool().length < 1) {
                URL schoolsRequestUrl = NetworkUtils.buildUrl();

                try {
                    String jsonWeatherResponse = NetworkUtils
                            .getResponseFromHttpUrl(schoolsRequestUrl);

                    List<School> mSchools = SchoolListJsonUtils
                            .getSchoolListFromJson(jsonWeatherResponse);

                    if (mSchools.size() > 0){
                        mDao.insertList(mSchools);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }
}
