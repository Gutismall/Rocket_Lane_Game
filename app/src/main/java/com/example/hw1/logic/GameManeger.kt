package com.example.hw1.logic

import android.os.CountDownTimer
import com.google.android.material.imageview.ShapeableImageView
import kotlin.random.Random

class GameManeger {
    var board: Array<Array<Int>> = Array(4) { Array(3){0} }
    var currentPostion: Int = 1
    var hits: Int = 0



    fun checkHit():Boolean{
        if (board[board.size-1][currentPostion] == 2){
            if (hits != 3){
                hits++
            }
            board[board.size-1][currentPostion] -= 1
            return true
        }
        else{
            for (pos in 0 until board[0].size){
                if (board[board.size - 1][pos] == 1 && pos != currentPostion){
                    board[board.size - 1][pos] -= 1
                }
            }
        }
        return false
    }
    fun moveDown() {
        for (row in board.size - 2 downTo 0) {
            for (col in 0 until board[row].size) {
                if (board[row][col] == 1) {
                    board[row + 1][col] += 1

                    board[row][col] -= 1
                }
            }
        }
    }

    fun addNewRock(lane:Int){
        board[0][lane] = 1
    }

    fun moveLeft() {
        board[board.size - 1][currentPostion] -= 1
        board[board.size - 1][currentPostion - 1] += 1
    }

    fun moveRight() {
        board[board.size - 1][currentPostion] -= 1
        board[board.size - 1][currentPostion + 1] += 1
    }

}