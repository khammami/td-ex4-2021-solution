package tn.khammami.app;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SchoolViewHolder extends RecyclerView.ViewHolder {
    TextView name;
    TextView description;
    ImageView logo;
    private SchoolClickListener clickListener;

    public SchoolViewHolder(@NonNull View itemView) {
        super(itemView);

        this.name = itemView.findViewById(R.id.school_name);
        this.description = itemView.findViewById(R.id.school_description);
        this.logo = itemView.findViewById(R.id.school_logo);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickListener != null) {
                    clickListener.onItemClick(view, getBindingAdapterPosition());
                }
            }
        });
    }

    void setAdapterClickListener(SchoolClickListener schoolClickListener){
        this.clickListener = schoolClickListener;
    }
}
