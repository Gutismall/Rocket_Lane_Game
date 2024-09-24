package com.example.hw1.Fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.example.hw1.Interfaces.OnScoreSelectedListener
import com.example.hw1.R
import com.example.hw1.adapters.HighScoreAdapter
import com.example.hw1.utilities.DataManger
import com.example.hw1.utilities.SharedPreferences

class HighScoreListFragment : Fragment() {

    private lateinit var fragment_LST_high_scores: ListView
    private var listener: OnScoreSelectedListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnScoreSelectedListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnScoreSelectedListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_high_score_list, container, false)
        findViews(v)
        initViews()
        return v
    }

    private fun findViews(v: View) {
        fragment_LST_high_scores = v.findViewById(R.id.fragment_LST_high_scores)
    }

    private fun initViews() {
        val scores = DataManger.getInstance().getScores()
        Log.v("TAG", "HighScoreListFragment: initViews: $scores")
        val adapter = HighScoreAdapter(requireContext(), scores)
        fragment_LST_high_scores.adapter = adapter

        fragment_LST_high_scores.setOnItemClickListener { _, _, position, _ ->
            val selectedScore = scores[position]
            listener?.onScoreSelected(selectedScore.plusCode)
        }
    }
}