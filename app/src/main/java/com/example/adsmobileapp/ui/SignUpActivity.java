package com.example.adsmobileapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adsmobileapp.AdsApp;
import com.example.adsmobileapp.R;
import com.example.adsmobileapp.rest.RestManager;
import com.example.adsmobileapp.rest.endpoints.AddUser;
import com.example.adsmobileapp.rest.model.User;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends BaseActivity implements View.OnClickListener {
    private TextInputEditText etEmail;
    private TextInputEditText etName;
    private TextInputEditText etLastName;
    private TextInputEditText etPassword;
    private TextInputEditText etRepeatPassword;


    @Override
    protected int provideLayout() {
        return R.layout.activity_sign_up;
    }

    @Override
    protected boolean hasHeader() {
        return true;
    }

    @Override
    protected String getHeaderTitle() {
        return "Sign up";
    }

    @Override
    protected boolean hasMainButton() {
        return true;
    }

    @Override
    protected String getMainButtonTitle() {
        return "Sign up";
    }

    @Override
    protected void layoutReady() {
        initUI();
    }

    @Override
    protected void getExtras() {

    }


    private void initUI() {
        etEmail = findViewById(R.id.etSignUpEmail);
        etName = findViewById(R.id.etName);
        etLastName = findViewById(R.id.etLastName);
        etPassword = findViewById(R.id.etSignUpPassword);
        etRepeatPassword = findViewById(R.id.etSignUpRepeatPassword);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvMainButton:
                doSingUp();
                break;
            case R.id.ivBackButton:
                onBackPressed();
                break;
        }
    }

    private void doSingUp() {
        if (validation()) {
            User user = new User(etName.getText().toString(), etLastName.getText().toString(), etEmail.getText().toString(), etPassword.getText().toString());
            RestManager.getInstance().getService(AddUser.class).addUser(user).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    Toast.makeText(SignUpActivity.this, "You created account successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(SignUpActivity.this, "Error message: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("ASD", "onFailure: " + t.getMessage());
                }
            });


        }
    }

    private boolean validation() {
        boolean isValid = true;
        if (etEmail.getText().toString().isEmpty() && !Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString()).matches()) {
            etEmail.setError("Enter correct email");
            isValid = false;
        }
        if (etName.getText().toString().isEmpty()) {
            etName.setError("Enter name");
            isValid = false;
        }
        if (etLastName.getText().toString().isEmpty()) {
            etLastName.setError("Enter lastname");
            isValid = false;
        }
        if (etPassword.getText().toString().isEmpty()) {
            etPassword.setError("Enter password");
            isValid = false;
        }
        if (etRepeatPassword.getText().toString().isEmpty() || !etPassword.getText().toString().equals(etRepeatPassword.getText().toString())) {
            etRepeatPassword.setError("Enter correct repeat password");
            isValid = false;
        }

        return isValid;
    }
}
