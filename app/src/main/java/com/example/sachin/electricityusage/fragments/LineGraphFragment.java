package com.example.sachin.electricityusage.fragments;


import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.android.volley.VolleyError;
import com.example.sachin.electricityusage.constants.Constants;
import com.example.sachin.electricityusage.interfaces.ResponseListener;
import com.example.sachin.electricityusage.models.January;
import com.example.sachin.electricityusage.R;
import com.example.sachin.electricityusage.network.VolleyCallBack;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class LineGraphFragment extends Fragment implements ResponseListener {
    private LineChart chart1;
    private BarChart chart2;
    Button barButton;
    private ProgressDialog loading;
    public ArrayList<January> listItems;
    LinearLayout lnr_line,lnr_bar;

    boolean isLineChart=true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.line_graph,container,false);

        barButton = (Button) view.findViewById(R.id.barButton);
        chart1 = (LineChart) view.findViewById(R.id.chart1);
        chart2 = (BarChart) view.findViewById(R.id.chart2);
        lnr_line = (LinearLayout) view.findViewById(R.id.lnr_line);
        lnr_bar = (LinearLayout) view.findViewById(R.id.lnr_bar);

        getData();

        barButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isLineChart){
                    lnr_bar.setVisibility(View.VISIBLE);
                    lnr_line.setVisibility(View.GONE);
                    barButton.setText("Line Graph");
                    isLineChart=false;
                    display();
                }else {
                    lnr_bar.setVisibility(View.GONE);
                    lnr_line.setVisibility(View.VISIBLE);
                    barButton.setText("Bar Graph");
                    isLineChart=true;
                    display();
                }
            }
        });


        return view;
    }


    void display(){

        if(listItems!= null){

            chart1.animateXY(4000,4000);
            chart2.animateXY(4000,4000);

            ArrayList<Entry> entries = new ArrayList<>();
            ArrayList<BarEntry> entries1 = new ArrayList<>();
            for (int i=0;i< listItems.size(); i++){

                float units = Float.parseFloat(listItems.get(i).getUnits());

                entries.add(new Entry(units, i));

                try {
                    units = units - Float.parseFloat(listItems.get(i-1).getUnits());
                }catch (Exception e){
                    units =  Float.parseFloat(listItems.get(i).getUnits());
                }
                entries1.add(new BarEntry(units, i));
            }

            LineDataSet dataset =new LineDataSet(entries,"March");
            dataset.setColor(Color.parseColor("#8CEAFF"));
            BarDataSet dataset1 =new BarDataSet(entries1,"March");
            dataset1.setColor(Color.rgb(155, 155, 0));
            dataset.setDrawCubic(false);
            dataset.setDrawFilled(true);

            ArrayList<String> labels=new ArrayList<>();
            for (int i=0;i<listItems.size();i++){
                String date =listItems.get(i).getDate();
                String req = date.substring(8,10);
                labels.add(req);
            }

            LineData lineData=new LineData(labels,dataset);
            BarData barData = new BarData(labels,dataset1);
            chart1.setData(lineData);
            chart2.setData(barData);
            chart1.setDescription("X-axis : Date   Y-axis : Units Consumed");
            chart2.setDescription("X-axis : Date   Y-axis : Units Consumed");
        }
    }
    private void getData() {
        VolleyCallBack.jsonStringRequest(getContext(),Constants.FETCH_DATA,null,this);
    }
    @Override
    public void onResponseSuccess(String response, ProgressDialog progressDialog) {
        progressDialog.dismiss();
        Type type=new TypeToken<List<January>>(){}.getType();
        listItems = new Gson().fromJson(response,type);
        display();
    }

    @Override
    public void onResponseFailed(VolleyError error, ProgressDialog progressDialog) {
        progressDialog.dismiss();
    }
}