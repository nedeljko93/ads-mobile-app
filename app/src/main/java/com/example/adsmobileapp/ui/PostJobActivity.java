package com.example.adsmobileapp.ui;

import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adsmobileapp.AdsApp;
import com.example.adsmobileapp.R;
import com.example.adsmobileapp.rest.RestManager;
import com.example.adsmobileapp.rest.endpoints.AddJob;
import com.example.adsmobileapp.rest.model.Job;
import com.example.adsmobileapp.rest.model.ResponseMessage;
import com.example.adsmobileapp.ui.dialogs.CustomDialog;
import com.example.adsmobileapp.utils.Constants;
import com.example.adsmobileapp.utils.DateUtils;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostJobActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener {
    private TextInputEditText etTitle;
    private TextInputEditText etDescription;
    private TextInputEditText etRequredSkills;
    private TextInputEditText etOffer;
    private TextInputEditText etCompanyName;
    private TextInputEditText etBonusSkills;

    private TextView tvLocationBtn;
    private TextView tvTechnologiesBtn;
    private TextView tvPickedLocation;
    private TextView tvPickedTechnologies;

    private CheckBox cbJunior;
    private CheckBox cbMedior;
    private CheckBox cbSenior;

    private Job job;

    private List<String> skillLevel = new ArrayList<>();

    @Override
    protected int provideLayout() {
        return R.layout.activity_post_job;
    }

    @Override
    protected boolean hasHeader() {
        return true;
    }

    @Override
    protected String getHeaderTitle() {
        if (job != null) {
            return getString(R.string.update_job);
        }
        return getString(R.string.post_job);
    }

    @Override
    protected boolean hasMainButton() {
        return true;
    }

    @Override
    protected String getMainButtonTitle() {
        return "Create";
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
        etTitle = findViewById(R.id.etTitle);
        etDescription = findViewById(R.id.etJobDescription);
        etRequredSkills = findViewById(R.id.etRequredSkills);
        etOffer = findViewById(R.id.etJobOffer);
        etCompanyName = findViewById(R.id.etCompanyName);
        etBonusSkills = findViewById(R.id.etBonusSkills);

        tvLocationBtn = findViewById(R.id.tvLocation);
        tvLocationBtn.setOnClickListener(this);

        tvTechnologiesBtn = findViewById(R.id.tvTechnologies);
        tvTechnologiesBtn.setOnClickListener(this);

        tvPickedLocation = findViewById(R.id.tvPickedLocation);
        tvPickedTechnologies = findViewById(R.id.tvPickedTechnologies);

        cbJunior = findViewById(R.id.cbJunior);
        cbJunior.setOnCheckedChangeListener(this);

        cbMedior = findViewById(R.id.cbMedior);
        cbMedior.setOnCheckedChangeListener(this);

        cbSenior = findViewById(R.id.cbSenior);
        cbSenior.setOnCheckedChangeListener(this);
    }

    public void populateData() {
        tvMainButton.setText(getString(R.string.apply_changes));
        etTitle.setText(job.getTitle());
        etDescription.setText(job.getDescription());
        etRequredSkills.setText(job.getRequredSkills());
        etBonusSkills.setText(job.getBonusSkills());
        etOffer.setText(job.getOffer());
        etCompanyName.setText(job.getCompanyName());
        tvPickedLocation.setText(job.getLocation());
        tvPickedTechnologies.setText(job.getTechnologiesRequred());
        if (job.getRequredLevel().contains(getString(R.string.junior))) {
            cbJunior.setChecked(true);
            skillLevel.add(getString(R.string.junior));
        }
        if (job.getRequredLevel().contains(getString(R.string.medior))) {
            cbMedior.setChecked(true);
            skillLevel.add(getString(R.string.medior));
        }
        if (job.getRequredLevel().contains(getString(R.string.senior))) {
            cbSenior.setChecked(true);
            skillLevel.add(getString(R.string.senior));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBackButton:
                onBackPressed();
                finish();
                break;
            case R.id.tvMainButton:
                postJob();
                break;
            case R.id.tvLocation:
                CustomDialog customDialogPlaces = new CustomDialog(this, Arrays.asList(getResources().getStringArray(R.array.places)), listOfFilterItems -> {
                    String values = "";
                    for (String item : listOfFilterItems) {
                        values += item + ", ";
                    }
                    tvPickedLocation.setText(values);
                });
                customDialogPlaces.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                customDialogPlaces.show();
                break;
            case R.id.tvTechnologies:
                break;
        }
    }

    private void postJob() {
        if (validation()) {
            String valueSkillLevel = "";
            for (String val : skillLevel) {
                valueSkillLevel += val + " ";
            }
            Job job = new Job(etTitle.getText().toString(), etDescription.getText().toString(), etRequredSkills.getText().toString(),
                    etOffer.getText().toString(), tvPickedLocation.getText().toString(), etCompanyName.getText().toString(),
                    DateUtils.getCurrentDate(), valueSkillLevel, tvPickedTechnologies.getText().toString(),
                    AdsApp.getLocalData().getLoggedUser().getEmail(), AdsApp.getLocalData().getLoggedUser(),
                    etBonusSkills.getText().toString());

            RestManager.getInstance().getService(AddJob.class).addJob(job).enqueue(new Callback<ResponseMessage>() {
                @Override
                public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                    Toast.makeText(PostJobActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(PostJobActivity.this, ListofJobsActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onFailure(Call<ResponseMessage> call, Throwable t) {
                    Toast.makeText(PostJobActivity.this, "Error " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private boolean validation() {
        boolean val = true;
        if (etTitle.getText().toString().isEmpty()) {
            etTitle.setError(getString(R.string.invalid));
            val = false;
        }
        if (etDescription.getText().toString().isEmpty()) {
            etDescription.setError(getString(R.string.invalid));
            val = false;
        }
        if (etRequredSkills.getText().toString().isEmpty()) {
            etRequredSkills.setError(getString(R.string.invalid));
            val = false;
        }
        if (etBonusSkills.getText().toString().isEmpty()) {
            etBonusSkills.setError(getString(R.string.invalid));
            val = false;
        }
        if (etCompanyName.getText().toString().isEmpty()) {
            etCompanyName.setError(getString(R.string.invalid));
            val = false;
        }
        if (etOffer.getText().toString().isEmpty()) {
            etOffer.setError(getString(R.string.invalid));
            val = false;
        }
        if (skillLevel.isEmpty()) {
            Toast.makeText(PostJobActivity.this, getString(R.string.select_requred_level), Toast.LENGTH_SHORT).show();
            val = false;
        }
        if (tvPickedLocation.equals(getString(R.string.pick_location))) {
            Toast.makeText(PostJobActivity.this, getString(R.string.pick_location), Toast.LENGTH_SHORT).show();
            val = false;
        }
        return val;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            skillLevel.add(buttonView.getText().toString());
        } else {
            skillLevel.remove(buttonView.getText().toString());
        }
    }
}
