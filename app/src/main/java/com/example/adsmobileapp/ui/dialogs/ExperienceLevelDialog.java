package com.example.adsmobileapp.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.adsmobileapp.R;

public class ExperienceLevelDialog extends Dialog implements View.OnClickListener {
    private CheckBox cbJunior;
    private CheckBox cbMedior;
    private CheckBox cbSenior;

    private TextView tvPickBtn;

    private ImageView ivClose;

    public ExperienceLevelDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_experience_level);
        getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        initUI();
    }

    private void initUI() {
        cbJunior = findViewById(R.id.cbJunior);
        cbSenior = findViewById(R.id.cbSenior);
        cbMedior = findViewById(R.id.cbMedior);

        tvPickBtn = findViewById(R.id.tvPick);
        tvPickBtn.setOnClickListener(this);

        ivClose = findViewById(R.id.ivClose);
        ivClose.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvPick:
                break;
            case R.id.ivClose:
                dismiss();
                break;
        }
    }
}
