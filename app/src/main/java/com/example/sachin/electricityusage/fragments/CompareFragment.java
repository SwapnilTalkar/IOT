package com.example.sachin.electricityusage.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.sachin.electricityusage.R;

import java.util.ArrayList;


public class CompareFragment extends Fragment{

    CheckBox january,february,march,april;
    Button compareButton;


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.compare, container,false);

        january = (CheckBox) view.findViewById(R.id.january);
        february = (CheckBox) view.findViewById(R.id.february);
        march = (CheckBox) view.findViewById(R.id.march);
        april = (CheckBox) view.findViewById(R.id.april);

        compareButton =(Button) view.findViewById(R.id.compareButton);

        compareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<String> months = new ArrayList<String>();
                if (january.isChecked()){
                    months.add("january");
                }if (february.isChecked()){
                    months.add("february");
                }if (march.isChecked()){
                    months.add("march");
                }if (april.isChecked()){
                    months.add("april");
                }

                if(months.size()>=2){
                    MultiLineGraphFragment multiLineGraph = new MultiLineGraphFragment();
                    Bundle args = new Bundle();
                    args.putStringArrayList("Months", months);
                    multiLineGraph.setArguments(args);


                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.compare_fragment, multiLineGraph);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }else {
                    Toast.makeText(getActivity().getApplicationContext(), "Please select atleast two month",Toast.LENGTH_LONG).show();
                }

            }
        });

        return view;
    }
}
