package com.duynguyen.vncovid_19tracker

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.android.volley.VolleyError
import com.duynguyen.vncovid_19tracker.adapters.LocationsCasesAdapter
import com.duynguyen.vncovid_19tracker.controllers.AppConfiguration
import com.duynguyen.vncovid_19tracker.controllers.GetCovidData
import com.duynguyen.vncovid_19tracker.databinding.ActivityMainBinding
import org.json.JSONObject
import com.duynguyen.vncovid_19tracker.models.InternalCases
import com.duynguyen.vncovid_19tracker.models.InternalLocationsCases
import org.json.JSONArray





class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private var appConfig = AppConfiguration(this)
    private var mInternalLocationsCasesList : MutableList<InternalLocationsCases> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // using binding for this layout
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()


    }

    /**
     * Initialize view of MainActivity
     */
    private fun init(){
        // set on refreshing listener for swipe refresh layout
        binding.swipeMain.setOnRefreshListener { getData() }
        // get data for the first time
        getData()
    }

    /**
     * try to get covid 19 data if device have internet connection
     */
    private fun getData(){
        // enable refreshing icon
        binding.swipeMain.isRefreshing = true

        // if device have internet connection, init GetCovidData to get data from sever
        if (appConfig.isConnected()){
            with(GetCovidData(this)){
                sendAndRequestResponse(object : GetCovidData.OnResponseListener{
                    override fun onResponse(obj: JSONObject) {
                        // when get data completed, data will be set to view
                        setData(obj)
                        // show toast inform get data was completed
                        Toast.makeText(this@MainActivity, "Tải dữ liệu thành công!", Toast.LENGTH_SHORT).show()
                        // disable refreshing icon
                        binding.swipeMain.isRefreshing = false
                    }

                    override fun onErrorResponse(error: VolleyError) {
                        Toast.makeText(this@MainActivity, resources.getText(R.string.something_went_wrong_please_try_again), Toast.LENGTH_SHORT).show()
                        binding.swipeMain.isRefreshing = false
                    }
                })
            }
        }
        else{
            // inform device have not internet connection and disable refreshing icon
            Toast.makeText(this, resources.getText(R.string.no_internet_connection), Toast.LENGTH_SHORT).show()
            binding.swipeMain.isRefreshing = false
        }
    }

    /**
     * set  data to view
     */
    private fun setData(obj: JSONObject){

        // set json object to model
        val totalCases = InternalCases(obj.getJSONObject("total").getJSONObject("internal"))
        val todayCases = InternalCases(obj.getJSONObject("today").getJSONObject("internal"))

        // set info for "total" data
        binding.tvTotalCases.text = totalCases.cases
        binding.tvTotalRecovered.text = totalCases.recovered
        binding.tvTotalTreating.text = totalCases.treating
        binding.tvTotalDeath.text = totalCases.death

        // set info for "today" data
        binding.tvTodayCases.text = todayCases.cases
        binding.tvTodayRecovered.text = todayCases.recovered
        binding.tvTodayTreating.text = todayCases.treating
        binding.tvTodayDeath.text = todayCases.death

        // get locations cases in obj and add all to mInternalLocationsCasesList
        val locationsCases: JSONArray = obj.getJSONArray("locations")
        for (i in 0 until locationsCases.length()){
            mInternalLocationsCasesList.add(InternalLocationsCases(locationsCases[i] as JSONObject))
        }

        // set adapter
        binding.recycleViewInternalLocationCases.adapter = LocationsCasesAdapter(mInternalLocationsCasesList)


    }

    /**
     * set menu for action bar
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // set menu from R.menu.main_activity_menu
        menuInflater.inflate(R.menu.main_activity_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.app_info_menu_item -> {
                // open application information dialog and return true
                openAppInfoDialog()
                true
            }
            else -> {
                // return default boolean
                super.onOptionsItemSelected(item)
            }
        }
    }

    /**
     * custom a dialog for application information and show this dialog
     */
    @SuppressLint("InflateParams")
    private fun openAppInfoDialog(){
        // init a dialog then set attr and show
        with(AlertDialog.Builder(this)){
            setView(layoutInflater.inflate(R.layout.dialog_app_info, null))
            setCancelable(true)
            show()
        }
    }

}