package com.duynguyen.vncovid_19tracker.controllers

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject

class GetCovidData(var context: Context) {

    companion object{
        var url = "https://static.pipezero.com/covid/data.json"
    }

    public fun sendAndRequestResponse(onResponse: OnResponseListener){
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                onResponse.onResponse(response)
            },
            { error ->
                onResponse.onErrorResponse(error)
            }
        )
        Singleton.getInstance(context).addToRequestQueue(jsonObjectRequest)
    }

    public interface OnResponseListener{
        fun onResponse(obj : JSONObject)
        fun  onErrorResponse(error : VolleyError)
    }
}