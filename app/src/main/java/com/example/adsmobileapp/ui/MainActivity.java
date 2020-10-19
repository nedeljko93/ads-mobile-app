package com.example.adsmobileapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.adsmobileapp.AdsApp;
import com.example.adsmobileapp.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvLogin;
    private TextView tvSkipLogin;
    private TextView tvFindJob;
    private TextView tvPostJob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AdsApp.getLocalData().setPostingJob(false);
        initUI();
    }

    private void initUI() {
        tvLogin = findViewById(R.id.tvLogin);
        tvLogin.setOnClickListener(this);

        tvSkipLogin = findViewById(R.id.tvSkipLogin);
        tvSkipLogin.setOnClickListener(this);

        tvFindJob = findViewById(R.id.tvFindJob);
        tvFindJob.setOnClickListener(this);

        tvPostJob = findViewById(R.id.tvPostJob);
        tvPostJob.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvLogin:
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
                break;

            case R.id.tvSkipLogin:
                AdsApp.getLocalData().setLoggedUser(null);
                Intent intent = new Intent(MainActivity.this, ListofJobsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
                break;
            case R.id.tvFindJob:
                if (AdsApp.getLocalData().isPostingJob()) {
                    AdsApp.getLocalData().setPostingJob(false);
                    tvSkipLogin.setVisibility(View.VISIBLE);
                    tvFindJob.setBackground(getResources().getDrawable(R.drawable.selected_toggle_button));
                    tvFindJob.setTextColor(getResources().getColor(R.color.white));

                    tvPostJob.setBackground(getResources().getDrawable(R.drawable.not_selected_toggle_button));
                    tvPostJob.setTextColor(getResources().getColor(R.color.black));
                }
                break;
            case R.id.tvPostJob:
                if (!AdsApp.getLocalData().isPostingJob()) {
                    AdsApp.getLocalData().setPostingJob(true);
                    tvSkipLogin.setVisibility(View.GONE);
                    tvPostJob.setBackground(getResources().getDrawable(R.drawable.selected_toggle_button));
                    tvPostJob.setTextColor(getResources().getColor(R.color.white));

                    tvFindJob.setBackground(getResources().getDrawable(R.drawable.not_selected_toggle_button));
                    tvFindJob.setTextColor(getResources().getColor(R.color.black));

                }
                break;
        }
    }
}
