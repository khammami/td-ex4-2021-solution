package tn.khammami.app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class SchoolListAdapter extends RecyclerView.Adapter<SchoolViewHolder> {
    List<School> schools;
    private SchoolClickListener schoolClickListener;

    public SchoolListAdapter() {}

    @NonNull
    @Override
    public SchoolViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.school_list_item, parent, false);
        return new SchoolViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SchoolViewHolder holder, int position) {
        School mSchool = schools.get(position);

        holder.name.setText(mSchool.getName());
        holder.description.setText(mSchool.getDescription());
        Picasso.get().load(mSchool.getLogo()).into(holder.logo);

        holder.setAdapterClickListener(schoolClickListener);
    }

    @Override
    public int getItemCount() {
        if (schools != null) return schools.size();
        else return 0;
    }

    public void setOnSchoolClickListener(SchoolClickListener schoolClickListener) {
        this.schoolClickListener = schoolClickListener;
    }

    public void setSchools(List<School> schools){
        this.schools = schools;
        notifyDataSetChanged();
    }
}
