package com.example.adsmobileapp.ui;

import android.content.Intent;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

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
        return getString(R.string.sign_up);
    }

    @Override
    protected boolean hasMainButton() {
        return true;
    }

    @Override
    protected String getMainButtonTitle() {
        return getString(R.string.sign_up);
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
                    Toast.makeText(SignUpActivity.this, getString(R.string.succes_msg), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(SignUpActivity.this,  t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });


        }
    }

    private boolean validation() {
        boolean isValid = true;
        if (etEmail.getText().toString().isEmpty() && !Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString()).matches()) {
            etEmail.setError(getString(R.string.email_validation_msg));
            isValid = false;
        }
        if (etName.getText().toString().isEmpty()) {
            etName.setError(getString(R.string.name_validation_msg));
            isValid = false;
        }
        if (etLastName.getText().toString().isEmpty()) {
            etLastName.setError(getString(R.string.lastname_validation_msg));
            isValid = false;
        }
        if (etPassword.getText().toString().isEmpty()) {
            etPassword.setError(getString(R.string.password_validation_msg));
            isValid = false;
        }
        if (etRepeatPassword.getText().toString().isEmpty() || !etPassword.getText().toString().equals(etRepeatPassword.getText().toString())) {
            etRepeatPassword.setError(getString(R.string.repeat_password_validation_msg));
            isValid = false;
        }

        return isValid;
    }
}
