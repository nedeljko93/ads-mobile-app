package com.example.adsmobileapp.ui;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.example.adsmobileapp.R;
import com.example.adsmobileapp.rest.model.Job;
import com.example.adsmobileapp.utils.Constants;

public class JobDetailsActivity extends BaseActivity implements View.OnClickListener {
    private Job job;
    private TextView tvJobTitle;
    private TextView tvLocation;
    private TextView tvDate;
    private TextView tvJobDescription;
    private TextView tvJobRequerments;
    private TextView tvJobOffer;
    private TextView tvBonusSkills;


    @Override
    protected int provideLayout() {
        return R.layout.activity_job_details;
    }

    @Override
    protected boolean hasHeader() {
        return true;
    }

    @Override
    protected String getHeaderTitle() {
        return job.getTitle();
    }

    @Override
    protected boolean hasMainButton() {
        return true;
    }

    @Override
    protected String getMainButtonTitle() {
        return "Apply";
    }

    @Override
    protected void layoutReady() {
        initUI();
        populateData();
    }

    @Override
    protected void getExtras() {
        if (getIntent().hasExtra(Constants.EXTRA_JOB)) {
            job = (Job) getIntent().getSerializableExtra(Constants.EXTRA_JOB);
        }
    }

    private void initUI() {
        tvJobTitle = findViewById(R.id.tvJobTitle);
        tvLocation = findViewById(R.id.tvLocation);
        tvDate = findViewById(R.id.tvCreationDate);
        tvJobDescription = findViewById(R.id.tvJobDescription);
        tvJobRequerments = findViewById(R.id.tvJobRequierments);
        tvJobOffer = findViewById(R.id.tvOffer);
        tvBonusSkills = findViewById(R.id.tvBonusSkills);

    }


    private void populateData() {
        tvJobTitle.setText(job.getTitle());
        tvLocation.setText(job.getLocation());
        tvDate.setText(job.getDate());
        tvJobDescription.setText(job.getDescription());
        tvJobRequerments.setText(job.getRequredSkills());
        tvJobOffer.setText(job.getOffer());
        tvBonusSkills.setText(job.getBonusSkills());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvMainButton:
                Intent intent = new Intent(JobDetailsActivity.this, JobApplyActivity.class);
                intent.putExtra(Constants.EXTRA_JOB, job);
                startActivity(intent);
                break;
            case R.id.ivBackButton:
                onBackPressed();
                break;
        }
    }

}
