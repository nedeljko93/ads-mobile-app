package com.example.adsmobileapp.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adsmobileapp.R;
import com.example.adsmobileapp.ui.adapters.DialogRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class CustomDialog extends Dialog implements View.OnClickListener {
    private EditText etSearch;
    private TextView tvChooseBtn;
    private RecyclerView rvCustomDialog;
    private DialogRecyclerAdapter dialogRecyclerAdapter;
    private List<String> stringList;
    private OnDialogBtnClick onDialogBtnClick;
    private ImageView ivClose;

    public CustomDialog(@NonNull Context context, List<String> stringList, OnDialogBtnClick onDialogBtnClick) {
        super(context);
        this.stringList = stringList;
        this.onDialogBtnClick = onDialogBtnClick;
    }

    public interface OnDialogBtnClick {
        void onClick(List<String> listOfFilterItems);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_custom);
        getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        initUI();
        initRecyclerView();
        initSearchBar();
    }

    private void initUI() {
        etSearch = findViewById(R.id.etSearchDialog);

        tvChooseBtn = findViewById(R.id.tvPickBtn);
        tvChooseBtn.setOnClickListener(this);

        ivClose = findViewById(R.id.ivClose);
        ivClose.setOnClickListener(this);
    }

    private void initRecyclerView() {
        rvCustomDialog = findViewById(R.id.rvDialogItems);
        dialogRecyclerAdapter = new DialogRecyclerAdapter(stringList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvCustomDialog.setLayoutManager(linearLayoutManager);
        rvCustomDialog.setAdapter(dialogRecyclerAdapter);

    }

    private void initSearchBar() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
    }

    private void filter(String text) {
        if (!text.equals("")) {
            List<String> filtredItems = new ArrayList<>();
            for (String string : stringList) {
                if (string.toLowerCase().contains(text.toLowerCase())) {
                    filtredItems.add(string);

                }
            }
            if (filtredItems.size() != 0) {
                dialogRecyclerAdapter.setDialogItems(filtredItems);
            }
        } else {
            dialogRecyclerAdapter.setDialogItems(stringList);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvPickBtn:
                onDialogBtnClick.onClick(dialogRecyclerAdapter.getSelectedDialogItems());
                dismiss();
                break;
            case R.id.ivClose:
                dismiss();
                break;
        }
    }
}
