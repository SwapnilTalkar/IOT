package com.example.sachin.electricityusage.interfaces;

import android.app.ProgressDialog;

import com.android.volley.VolleyError;
import com.google.gson.JsonObject;

import org.json.JSONObject;

/**
 * Created by sachin on 1/4/17.
 */

public interface ResponseListener {
    void onResponseSuccess(String response, ProgressDialog progressDialog);
    void onResponseFailed(VolleyError error,ProgressDialog progressDialog);
}
