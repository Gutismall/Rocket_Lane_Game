package com.example.hw1

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.hw1.Interfaces.CallBackLocation
import com.example.hw1.Interfaces.Callback_MoveCallback
import com.example.hw1.Models.BoardModel
import com.example.hw1.Models.HighScore
import com.example.hw1.logic.GameManeger
import com.example.hw1.utilities.Constants
import com.example.hw1.utilities.DataManger
import com.example.hw1.utilities.ImageLoader
import com.example.hw1.utilities.SharedPreferences
import com.example.hw1.utilities.SingleSoundPlayer
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.imageview.ShapeableImageView
import com.google.openlocationcode.OpenLocationCode
import dev.tomco.a24c_10357_w04.Utilities.MoveDetector
import java.time.LocalDate
import kotlin.random.Random

class MainActivity : AppCompatActivity(), CallBackLocation {
    private lateinit var main_FAB_left: ExtendedFloatingActionButton
    private lateinit var main_FAB_right: ExtendedFloatingActionButton
    private lateinit var main_IMG_hearts: Array<ShapeableImageView>
    private val rockImageViews = mutableListOf<ShapeableImageView>()
    private val shipImageViews = mutableListOf<ShapeableImageView>()
    private val coinImageViews = mutableListOf<ShapeableImageView>()
    private lateinit var gameManeger: GameManeger
    private var isFastMode = false
    private var isSensorMode = false
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    var callBackLocation: CallBackLocation? = null
    private lateinit var moveDetector: MoveDetector
    private var countDownTimer: CountDownTimer? = null
    private lateinit var singleSoundPlayer: SingleSoundPlayer
    private val LOCATION_PERMISSION_REQUEST_CODE = 1
    private var imageLoader : ImageLoader = ImageLoader.getInstance()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        singleSoundPlayer = SingleSoundPlayer(this)
        isFastMode = intent.getBooleanExtra("isFastMode", false)
        isSensorMode = intent.getBooleanExtra("isSensorMode", false)
        findView(isSensorMode)
        gameManeger = GameManeger()
        callBackLocation = this
        initView(isFastMode)

    }

    private fun initView(isFastMode: Boolean) {
        main_FAB_left.setOnClickListener { moveLeft() }
        main_FAB_right.setOnClickListener { moveRight() }
        shipImageViews[gameManeger.currentPostion].visibility = View.VISIBLE
        if (isFastMode){
            startTimer(Constants.timer)
        }else{
            startTimer(Constants.timer * 2)
        }

    }

    private fun findView(isSensorMode:Boolean) {
        main_FAB_left = findViewById(R.id.main_FAB_left)
        main_FAB_right = findViewById(R.id.main_FAB_right)
        if (isSensorMode){
            main_FAB_left.visibility = View.INVISIBLE
            main_FAB_right.visibility = View.INVISIBLE
            initMoveDetector()
        }
        for (row in 0 until BoardModel.rocks.size) {
            for (id in 0 until BoardModel.rocks[row].size) {
                val rockView = findViewById<ShapeableImageView>(BoardModel.rocks[row][id])
                val coinView = findViewById<ShapeableImageView>(BoardModel.coins[row][id])

                rockView.visibility = View.INVISIBLE
                coinView.visibility = View.INVISIBLE

                if (rockView != null) {
                    rockImageViews.add(rockView)
                }
                if (coinView != null) {
                    coinImageViews.add(coinView)
                }
            }
        }
        for (id in BoardModel.ships) {
            val imageView = findViewById<ShapeableImageView>(id)
            imageView.visibility = View.INVISIBLE
            if (imageView != null) {
                shipImageViews.add(imageView)
            }
        }
        main_IMG_hearts = arrayOf(
            findViewById(R.id.main_IMG_heat_0),
            findViewById(R.id.main_IMG_heat_1),
            findViewById(R.id.main_IMG_heat_2),
        )
    }

    private fun startTimer(timer: Long) {
        countDownTimer = object : CountDownTimer(timer, timer) {
            override fun onTick(millisUntilFinished: Long) {
                refreshUI()
                if (Random.nextInt(0, 5) == 0) { // 20% chance to add a coin each second
                    addRandomCoin()
                }
            }

            override fun onFinish() {
                gameManeger.odometer += 100
                if (gameManeger.hits == 3) {
                    saveScore()
                    moveToMainMenu()
                    countDownTimer?.cancel()
                } else {
                    startTimer(timer)
                }
            }
        }.start()
    }

    private fun moveToMainMenu() {
        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun saveScore() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
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

        // Fetch the last known location
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    // Convert latitude and longitude to Plus Code
                    val plusCode = OpenLocationCode.encode(location.latitude, location.longitude)
                    Log.v("PlusCode", "Plus Code: $plusCode")

                    // Use callback to create score with Plus Code
                    callBackLocation?.createScore(plusCode)
                } else {
                    Log.e("LocationError", "Location is null")
                }
            }
            .addOnFailureListener { exception ->
                Log.e("LocationError", "Failed to get location", exception)
            }
    }

    private fun addRockToView(){
        var randomLane = Random.nextInt(0, 5)
        gameManeger.addNewRock(randomLane)
        rockImageViews[randomLane].visibility = View.VISIBLE
    }

    private fun refreshUI() {
        moveItemsDown()
        addRockToView()
        if (gameManeger.checkHit()){
            main_IMG_hearts[main_IMG_hearts.size - gameManeger.hits].visibility = View.INVISIBLE
            Toast.makeText(this, "You got hit", Toast.LENGTH_SHORT).show()
            vibrate()
        }
        if (gameManeger.checkCoin()){
            singleSoundPlayer.playSound(R.raw.coin_sound)
            Toast.makeText(this, "You got a coin", Toast.LENGTH_SHORT).show()
        }
    }

    private fun addRandomCoin() {
        var randomLane = Random.nextInt(0, 5)
        if (rockImageViews[randomLane].visibility == View.VISIBLE) {
            return
        }
        gameManeger.addNewCoin(randomLane)
        coinImageViews[randomLane].visibility = View.VISIBLE
    }

    private fun vibrate() {
        val vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator

        if (vibrator.hasVibrator()) {
            val pattern = longArrayOf(0, 200, 100, 300)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val vibrationEffect = VibrationEffect.createWaveform(pattern, -1)
                vibrator.vibrate(vibrationEffect)
            } else {
                vibrator.vibrate(pattern, -1)
            }
        }
    }

    fun moveItemsDown() {
    gameManeger.moveDown()
    for (row in BoardModel.rocks.size - 1 downTo 0) {
        for (col in 0 until BoardModel.rocks[row].size) {
            val currentIndex = row * BoardModel.rocks[row].size + col
            val nextIndex = (row + 1) * BoardModel.rocks[row].size + col

            // Move rocks down
            if (row == BoardModel.rocks.size - 1 && rockImageViews[currentIndex].visibility == View.VISIBLE) {
                rockImageViews[currentIndex].visibility = View.INVISIBLE
            } else if (rockImageViews[currentIndex].visibility == View.VISIBLE) {
                rockImageViews[currentIndex].visibility = View.INVISIBLE
                rockImageViews[nextIndex].visibility = View.VISIBLE
            }

            // Move coins down
            if (row == BoardModel.rocks.size - 1 && coinImageViews[currentIndex].visibility == View.VISIBLE) {
                coinImageViews[currentIndex].visibility = View.INVISIBLE
            } else if (coinImageViews[currentIndex].visibility == View.VISIBLE) {
                coinImageViews[currentIndex].visibility = View.INVISIBLE
                coinImageViews[nextIndex].visibility = View.VISIBLE
            }
        }
    }
}

    private fun moveLeft() {
        if (gameManeger.currentPostion != 0) {
            gameManeger.moveLeft()
            shipImageViews[gameManeger.currentPostion].visibility = View.INVISIBLE
            gameManeger.currentPostion--
            shipImageViews[gameManeger.currentPostion].visibility = View.VISIBLE
        }
    }

    private fun moveRight() {
        if (gameManeger.currentPostion != 5) {
            gameManeger.moveRight()
            shipImageViews[gameManeger.currentPostion].visibility = View.INVISIBLE
            gameManeger.currentPostion++
            shipImageViews[gameManeger.currentPostion].visibility = View.VISIBLE
        }
    }

    override fun createScore(plusCode : String) {
        plusCode.let {
            val highScore = HighScore(
                LocalDate.now().toString(),
                (gameManeger.odometer) + (gameManeger.coins * 10),
                plusCode
            )
            Log.v("HighScore", highScore.toString())
            DataManger.getInstance().addScore(highScore)
        }
    }
    private fun initMoveDetector() {
                moveDetector = MoveDetector(
                    this,
                    object : Callback_MoveCallback {
                        override fun moveY_left() {
                            moveLeft()
                        }

                        override fun moveY_right() {
                            moveRight()
                        }
                    }
                )
                moveDetector.start()
    }

    override fun onPause() {
    super.onPause()
    if (isSensorMode) {
        moveDetector.stop()
    }
}

override fun onRestart() {
    super.onRestart()
    if (isSensorMode) {
        moveDetector.start()
    }
}
    override fun onDestroy() {
        super.onDestroy()
        SharedPreferences.getInstance().putScores(DataManger.getInstance().getScores())
    }
}
