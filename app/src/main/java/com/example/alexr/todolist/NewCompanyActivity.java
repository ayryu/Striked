package com.example.alexr.todolist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewCompanyActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.android.companylistsql.REPLY";

    private EditText mEditCompanyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_company);

        mEditCompanyView = findViewById(R.id.edit_company);

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(mEditCompanyView.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String company = mEditCompanyView.getText().toString(); //changed word to company
                    replyIntent.putExtra(EXTRA_REPLY, company); //changed word to company
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }
}
