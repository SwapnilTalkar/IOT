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
import com.example.sachin.electricityusage.R;
import com.example.sachin.electricityusage.constants.Constants;
import com.example.sachin.electricityusage.interfaces.ResponseListener;
import com.example.sachin.electricityusage.models.January;
import com.example.sachin.electricityusage.models.request.Request;
import com.example.sachin.electricityusage.models.response.Response;
import com.example.sachin.electricityusage.network.VolleyCallBack;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class MultiLineGraphFragment extends Fragment implements ResponseListener {

    Button changeGraph;
    LineChart lineChart;
    LinearLayout lnr_line,lnr_bar;

    boolean isBarChart;
    ArrayList<String> months;
    BarChart barChart;
    ArrayList<String> xAxisValue=new ArrayList<>();

    int[] colors={Color.BLACK,Color.GRAY,Color.rgb(155, 155, 0),Color.CYAN};
    ArrayList<ArrayList<January>> parentList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.multi_line_graph, container, false);

        lineChart = (LineChart) view.findViewById(R.id.chart);
        barChart = (BarChart) view.findViewById(R.id.chart2);
        lnr_line = (LinearLayout) view.findViewById(R.id.lnr_line);
        lnr_bar = (LinearLayout) view.findViewById(R.id.lnr_bar);

        Bundle bundle = this.getArguments();
        getData(bundle);

        changeGraph = (Button) view.findViewById(R.id.changeGraph);
        changeGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!isBarChart){
                    lnr_line.setVisibility(View.GONE);
                    changeGraph.setText("MULTI-LINE GRAPH");
                    lnr_bar.setVisibility(View.VISIBLE);
                    isBarChart=true;
                    showBarGraph(parentList);
                }else {
                    lnr_line.setVisibility(View.VISIBLE);
                    changeGraph.setText("MULTI-BAR GRAPH");
                    lnr_bar.setVisibility(View.GONE);
                    isBarChart=false;
                    showLineGraph(parentList);
                }

            }
        });
        return view;
    }

    private void getData(Bundle bundle) {

        if (bundle != null)
            months = bundle.getStringArrayList("Months");

        Request request=new Request();
        request.setStrings(months);

        setXAxisValues(months);

        VolleyCallBack.jsonObjectRequest(getContext(), Constants.MULTI_LINE_DATA,request,this);
    }


    public void setXAxisValues(ArrayList<String> months){

        int maxDate = 0;

            if(months.size() >= 3){
                 maxDate = 31;
            }else {
                if(months.get(0)!="january" && months.get(1)!="march"){
                    maxDate = 30;
                }else {
                    maxDate = 31;
                }
            }
            for(int i = 1;i<= maxDate;i++){
                xAxisValue.add(i+"");
            }
    }


    @Override
    public void onResponseSuccess(String response, ProgressDialog progressDialog) {
        progressDialog.dismiss();
        parentList=new ArrayList<>();
        try {
            JSONObject jsonObject=new JSONObject(response);
            Type type=new TypeToken<List<January>>(){}.getType();


            for (int i=0;i<months.size();i++){
                if(jsonObject.getJSONArray(months.get(i))!=null || jsonObject.has(months.get(i)) || jsonObject.getJSONArray(months.get(i)).length()!=0) {
                    ArrayList<January> januaries = new Gson().fromJson(jsonObject.getJSONArray(months.get(i)).toString(), type);
                    parentList.add(januaries);
                }
            }

            showLineGraph(parentList);
            System.out.print("");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResponseFailed(VolleyError error, ProgressDialog progressDialog) {
        progressDialog.dismiss();
    }


    void showLineGraph(ArrayList<ArrayList<January>> parent){
        lineChart.animateXY(5000, 5000);
        lineChart.setDescription("X-axis : Date   Y-axis : Units Consumed");


        ArrayList<ILineDataSet> lineDataSets = new ArrayList<>();
        ArrayList<Entry> temp=null;
        for (int i = 0; i < parent.size(); i++) {
            temp=new ArrayList<>();
            for(int j=0;j<parent.get(i).size();j++) {

                temp.add(new Entry(Float.parseFloat(parent.get(i).get(j).getUnits()),j));
            }

            LineDataSet lineDataSet1 = new LineDataSet(temp, months.get(i));
            lineDataSet1.setDrawCircles(false);
            lineDataSet1.setColor(colors[i]);
            lineDataSets.add(lineDataSet1);
        }

        lineChart.setData(new LineData(xAxisValue, lineDataSets));
        lineChart.setVisibleXRangeMaximum(65f);

    }


    void showBarGraph(ArrayList<ArrayList<January>> parent){

        barChart.setDescription("X-axis : Date   Y-axis : Units Consumed");
        barChart.animateXY(3000,3000);
        ArrayList<IBarDataSet> iBarDataSets = new ArrayList<>();
        ArrayList<BarEntry> temp=null;

        for (int i = 0; i < parent.size(); i++) {
            temp=new ArrayList<>();
            for(int j=0;j<parent.get(i).size();j++) {

                float x=0;
                try{
                     x = Integer.parseInt(parent.get(i).get(j).getUnits()) - Integer.parseInt( parent.get(i).get(j-1).getUnits());
                }catch (Exception e){
                    x = Integer.parseInt(parent.get(i).get(j).getUnits());
                }

                temp.add(new BarEntry(x,j));
            }

            BarDataSet barDataSet = new BarDataSet(temp, months.get(i));
            barDataSet.setColor(colors[i]);
            iBarDataSets.add(barDataSet);
        }

        BarData data = new BarData(xAxisValue,iBarDataSets);
        barChart.setData(data);
        barChart.invalidate();

    }


}
