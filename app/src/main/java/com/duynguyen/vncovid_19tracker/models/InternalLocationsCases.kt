package com.duynguyen.vncovid_19tracker.models

import org.json.JSONObject
import org.json.JSONException


class InternalLocationsCases(obj : JSONObject) : InternalCases(obj){
    var name : String
    var caseToday : String


    init {
        try {
            name = obj.getString("name")
            caseToday = "+" + super.en.format(obj.getString("casesToday").toDouble())
        } catch (e: JSONException) {
            e.printStackTrace()
            name = "#error"
            caseToday = "#error"
        }
    }
}