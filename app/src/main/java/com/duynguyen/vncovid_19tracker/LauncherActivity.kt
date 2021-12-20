package com.duynguyen.vncovid_19tracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.util.*

class LauncherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)
        init()
    }

    /**
     * Initialize view of LauncherActivity
     */
    private fun init(){
        updateGui();
    }

    /**
     * Update GUI of MainActivity
     */
    private fun updateGui() {
        // set timeout
        val timer = Timer()
        timer.schedule(UpdateGui(), 2000)
    }

    /**
     * Class use for switching to MainActivity view in seconds
     */
    private inner class UpdateGui : TimerTask() {
        override fun run() {
            // create an intent
            val intent = Intent(applicationContext, MainActivity::class.java)

            // switching to MainActivity and finish this activity
            startActivity(intent);
            finish();
        }

    }
}