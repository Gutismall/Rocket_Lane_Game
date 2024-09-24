package com.example.hw1.utilities

import android.content.Context
import android.util.Log
import com.example.hw1.Models.HighScore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SharedPreferences private constructor(context: Context){
    private val sharedPreferences = context.getSharedPreferences("scoreboard", Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()
    private val gson = Gson()


    companion object {
        @Volatile
        private var instance: SharedPreferences? = null

        fun init(context: Context): SharedPreferences {
            return instance ?: synchronized(this) {
                instance ?: SharedPreferences(context).also { instance = it }
            }
        }

        fun getInstance(): SharedPreferences {
            return instance
                ?: throw IllegalStateException("Shared Preferences must be initialized by calling init(context) before use.")
        }
    }

    fun putScores(scores: MutableList<HighScore>) {
        val json = gson.toJson(scores)
        editor.putString("scores", json).apply()
    }

    fun getScores(): MutableList<HighScore> {
        val json = sharedPreferences.getString("scores", null)
        Log.d("SharedPreferences", "getScores: $json")
        return if (!json.isNullOrEmpty()) {
            val type = object : TypeToken<MutableList<HighScore>>() {}.type
            gson.fromJson(json, type)
        } else {
            mutableListOf()
        }
    }

}