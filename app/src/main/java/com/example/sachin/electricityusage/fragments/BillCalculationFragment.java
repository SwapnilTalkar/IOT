package com.example.sachin.electricityusage.fragments;

import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sachin.electricityusage.R;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BillCalculationFragment extends Fragment{

    EditText editText;
    Button button;
    TextView fixedcharge,wheelingCharge,energyChargee,regAsscharge,fuelAdjChargee,totalBill,bill;
    LinearLayout bill_layout;
    
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bill_calculation,container,false);

        editText = (EditText) view.findViewById(R.id.unitsConsumed);
        button = (Button) view.findViewById(R.id.calculate);
        bill_layout = (LinearLayout) view.findViewById(R.id.bill_layout);
        bill_layout.setVisibility(View.INVISIBLE);

        fixedcharge = (TextView)view.findViewById(R.id.fixedcharge);
        wheelingCharge = (TextView)view.findViewById(R.id.wheelingCharge);
        energyChargee = (TextView)view.findViewById(R.id.energyCharge);
        regAsscharge = (TextView)view.findViewById(R.id.regAsscharge);
        fuelAdjChargee = (TextView)view.findViewById(R.id.fuelAdjCharge);
        totalBill = (TextView)view.findViewById(R.id.totalBill);
        bill = (TextView)view.findViewById(R.id.bill);

        fixedcharge.setTextSize(20);
        wheelingCharge.setTextSize(20);
        energyChargee.setTextSize(20);
        regAsscharge.setTextSize(20);
        fuelAdjChargee.setTextSize(20);
        totalBill.setTextSize(20);
        totalBill.setTypeface(null, Typeface.BOLD);
        bill.setTypeface(null,Typeface.BOLD);


        editText.setText("287");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editText.getText().toString().length() == 0){
                    System.out.print("In ");
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity().getApplicationContext());
                    alertDialogBuilder.setTitle("Please Enter the no of Units Consumed");
                    alertDialogBuilder.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {

                                }
                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }else {

                    int unitsConsumed = Integer.parseInt(editText.getText().toString());
                    int fixedCharge = 0;
                    double energyCharge = 0;
                    double wheeelingCharge = 1.80;
                    double regAssCharge = 0;
                    double fuelAdjCharge = 0;
                    double totalBillAmount = 0;

                    if (unitsConsumed>=0 && unitsConsumed<= 100){
                        fixedCharge = 50;
                        energyCharge = 2.43;
                        regAssCharge=0.56;
                        fuelAdjCharge = 0.1723;

                    }else if (unitsConsumed>100 && unitsConsumed<= 300){
                        fixedCharge= 75;
                        energyCharge= 3.99;
                        regAssCharge=0.75;
                        fuelAdjCharge = 0.2462;

                    }else if (unitsConsumed>300 && unitsConsumed<= 500){
                        fixedCharge= 75;
                        energyCharge= 5.57;
                        regAssCharge = 0.89;
                        fuelAdjCharge = 0.2990;

                    }else if (unitsConsumed>500){
                        fixedCharge = 100;
                        energyCharge= 7.21;
                        regAssCharge= 1.07;
                        fuelAdjCharge = 0.3573;

                    }

                    wheeelingCharge = (wheeelingCharge * unitsConsumed);
                    energyCharge = (energyCharge * unitsConsumed);
                    regAssCharge = (regAssCharge * unitsConsumed);
                    fuelAdjCharge = (fuelAdjCharge * unitsConsumed);


                    wheeelingCharge = round(wheeelingCharge,2);
                    energyCharge = round(energyCharge,2);
                    regAssCharge = round(regAssCharge,2);
                    fuelAdjCharge = round(fuelAdjCharge,2);
                    totalBillAmount = fixedCharge + wheeelingCharge + energyCharge + regAssCharge + fuelAdjCharge;
                    totalBillAmount = round(totalBillAmount,2);



                    fixedcharge.setText(fixedCharge+"");
                    wheelingCharge.setText(wheeelingCharge+"");
                    energyChargee.setText(energyCharge+"");
                    regAsscharge.setText(regAssCharge+"");
                    fuelAdjChargee.setText(fuelAdjCharge+"");
                    totalBill.setText(totalBillAmount+"");
                    bill_layout.setVisibility(View.VISIBLE);


                }

            }
        });
        button.performClick();
        return view;
    }



    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
