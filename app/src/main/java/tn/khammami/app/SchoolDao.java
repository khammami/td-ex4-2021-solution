package tn.khammami.app;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SchoolDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(School school);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertList(List<School> school);

    @Delete
    void deleteSchool(School school);

    @Query("DELETE FROM school_table WHERE id = :schoolId")
    void deleteSchoolById(int schoolId);

    @Query("DELETE FROM school_table")
    void deleteAll();

    @Query("SELECT * from school_table LIMIT 1")
    School[] getAnySchool();

    @Query("SELECT * from school_table ORDER BY name ASC")
    LiveData<List<School>> getAllSchools();

    @Update
    void update(School... school);
}
