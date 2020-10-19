package com.example.adsmobileapp.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adsmobileapp.R;
import com.example.adsmobileapp.rest.model.Job;
import com.example.adsmobileapp.ui.JobDetailsActivity;
import com.example.adsmobileapp.ui.PostJobActivity;
import com.example.adsmobileapp.ui.adapters.JobsAdapter;
import com.example.adsmobileapp.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class JobsListFragment extends Fragment implements JobsAdapter.OnJobClickListener {
    private EditText etSearch;
    private RecyclerView rvListOfJobs;
    private JobsAdapter jobsAdapter;

    private List<Job> jobList;
    private int fragmentPos;

    public JobsListFragment(List<Job> jobList, int fragmentPos) {
        this.jobList = jobList;
        this.fragmentPos = fragmentPos;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jobs_list, container, false);
        initUI(view);
        return view;

    }


    @Override
    public void onResume() {
        super.onResume();
    }

    private void initUI(View view) {
        etSearch = view.findViewById(R.id.etSearch);
        rvListOfJobs = view.findViewById(R.id.rvJobs);
        initRecyclerView();

        etSearch = view.findViewById(R.id.etSearch);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filterList(s.toString());
            }
        });
    }

    private void filterList(String text) {
        if (!text.equals("")) {
            List<Job> filtredItems = new ArrayList<>();
            for (Job string : jobList) {
                if (string.getTitle().toLowerCase().contains(text.toLowerCase())) {
                    filtredItems.add(string);

                }
            }
            if (filtredItems.size() != 0) {
                jobsAdapter.setJobsList(filtredItems);

            }
        } else {
            jobsAdapter.setJobsList(jobList);
        }
    }

    private void initRecyclerView() {
        jobsAdapter = new JobsAdapter(jobList, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvListOfJobs.setLayoutManager(linearLayoutManager);
        rvListOfJobs.setAdapter(jobsAdapter);
    }

    @Override
    public void onClick(Job job) {
        if (fragmentPos == 0) {
            Intent intent = new Intent(getActivity(), JobDetailsActivity.class);
            intent.putExtra(Constants.EXTRA_JOB, job);
            startActivity(intent);

        } else {
            Intent intent = new Intent(getActivity(), PostJobActivity.class);
            intent.putExtra(Constants.EXTRA_JOB, job);
            startActivity(intent);
        }
    }


    public void setJobList(List<Job> jobList) {
        this.jobList = jobList;
        jobsAdapter.setJobsList(jobList);
    }
}
