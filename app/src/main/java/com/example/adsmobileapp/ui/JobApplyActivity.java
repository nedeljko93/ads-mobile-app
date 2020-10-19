package com.example.adsmobileapp.ui;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.adsmobileapp.R;
import com.example.adsmobileapp.rest.model.Job;
import com.example.adsmobileapp.utils.Constants;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;

public class JobApplyActivity extends BaseActivity implements View.OnClickListener {
    public static final int PICK_CV_REQUEST_CODE = 1;
    public static final int PICK_COVER_LETTER = 2;

    private TextInputEditText etNameAndLastname;
    private TextInputEditText etEmail;
    private TextInputEditText etPhoneNumber;
    private ImageView ivCv;
    private ImageView ivCoverLetter;
    private TextView tvCvName;
    private TextView tvCoverLetter;
    private String filename;
    private File cvFile;
    private File coverLetterFile;
    private Job job;


    @Override
    protected int provideLayout() {
        return R.layout.activity_job_apply;
    }

    @Override
    protected boolean hasHeader() {
        return true;
    }

    @Override
    protected String getHeaderTitle() {
        return getString(R.string.apply_for_the_job, job.getTitle());
    }

    @Override
    protected boolean hasMainButton() {
        return true;
    }

    @Override
    protected String getMainButtonTitle() {
        return getString(R.string.apply);
    }

    @Override
    protected void layoutReady() {
        initUI();
    }

    @Override
    protected void getExtras() {
        if (getIntent().hasExtra(Constants.EXTRA_JOB)) {
            job = (Job) getIntent().getSerializableExtra(Constants.EXTRA_JOB);
        }
    }

    private void initUI() {
        ivCoverLetter = findViewById(R.id.ivCoverLetter);
        ivCoverLetter.setOnClickListener(this);

        ivCv = findViewById(R.id.ivCv);
        ivCv.setOnClickListener(this);

        etNameAndLastname = findViewById(R.id.etNameAndLastName);
        etEmail = findViewById(R.id.etEmail);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);

        tvCoverLetter = findViewById(R.id.tvCoverLetterName);
        tvCvName = findViewById(R.id.tvCvName);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivCv:
                openFilePicker(PICK_CV_REQUEST_CODE);
                break;
            case R.id.ivCoverLetter:
                openFilePicker(PICK_COVER_LETTER);
                break;
            case R.id.tvMainButton:
                break;
            case R.id.ivBackButton:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
            if (requestCode == PICK_CV_REQUEST_CODE) {
                cvFile = new File(uri.getPath());
                tvCvName.setText(getFileName(uri));
            }
            if (requestCode == PICK_COVER_LETTER) {
                coverLetterFile = new File(uri.getPath());
                tvCoverLetter.setText(getFileName(uri));
            }
        }

    }

    private String getFileName(Uri uri) {
        String uriString = uri.toString();
        File myFile = new File(uriString);
        String displayName = null;

        if (uriString.startsWith("content://")) {
            Cursor cursor = null;
            try {
                cursor = this.getContentResolver().query(uri, null, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        } else if (uriString.startsWith("file://")) {
            displayName = myFile.getName();
        }
        return displayName;
    }

    private void openFilePicker(int requestCode) {
        String[] supportedMimeTypes = {"application/pdf", "application/msword", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"};
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            intent.setType(supportedMimeTypes.length == 1 ? supportedMimeTypes[0] : "*/*");
            if (supportedMimeTypes.length > 0) {
                intent.putExtra(Intent.EXTRA_MIME_TYPES, supportedMimeTypes);
            }
        } else {
            String mimeTypes = "";
            for (String mimeType : supportedMimeTypes) {
                mimeTypes += mimeType + "|";
            }
            intent.setType(mimeTypes.substring(0, mimeTypes.length() - 1));
        }
        startActivityForResult(intent, requestCode);
    }

}
