package com.example.adsmobileapp.ui.adapters;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.adsmobileapp.AdsApp;
import com.example.adsmobileapp.rest.model.Job;
import com.example.adsmobileapp.ui.fragments.JobsListFragment;

import java.util.ArrayList;
import java.util.List;

public class JobsListFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private JobsListFragment jobsListFragment1;
    private JobsListFragment jobsListFragment2;
    private List<Job> jobList;
    private List<Job> postJobList = new ArrayList<>();

    public JobsListFragmentPagerAdapter(@NonNull FragmentManager fm, List<Job> jobList) {
        super(fm);
        this.jobList = jobList;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        filterJobs();
        switch (position) {
            case 0:
                return jobsListFragment1 = new JobsListFragment(jobList, 0);
            case 1:
                return jobsListFragment2 = new JobsListFragment(postJobList, 1);
        }
        return null;
    }

    @Override
    public int getCount() {
        if (AdsApp.getLocalData().getLoggedUser() != null) {
            return 2;
        }
        return 1;
    }

    public JobsListFragment getJobsListFragment() {
        notifyDataSetChanged();

        return jobsListFragment1;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    private void filterJobs() {
        postJobList.clear();
        for (Job job : jobList) {
            if (AdsApp.getLocalData().getLoggedUser() != null && AdsApp.getLocalData().getLoggedUser().getId() != null) {
                if (job.getUser() != null && job.getUser().getId().equals(AdsApp.getLocalData().getLoggedUser().getId())) {
                    postJobList.add(job);
                }
            }
        }
    }

}
