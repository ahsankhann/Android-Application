package com.ahsan.daillyexpense.dailyexpenses;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ExpenseList_Activity extends AppCompatActivity {
    SqlLiteDatabase db;
    TextView txtShow;
    ListView LV;
    Gson gson;
    Integer _totalFood= 0, _totalPetrol = 0, _totalCiggrete = 0, _totalOthers = 0, _Total = 0, _Days = 0;
    public static TextView _txtDays, _txtFood, _txtPetrol, _txtCiggrete, _txtOthers, _txtTotal;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_list_);
        _txtDays = (TextView) findViewById(R.id.totaldaystxt);
        _txtFood = (TextView) findViewById(R.id.totalfoodtxt);
        _txtPetrol = (TextView) findViewById(R.id.totalPetroltxt);
        _txtCiggrete = (TextView) findViewById(R.id.totalciggtxt);
        _txtOthers = (TextView) findViewById(R.id.totalothers);
        _txtTotal = (TextView) findViewById(R.id.totalExpens);
        recyclerView = (RecyclerView) findViewById(R.id.RecylerViewExp);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ShowData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int res_id = item.getItemId();
        if(res_id == R.id.addbtntoolbar){
            Intent IT = new Intent(ExpenseList_Activity.this, MainActivity.class);
            startActivity(IT);
        }
        if(res_id == R.id.listviewbtn){
            Intent IT = new Intent(ExpenseList_Activity.this, list_monthly.class);
            startActivity(IT);
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        ShowData();
    }

    private void ShowData() {
        db = new SqlLiteDatabase(this);
        gson = new Gson();
        JSONArray returnData = db.ArrayReturn();


        List<HashMap<String, String>> ListMap = new ArrayList<>();

        /*SimpleAdapter adapter = new SimpleAdapter(this, ListMap,
                android.R.layout.simple_list_item_2,
                new String[] {"First Line", "Second Line" },
                new int[] {android.R.id.text1, android.R.id.text2 });*/

        //Type founderListType = new TypeToken<ArrayList<Expenses>>(){}.getType();

        Expenses[] ExpeneList = gson.fromJson(returnData.toString(), Expenses[].class);
        if(ExpeneList.length > 0)
            recyclerView.setAdapter(new ExpenseList_Adapter(ExpeneList, ExpenseList_Activity.this));
        //List<Expenses> ExpeneList = (List<Expenses>) gson.fromJson(returnData.toString(),Expenses);
        _totalFood = 0;_totalPetrol = 0; _totalCiggrete = 0; _totalOthers=0; _Total=0;
        for(Expenses Expens : ExpeneList){
//            if(_Days == ExpeneList.length) {
//                HashMap<String, String> HMap = new HashMap<>();
                _totalFood += Integer.parseInt(Expens.Food);
                _totalPetrol = _totalPetrol + Integer.valueOf(Expens.Petrol);
                _totalCiggrete = _totalCiggrete + Integer.valueOf(Expens.Ciggrete);
                _totalOthers = _totalOthers + Integer.valueOf(Expens.Others);
                //_Total = _Total + _totalFood + _totalPetrol + _totalCiggrete + _totalOthers;

                //Toast.makeText(getApplicationContext(), _Days.toString(), Toast.LENGTH_LONG).show();
                Integer DTotal = Integer.valueOf(Expens.Food) + Integer.valueOf(Expens.Petrol) + Integer.valueOf(Expens.Ciggrete) + Integer.valueOf(Expens.Others);
                _Total += DTotal;
                //Toast.makeText(getApplicationContext(), _Total.toString(), Toast.LENGTH_LONG).show();
//                String Data = "Food: " + Expens.Food + "  |    Petrol: " + Expens.Petrol + "  |    Cigarette: " + Expens.Ciggrete + "  |    Others: " + Expens.Others + "  |    Total: " + DTotal.toString();
//                HMap.put("First Line", Expens.ID);
//                HMap.put("Second Line", Data);
//                ListMap.add(HMap);
//            }
//            _Days++;
        }

//        LV.setAdapter(adapter);
        try{
        _txtFood.setText(_totalFood.toString());
        _txtPetrol.setText(_totalPetrol.toString());
        _txtCiggrete.setText(_totalCiggrete.toString());
        _txtOthers.setText(_totalOthers.toString());
        _txtTotal.setText(_Total.toString());
        }catch (Exception ex){

        }
        _txtDays.setText(String.valueOf(ExpeneList.length));
    }
}
