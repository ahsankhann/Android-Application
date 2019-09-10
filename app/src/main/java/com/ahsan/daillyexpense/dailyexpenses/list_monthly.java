package com.ahsan.daillyexpense.dailyexpenses;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

public class list_monthly extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_monthly);
        getActionBar().hide();
    }
}
