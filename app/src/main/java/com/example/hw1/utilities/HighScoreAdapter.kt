package com.example.hw1.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import com.example.hw1.Models.HighScore
import com.example.hw1.R

class HighScoreAdapter(context: Context, private val scores: List<HighScore>) :
    ArrayAdapter<HighScore>(context, 0, scores) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_high_score, parent, false)

        val highScore = scores[position]

        val itemDate: TextView = view.findViewById(R.id.item_date)
        val itemScore: TextView = view.findViewById(R.id.item_score)

        itemDate.text = "Date: ${highScore.date}"
        itemScore.text = "Score: ${highScore.score}"

        return view
    }
}