package com.example.hw1

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.View
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hw1.logic.GameManeger
import com.example.hw1.utilities.Constants
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.imageview.ShapeableImageView
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var main_FAB_left: ExtendedFloatingActionButton
    private lateinit var main_FAB_right: ExtendedFloatingActionButton
    private lateinit var main_IMG_Ship: ShapeableImageView
    private lateinit var main_IMG_hearts: Array<ShapeableImageView>
    private var main_IMG_rocks: MutableList<ShapeableImageView> = mutableListOf()
    private lateinit var gameManeger: GameManeger
    private lateinit var rootLayout: RelativeLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findView()
        gameManeger = GameManeger()
        gameManeger.board[gameManeger.board.size - 1][1] = 1
        initView()
    }

    private fun initView() {
        main_FAB_left.setOnClickListener { moveLeft() }
        main_FAB_right.setOnClickListener { moveRight() }
        startTimer()
    }

    private fun findView() {
        main_FAB_left = findViewById(R.id.main_FAB_left)
        main_FAB_right = findViewById(R.id.main_FAB_right)
        main_IMG_Ship = findViewById(R.id.main_IMG_Ship)
        rootLayout = findViewById(R.id.main)
        main_IMG_hearts = arrayOf(
            findViewById(R.id.main_IMG_heat_0),
            findViewById(R.id.main_IMG_heat_1),
            findViewById(R.id.main_IMG_heat_2),
        )
    }

    private fun startTimer() {
        object : CountDownTimer(1500, 1500) {
            override fun onTick(millisUntilFinished: Long) {
                refreshUI()
            }

            override fun onFinish() {
                moveAllDown()
                startTimer()
            }
        }.start()
    }

    private fun refreshUI() {
        main_IMG_rocks.add(createNewRock())
        if (gameManeger.checkHit()){
            main_IMG_hearts[main_IMG_hearts.size - gameManeger.hits].visibility = View.INVISIBLE
            Toast.makeText(this, "You got hit", Toast.LENGTH_SHORT).show()
            vibrate()
        }
    }

    private fun vibrate() {
        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        if (vibrator.hasVibrator()) {
            val pattern = longArrayOf(0, 200, 100, 300)

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                val vibrationEffect = VibrationEffect.createWaveform(pattern, -1)
                vibrator.vibrate(vibrationEffect)
            } else {
                vibrator.vibrate(pattern, -1)
            }
        }
    }

    private fun moveAllDown() {
        gameManeger.moveDown()
        val rocksCopy = ArrayList(main_IMG_rocks)

        for (rock in rocksCopy) {

            if (rock.translationY >= 1800f) {
                rootLayout.removeView(rock)
                main_IMG_rocks.remove(rock)
            } else {
                rock.animate()
                    .translationYBy(600f)
                    .setDuration(500)
                    .start()
            }
        }
    }

    private fun createNewRock(): ShapeableImageView {
        val randomLane = Random.nextInt(0, 3)
        val newRock = ShapeableImageView(this).apply {
            setImageResource(R.drawable.asteroid_2_svgrepo_com)
            layoutParams = RelativeLayout.LayoutParams(350, 350).apply {
                addRule(RelativeLayout.ALIGN_PARENT_TOP)
                addRule(RelativeLayout.CENTER_IN_PARENT)
            }
        }
        newRock.translationX = Constants.ROW[randomLane]
        rootLayout.addView(newRock)
        gameManeger.addNewRock(randomLane)
        return newRock
    }

    private fun moveLeft() {
        if (gameManeger.currentPostion != 0) {
            gameManeger.moveLeft()
            gameManeger.currentPostion--
            main_IMG_Ship.translationX = Constants.ROW[gameManeger.currentPostion] as Float
        }
    }

    private fun moveRight() {
        if (gameManeger.currentPostion < 2) {
            gameManeger.moveRight()
            gameManeger.currentPostion++
            main_IMG_Ship.translationX = Constants.ROW[gameManeger.currentPostion] as Float
        }
    }
}