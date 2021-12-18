package tn.khammami.app;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "school_table")
public class School {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String name;
    private String description;
    private String logo;

    public School(@NonNull String name, String description, String logo) {
        this.name = name;
        this.description = description;
        this.logo = logo;
    }

    @Ignore
    public School(int id, @NonNull String name, String description, String logo) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.logo = logo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
