package com.example.hw1.utilities

import android.content.Context
import android.util.Log
import com.example.hw1.Models.HighScore
import com.google.gson.Gson

class DataManger private constructor(context: Context){
    private val data = SharedPreferences.getInstance().getScores() as MutableList<HighScore>

    companion object {
        @Volatile
        private var instance: DataManger? = null

        fun init(context: Context): DataManger {
            return instance ?: synchronized(this) {
                instance ?: DataManger(context).also { instance = it }
            }
        }

        fun getInstance(): DataManger {
            return instance
                ?: throw IllegalStateException("DataManger must be initialized by calling init(context) before use.")
        }
    }

    fun addScore(score: HighScore) {
        if (data.size < 10) {
            data.add(score)
        } else {
            val minScore = data.minByOrNull { it.score }!!
            if (score.score > minScore.score) {
                data.remove(minScore)
                data.add(score)
            }
        }
        Log.v("TAG", "DataManger: addScore: $data")
    }

    fun getScores(): MutableList<HighScore> {
        return data
    }

    fun generateHighScores() {
        for (i in 1..10) {
            val highScore = HighScore(
                date = "2023-10-${i.toString().padStart(2, '0')}",
                score = 2000,
                plusCode = "8G4P3RJ2+XG"
            )
            addScore(highScore)
        }
    }


}