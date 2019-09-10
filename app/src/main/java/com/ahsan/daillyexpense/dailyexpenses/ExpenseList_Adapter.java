package com.ahsan.daillyexpense.dailyexpenses;

import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

public class ExpenseList_Adapter extends RecyclerView.Adapter<ExpenseList_Adapter.AdapterHolder> {
    Expenses[] expenses;
    Context context;
    AlertDialog alertDialog;
    int FoodT= 0, PetrolT= 0, CiggrteT= 0, OtherT = 0;
    Double TotalT = 0.00;
    public ExpenseList_Adapter(Expenses[] expenses, Context context){
        this.expenses = expenses;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.listview_expenses, viewGroup,false);
        return new AdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterHolder adapterHolder, int i) {
        Expenses exp = expenses[i];
        Double totalExp = Double.valueOf(exp.Food) + Double.valueOf(exp.Petrol) + Double.valueOf(exp.Ciggrete) + Double.valueOf(exp.Others);
        /*FoodT = FoodT + Integer.valueOf(exp.Food);
        PetrolT = PetrolT + Integer.valueOf(exp.Petrol);
        CiggrteT = CiggrteT + Integer.valueOf(exp.Ciggrete);
        OtherT = OtherT + Integer.valueOf(exp.Others);
        TotalT = TotalT + totalExp;*/

        String[] SplitDate = exp.ID.split("/");
        adapterHolder.txtLongDate.setText(exp.ID);
        adapterHolder.txtFood.setText(exp.Food);
        adapterHolder.txtPetrol.setText(exp.Petrol);
        adapterHolder.txtCiggrte.setText(exp.Ciggrete);
        adapterHolder.txtOthers.setText(exp.Others);
        adapterHolder.txtTotal.setText(totalExp.toString());
        adapterHolder.txtDate.setText(SplitDate[0]);
        final String Dates = expenses[i].ID;
        final String Totalss = adapterHolder.txtTotal.getText().toString();
        adapterHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, Dates + " - " + Totalss, Toast.LENGTH_SHORT).show();
            }
        });

        /*if(i == expenses.length){
            ExpenseList_Activity._txtFood.setText(String.valueOf(FoodT));
            ExpenseList_Activity._txtPetrol.setText(String.valueOf(PetrolT));
            ExpenseList_Activity._txtCiggrete.setText(String.valueOf(CiggrteT));
            ExpenseList_Activity._txtOthers.setText(String.valueOf(OtherT));
            ExpenseList_Activity._txtTotal.setText(TotalT.toString());
            ExpenseList_Activity._txtDays.setText(String.valueOf(expenses.length));
        }*/
    }

    @Override
    public int getItemCount() {
        return expenses.length;
    }

    public class AdapterHolder extends RecyclerView.ViewHolder {
        TextView txtTotal, txtFood, txtPetrol, txtCiggrte, txtOthers, txtLongDate, txtDate;
        public AdapterHolder(View view) {
            super(view);
            txtDate = (TextView) view.findViewById(R.id.Datetxt);
            txtTotal = (TextView) view.findViewById(R.id.Totaltxt);
            txtFood = (TextView) view.findViewById(R.id.Foodtxt);
            txtPetrol = (TextView) view.findViewById(R.id.Petroltxt);
            txtCiggrte = (TextView) view.findViewById(R.id.Cigarettetxt);
            txtOthers = (TextView) view.findViewById(R.id.Othertxt);
            txtLongDate = (TextView) view.findViewById(R.id.ComplDatetxt);
        }

    }
}
