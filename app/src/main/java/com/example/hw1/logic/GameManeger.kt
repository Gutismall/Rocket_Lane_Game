package com.example.hw1.logic

import com.example.hw1.utilities.Constants

class GameManeger {
    var board: Array<Array<Int>> = Array(Constants.boardSize) { Array(Constants.boardSize){0} }
    var shipRow = Array(Constants.boardSize) {0}
    var currentPostion: Int = 2
    var odometer : Int = 0
    var coins : Int = 0
    var hits: Int = 0

    init {
        shipRow[currentPostion] = 1
    }

    fun checkHit(): Boolean {
    if (shipRow[currentPostion] == board[board.size - 1][currentPostion]) {
        if (hits < 3)
            hits++
        return true
    } else
        return false
    }
    fun checkCoin(): Boolean {
        if (board[board.size - 1][currentPostion] - shipRow[currentPostion] == 1) {
            coins++
            return true
        }
        return false
    }
    fun moveDown() {
        for (i in board.size - 1 downTo 0) {
            for (j in 0 until board[i].size) {
                if (i == board.size - 1 && board[i][j] > 0) {
                    board[i][j] = 0
                }
                if (board[i][j] == 1) {
                    board[i][j] = 0
                    board[i + 1][j] = 1
                }
                if (board[i][j] == 2) {
                    board[i][j] = 0
                    board[i + 1][j] = 2
                }
            }
        }
    }
    fun addNewRock(lane:Int){
        board[0][lane] = 1
    }

    fun moveLeft() {
        shipRow[currentPostion] -= 1
        shipRow[currentPostion - 1] += 1
    }

    fun moveRight() {
        shipRow[currentPostion] -= 1
        shipRow[currentPostion + 1] += 1
    }

    fun addNewCoin(randomLane: Int) {
        if (board[0][randomLane] == 0) {
            board[0][randomLane] = 2
        }
    }

}