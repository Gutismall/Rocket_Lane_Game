package com.example.hw1

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.hw1.Fragments.HighScoreListFragment
import com.example.hw1.Fragments.MapFragment2
import com.example.hw1.Interfaces.OnScoreSelectedListener

class ScoreboardActivity : AppCompatActivity(), OnScoreSelectedListener {

    private lateinit var score_FRAME_list: FrameLayout
    private lateinit var score_FRAME_map: FrameLayout
    private lateinit var highScoreFragment: HighScoreListFragment
    private lateinit var mapFragment: MapFragment2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scoreboard)
        findViews()
        initViews()
    }

    private fun initViews() {
        highScoreFragment = HighScoreListFragment()
        supportFragmentManager.beginTransaction().add(R.id.score_FRAME_list, highScoreFragment).commit()
        mapFragment = MapFragment2()
        supportFragmentManager.beginTransaction().add(R.id.score_FRAME_map, mapFragment).commit()
    }

    private fun findViews() {
        score_FRAME_list = findViewById(R.id.score_FRAME_list)
        score_FRAME_map  = findViewById(R.id.score_FRAME_map)
    }

    override fun onScoreSelected(plusCode : String) {
        val mapFragment = MapFragment2.newInstance(plusCode)
        supportFragmentManager.beginTransaction()
            .replace(R.id.score_FRAME_map, mapFragment)
            .addToBackStack(null)
            .commit()
    }
}