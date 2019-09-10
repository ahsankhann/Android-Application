package com.ahsan.daillyexpense.dailyexpenses;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText _DisplayDate, _Edtfood, _Edtpetrol, _Edtciggrete, _Edtothers;
    Button btnSave, btnCancel;
    SqlLiteDatabase db;
    DatePickerDialog.OnDateSetListener setDateListener;
    AlertDialog alertDialog;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.appbarr);


        _DisplayDate = (EditText) findViewById(R.id.DatePickerDaily);
        _Edtfood = (EditText) findViewById(R.id.foodedt);
        _Edtpetrol = (EditText) findViewById(R.id.petroledt);
        _Edtciggrete = (EditText) findViewById(R.id.cigaretteedt);
        _Edtothers = (EditText) findViewById(R.id.otheredt);
        btnSave = (Button) findViewById(R.id.savebtn);
        btnCancel = (Button)findViewById(R.id.calcelbtn);
        db = new SqlLiteDatabase(this);
        _DisplayDate.setInputType(InputType.TYPE_NULL);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        _DisplayDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                _DisplayDate.setInputType(InputType.TYPE_NULL);
                if(hasFocus){
                    Calendar Cal = Calendar.getInstance();
                    int year = Cal.get(Calendar.YEAR);
                    int month = Cal.get(Calendar.MONTH);
                    int Day = Cal.get(Calendar.DAY_OF_MONTH);


                    DatePickerDialog DPD = new DatePickerDialog(
                            MainActivity.this,
                            R.style.Theme_AppCompat_Light_Dialog,
                            setDateListener,
                            year, month, Day);
                    DPD.getWindow();
                    DPD.show();
                }

            }
        });
        _DisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _DisplayDate.setInputType(InputType.TYPE_NULL);
                Calendar Cal = Calendar.getInstance();
                int year = Cal.get(Calendar.YEAR);
                int month = Cal.get(Calendar.MONTH);
                int Day = Cal.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog DPD = new DatePickerDialog(
                        MainActivity.this,
                        R.style.Theme_AppCompat_Light_Dialog,
                        setDateListener,
                        year, month, Day);
                DPD.getWindow();
                DPD.show();

            }
        });
setDateListener = new DatePickerDialog.OnDateSetListener() {
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        int months = month + 1;
        String DateComplete = dayOfMonth + "/" + months + "/" + year;

        _DisplayDate.setText(DateComplete);
    }
};

        btnSave.setOnClickListener(this);


    }

    @Override
    public synchronized  void onClick(View v) {
        switch(v.getId()) {
            case R.id.savebtn:
                if(_DisplayDate.getText().toString() == ""){
                    _DisplayDate.isFocused();
                    return;
                }
                boolean fine = true;
                try {
                    String _date = _DisplayDate.getText().toString();
                    String _food;
                    if(_Edtfood.getText().toString().equals(""))
                        _food = "00";
                    else
                        _food= _Edtfood.getText().toString();
                    String _petrol;
                    if(_Edtpetrol.getText().toString().equals(""))
                        _petrol = "00";
                    else
                        _petrol = _Edtpetrol.getText().toString();

                    String _ciggrete;
                    if(_Edtciggrete.getText().toString().equals(""))
                        _ciggrete = "00";
                    else
                        _ciggrete= _Edtciggrete.getText().toString();
                    String _others;
                    if (_Edtothers.getText().toString().equals(""))
                        _others = "00";
                    else
                        _others = _Edtothers.getText().toString();
                    SaveData(_date, _food, _petrol, _ciggrete, _others);
                    //Toast.makeText(getApplicationContext(), _date + "|" + _food +"|"+_petrol+"|"+_ciggrete+"|"+_others , Toast.LENGTH_SHORT).show();
                } catch (Exception ex) {
                    fine = false;
                    String error = ex.toString();

                }

        }
    }

    private void SaveData(String _date, String _food, String _petrol, String _ciggrete, String _others){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        try {
            //Toast.makeText(getApplicationContext(), _date.toString(), Toast.LENGTH_SHORT).show();
            int Result = db.CheckExpense(_date);
            //Toast.makeText(getApplicationContext(), _date.toString(), Toast.LENGTH_SHORT).show();
            //int Result = 1;
            Toast.makeText(getApplicationContext(), String.valueOf(Result), Toast.LENGTH_SHORT).show();
            if(Result == 0){
            long Ret = db.insertExpense(_date, _food, _petrol, _ciggrete, _others);
            builder.setTitle("Success");
            builder.setMessage(_date + " Save Successfully!");
            builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    onBackPressed();
                    alertDialog.dismiss();
                }
            });
            alertDialog = builder.create();
            alertDialog.show();
            }
            else if(Result == 1){
                builder.setTitle("Failed Error");
                builder.setMessage("Already Save Expense this Date: " + _date + ". Please try another Date!");
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        _DisplayDate.isFocused();
                        alertDialog.dismiss();
                    }
                });
                alertDialog = builder.create();
                alertDialog.show();
            }
        }catch (Exception ex){
            Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
