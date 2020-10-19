package com.example.adsmobileapp.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adsmobileapp.AdsApp;
import com.example.adsmobileapp.R;
import com.example.adsmobileapp.rest.model.Job;

import java.util.List;

public class JobsAdapter extends RecyclerView.Adapter<JobsAdapter.ViewHolder> {
    private List<Job> jobsList;
    private OnJobClickListener listener;

    public JobsAdapter(List<Job> jobsList, OnJobClickListener listener) {
        this.jobsList = jobsList;
        this.listener = listener;

    }

    public interface OnJobClickListener {
        void onClick(Job job);
    }

    @NonNull
    @Override
    public JobsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_job, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JobsAdapter.ViewHolder holder, int position) {

        holder.tvJobTitle.setText(jobsList.get(position).getTitle());
        holder.tvJobLocation.setText(jobsList.get(position).getLocation());
        holder.tvJobDate.setText(jobsList.get(position).getDate());
        holder.tvTechnologiesRequred.setText(jobsList.get(position).getTechnologiesRequred());
        holder.tvExperinceLevel.setText(jobsList.get(position).getRequredLevel());
        if (jobsList.get(position).isPromoted()) {
            holder.clItem.setBackground(holder.clItem.getContext().getResources().getDrawable(R.drawable.selector_card_promoted));
        }

    }

    @Override
    public int getItemCount() {
        if (jobsList == null) {
            return 0;
        }
        return jobsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvJobTitle;
        private TextView tvJobLocation;
        private TextView tvJobDate;
        private TextView tvExperinceLevel;
        private TextView tvTechnologiesRequred;

        private ConstraintLayout clItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvJobTitle = itemView.findViewById(R.id.tvJobTitle);
            tvJobLocation = itemView.findViewById(R.id.tvLocation);
            tvJobDate = itemView.findViewById(R.id.tvDate);
            tvExperinceLevel = itemView.findViewById(R.id.tvLevel);
            tvTechnologiesRequred = itemView.findViewById(R.id.tvRequredTechnologies);

            clItem = itemView.findViewById(R.id.clItem);
            clItem.setOnClickListener(v -> listener.onClick(jobsList.get(getAdapterPosition())));
        }
    }

    public void setJobsList(List<Job> jobsList) {
        this.jobsList = jobsList;
        notifyDataSetChanged();
    }
}
