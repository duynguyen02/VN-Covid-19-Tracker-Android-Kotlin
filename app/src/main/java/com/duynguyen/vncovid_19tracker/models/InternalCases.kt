package com.duynguyen.vncovid_19tracker.models

import org.json.JSONException

import org.json.JSONObject
import java.text.NumberFormat
import java.util.Locale


open class InternalCases(obj: JSONObject) {
    var death: String
    var treating: String
    var cases: String
    var recovered: String
    var localeEN: Locale = Locale("en", "EN")
    protected var en: NumberFormat = NumberFormat.getInstance(localeEN)


    init {
        //try to set attr to object from json object
        try {
            death = en.format(obj.getString("death").toDouble())
            treating = en.format(obj.getString("treating").toDouble())
            cases = en.format(obj.getString("cases").toDouble())
            recovered = en.format(obj.getString("recovered").toDouble())
        } catch (e: JSONException) {
            e.printStackTrace()
            death = "#error"
            treating = "#error"
            cases = "#error"
            recovered = "#error"
        }
    }


}