package com.example.sachin.electricityusage.network;

import android.app.ProgressDialog;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sachin.electricityusage.constants.Constants;
import com.example.sachin.electricityusage.interfaces.ResponseListener;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;


public class VolleyCallBack {

    public static JSONObject createJsonObject(Object object) {
        JSONObject jsonObject = null;
        try {
            String json = new Gson().toJson(object);
            jsonObject = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public static void jsonObjectRequest(Context context, String url, Object object, final ResponseListener responseListener) {

        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Fetching...");
        progressDialog.setTitle("Please wait...");
        progressDialog.show();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, createJsonObject(object), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                responseListener.onResponseSuccess(response.toString(), progressDialog);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                responseListener.onResponseFailed(error, progressDialog);
            }
        });


        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);
    }


    public static void jsonStringRequest(Context context, String url, Object object, final ResponseListener responseListener) {


        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Fetching...");
        progressDialog.setTitle("Please wait...");
        progressDialog.show();

        StringRequest jsonObjectRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                responseListener.onResponseSuccess(response, progressDialog);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                responseListener.onResponseFailed(error, progressDialog);
            }
        });


        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);

    }

}
