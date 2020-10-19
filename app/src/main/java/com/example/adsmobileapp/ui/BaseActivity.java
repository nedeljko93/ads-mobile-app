package com.example.adsmobileapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.adsmobileapp.R;

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    protected TextView tvTitle;
    protected TextView tvMainButton;
    protected View vHeaderLine;

    private FrameLayout flMainFrame;
    private ImageView ivBack;

    private boolean hasHader;
    private boolean hasMainButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

        getExtras();
        hasHader = hasHeader();
        hasMainButton = hasMainButton();
        setContentView(provideLayout());
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(R.layout.activity_base);
        flMainFrame = findViewById(R.id.flMain);
        flMainFrame.addView(LayoutInflater.from(this).inflate(layoutResID, flMainFrame, false));

        initHeader();
        initMainButton();
        layoutReady();
        initBackButton();
    }

    private void initBackButton() {
        ivBack = findViewById(R.id.ivBackButton);
        ivBack.setOnClickListener(this);
    }

    private void initHeader() {
        tvTitle = findViewById(R.id.tvJobTitle);
        if (hasHader) {
            tvTitle.setText(getHeaderTitle());
        } else {
            tvTitle.setVisibility(View.GONE);

            vHeaderLine = findViewById(R.id.vHeaderHorizontalLine);
            vHeaderLine.setVisibility(View.GONE);
        }
    }

    private void initMainButton() {
        tvMainButton = findViewById(R.id.tvMainButton);
        tvMainButton.setOnClickListener(this);
        if (hasMainButton) {
            tvMainButton.setText(getMainButtonTitle());
        } else {
            tvMainButton.setVisibility(View.GONE);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }

    protected abstract @LayoutRes
    int provideLayout();

    protected abstract boolean hasHeader();

    protected abstract String getHeaderTitle();

    protected abstract boolean hasMainButton();

    protected abstract String getMainButtonTitle();

    protected abstract void layoutReady();

    protected abstract void getExtras();
}
