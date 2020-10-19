package com.example.adsmobileapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.adsmobileapp.AdsApp;
import com.example.adsmobileapp.R;
import com.example.adsmobileapp.rest.RestManager;
import com.example.adsmobileapp.rest.endpoints.GetAllUsers;
import com.example.adsmobileapp.rest.endpoints.Login;
import com.example.adsmobileapp.rest.model.RequestLogin;
import com.example.adsmobileapp.rest.model.User;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private TextInputEditText etEmail;
    private TextInputEditText etPassword;
    private TextView tvLoginBtn;
    private TextView tvSignUp;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }

    @Override
    protected int provideLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected boolean hasHeader() {
        return true;
    }

    @Override
    protected String getHeaderTitle() {
        return "Login";
    }

    @Override
    protected boolean hasMainButton() {
        return false;
    }

    @Override
    protected String getMainButtonTitle() {
        return null;
    }

    @Override
    protected void layoutReady() {
        initUI();
    }

    @Override
    protected void getExtras() {

    }

    private void initUI() {
        etPassword = findViewById(R.id.etPassword);
        etEmail = findViewById(R.id.etEmail);

        tvLoginBtn = findViewById(R.id.tvLogin);
        tvLoginBtn.setOnClickListener(this);

        tvSignUp = findViewById(R.id.tvSignUp);
        tvSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.tvLogin:
                doLogin();
                break;
            case R.id.tvSignUp:
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
                overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
                break;
            case R.id.ivBackButton:
                onBackPressed();
                break;
        }
    }

    private void doLogin() {
        if (validation()) {
            RequestLogin requestLogin = new RequestLogin(etEmail.getText().toString(), etPassword.getText().toString());
            RestManager.getInstance().getService(Login.class).login(requestLogin).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    AdsApp.getLocalData().setLoggedUser(response.body());
                    if (requestLogin.getEmail().equals(response.body().getEmail()) && requestLogin.getPassword().equals(response.body().getPassword())) {
                        Intent intent = new Intent(LoginActivity.this, ListofJobsActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
                    } else {
                        Toast.makeText(LoginActivity.this, "Enter valid credentials", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, "Error " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    private boolean validation() {
        boolean isValid = true;
        if (etEmail.getText().toString().isEmpty() && !Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString()).matches()) {
            etEmail.setError("Enter email");
            isValid = false;
        }
        if (etPassword.getText().toString().isEmpty()) {
            etPassword.setError("Enter password");
            isValid = false;
        }
        return isValid;
    }
}