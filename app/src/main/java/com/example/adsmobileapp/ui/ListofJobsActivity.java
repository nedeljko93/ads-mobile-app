package com.example.adsmobileapp.ui;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.adsmobileapp.AdsApp;
import com.example.adsmobileapp.R;
import com.example.adsmobileapp.rest.RestManager;
import com.example.adsmobileapp.rest.endpoints.GetJobs;
import com.example.adsmobileapp.rest.endpoints.Logout;
import com.example.adsmobileapp.rest.model.Job;
import com.example.adsmobileapp.rest.model.ResponseMessage;
import com.example.adsmobileapp.ui.adapters.JobsAdapter;
import com.example.adsmobileapp.ui.adapters.JobsListFragmentPagerAdapter;
import com.example.adsmobileapp.ui.dialogs.CustomDialog;
import com.example.adsmobileapp.ui.dialogs.ExperienceLevelDialog;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListofJobsActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private ImageView ivFilter;
    private DrawerLayout drawerLayout;
    private NavigationView navFilter;
    private RecyclerView rvListOfJobs;
    private JobsAdapter jobsAdapter;

    private TextView tvPickExperienceLevel;
    private TextView tvPickLocation;
    private TextView tvLogoutLogin;
    private TextView tvUsername;
    private TextView tvPostJob;
    private TextView tvTitle;

    private LinearLayout llLogoutLogin;
    private LinearLayout llUser;

    private LinearLayout llViewPagerBottomNavigation;

    private CheckBox cbFullTime;
    private CheckBox cbPartTime;

    private ImageView ivLogOutLogin;

    private TextView tvListOfJobsBtn;
    private TextView tvListOfPostedJobsBtn;

    private List<Job> jobList = new ArrayList<>();

    private ViewPager viewPager;
    private JobsListFragmentPagerAdapter jobsListFragmentPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listof_jobs);
        initUI();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getListOfJobs();
    }


    private void initUI() {
        tvUsername = findViewById(R.id.tvUsername);

        tvListOfJobsBtn = findViewById(R.id.tvJobListbtn);
        tvListOfJobsBtn.setOnClickListener(this);

        tvListOfPostedJobsBtn = findViewById(R.id.tvListOfPostedJobsBtn);
        tvListOfPostedJobsBtn.setOnClickListener(this);

        tvPostJob = findViewById(R.id.tvPostJobNavigation);
        tvPostJob.setOnClickListener(this);

        drawerLayout = findViewById(R.id.dlFilterDrawer);
        navFilter = findViewById(R.id.navigation_view);

        ivFilter = findViewById(R.id.ivFilter);
        ivFilter.setOnClickListener(this);

        tvPickLocation = findViewById(R.id.tvPickLocation);
        tvPickLocation.setOnClickListener(this);

        tvPickExperienceLevel = findViewById(R.id.tvPickExperienceLevel);
        tvPickExperienceLevel.setOnClickListener(this);

        llLogoutLogin = findViewById(R.id.llLogoutLogin);
        llLogoutLogin.setOnClickListener(this);

        llViewPagerBottomNavigation = findViewById(R.id.llViewPagerBottomnavigation);

        llUser = findViewById(R.id.llUser);

        tvLogoutLogin = findViewById(R.id.tvLogOut);

        ivLogOutLogin = findViewById(R.id.ivLoginLogout);
        ivLogOutLogin.setColorFilter(this.getResources().getColor(R.color.blue));
        if (AdsApp.getLocalData().getLoggedUser() != null) {
            tvLogoutLogin.setText(getString(R.string.log_out));
            ivLogOutLogin.setImageDrawable(this.getResources().getDrawable(R.mipmap.logout));
            ivLogOutLogin.setColorFilter(this.getResources().getColor(R.color.blue));
            tvUsername.setText(AdsApp.getLocalData().getLoggedUser().getName());
        } else {
            llUser.setVisibility(View.GONE);
            llViewPagerBottomNavigation.setVisibility(View.GONE);
        }

        cbFullTime = findViewById(R.id.cbFullTime);

        cbPartTime = findViewById(R.id.cbPartTime);

        tvTitle = findViewById(R.id.tvTitle);

        viewPager = findViewById(R.id.vpViewPager);
        jobsListFragmentPagerAdapter = new JobsListFragmentPagerAdapter(getSupportFragmentManager(), jobList);
        viewPager.setAdapter(jobsListFragmentPagerAdapter);
        viewPager.setPageMargin(10);
        viewPager.addOnPageChangeListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivFilter:
                openMenu();
                break;
            case R.id.tvPickLocation:
                CustomDialog customDialogPlaces = new CustomDialog(this, Arrays.asList(getResources().getStringArray(R.array.places)), listOfFilterItems -> {

                });
                customDialogPlaces.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                customDialogPlaces.show();
                break;
            case R.id.tvPickExperienceLevel:
                ExperienceLevelDialog experienceLevelDialog = new ExperienceLevelDialog(this);
                experienceLevelDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                experienceLevelDialog.show();
                break;
            case R.id.llLogoutLogin:
                if (AdsApp.getLocalData().getLoggedUser() != null) {
                    doLogout();
                } else {
                    doLogin();
                }
                break;
            case R.id.tvPostJobNavigation:
                Intent intent = new Intent(ListofJobsActivity.this, PostJobActivity.class);
                startActivity(intent);
                break;
            case R.id.tvJobListbtn:
                viewPager.setCurrentItem(0);
                break;
            case R.id.tvListOfPostedJobsBtn:
                viewPager.setCurrentItem(1);
                break;
        }
    }

    private void openMenu() {
        if (drawerLayout != null) {
            drawerLayout.openDrawer(GravityCompat.END, true);
        }
    }

    private void closeMenu() {
        if (drawerLayout != null) {
            drawerLayout.closeDrawer(GravityCompat.END, true);
        }
    }


    private void doLogin() {
        Intent intent = new Intent(ListofJobsActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void doLogout() {
        RestManager.getInstance().getService(Logout.class).logout(AdsApp.getLocalData().getLoggedUser()).enqueue(new Callback<ResponseMessage>() {
            @Override
            public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                Toast.makeText(ListofJobsActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                doLogin();
            }

            @Override
            public void onFailure(Call<ResponseMessage> call, Throwable t) {
                Toast.makeText(ListofJobsActivity.this, "Error " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void getListOfJobs() {
        RestManager.getInstance().getService(GetJobs.class).getAllJobs().enqueue(new Callback<List<Job>>() {
            @Override
            public void onResponse(Call<List<Job>> call, Response<List<Job>> response) {
                jobList.clear();
                jobList.addAll(response.body());
                jobsListFragmentPagerAdapter.getJobsListFragment().setJobList(jobList);
            }

            @Override
            public void onFailure(Call<List<Job>> call, Throwable t) {
                Toast.makeText(ListofJobsActivity.this, "Error " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == 0) {
            tvTitle.setText(getString(R.string.jobs));
            tvListOfJobsBtn.setBackground(getResources().getDrawable(R.drawable.selected_toggle_button));
            tvListOfJobsBtn.setTextColor(getResources().getColor(R.color.white));

            tvListOfPostedJobsBtn.setBackground(getResources().getDrawable(R.drawable.not_selected_toggle_button));
            tvListOfPostedJobsBtn.setTextColor(getResources().getColor(R.color.black));
        } else {
            tvTitle.setText(getString(R.string.posted_jobs));
            tvListOfPostedJobsBtn.setBackground(getResources().getDrawable(R.drawable.selected_toggle_button));
            tvListOfPostedJobsBtn.setTextColor(getResources().getColor(R.color.white));

            tvListOfJobsBtn.setBackground(getResources().getDrawable(R.drawable.not_selected_toggle_button));
            tvListOfJobsBtn.setTextColor(getResources().getColor(R.color.black));
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
