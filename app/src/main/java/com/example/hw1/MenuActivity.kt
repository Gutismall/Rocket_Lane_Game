package com.example.hw1

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.materialswitch.MaterialSwitch
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textview.MaterialTextView

class MenuActivity : AppCompatActivity() {
    private lateinit var menu_TXT_Title : MaterialTextView
    private lateinit var menu_BTN_start : MaterialButton
    private lateinit var menu_BTN_Scoreboard : MaterialButton
    private lateinit var manu_SWT_fast_slow : MaterialSwitch
    private lateinit var manu_SWT_sensor_mode : MaterialSwitch
    private var isFastMode = false
    private var isSensorMode = false
    private val LOCATION_PERMISSION_REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manu)
        checkLocationPermission()
        findViews()
        initViews()
    }

    private fun findViews() {
        menu_TXT_Title = findViewById(R.id.menu_TXT_Title)
        menu_BTN_start = findViewById(R.id.menu_BTN_start)
        menu_BTN_Scoreboard = findViewById(R.id.menu_BTN_Scoreboard)
        manu_SWT_fast_slow = findViewById(R.id.manu_SWT_fast_slow)
        manu_SWT_sensor_mode = findViewById(R.id.manu_SWT_sensor_mode)
    }

    private fun initViews() {

        manu_SWT_fast_slow.setOnCheckedChangeListener { _, isChecked: Boolean ->
            isFastMode = isChecked

        }
        manu_SWT_sensor_mode.setOnCheckedChangeListener { _, isChecked: Boolean ->
            isSensorMode = isChecked

        }
        menu_BTN_Scoreboard.setOnClickListener{ view: View -> openScoreboard() }
        menu_BTN_start.setOnClickListener{ view: View -> startGame() }
    }

    private fun startGame() {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("isFastMode", isFastMode)
        intent.putExtra("isSensorMode", isSensorMode)
        startActivity(intent)
    }

    private fun openScoreboard() {
        val intent = Intent(this, ScoreboardActivity::class.java)
        startActivity(intent)
    }
    private fun checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Request the location permissions
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission was granted
                return
            } else {
                // Permission was denied
            }
        }
    }
}