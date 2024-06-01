package com.selflearn.graphsudoku.persistence

import com.selflearn.graphsudoku.domain.GameStorageResult
import com.selflearn.graphsudoku.domain.IGameDataStorage
import com.selflearn.graphsudoku.domain.SudokuPuzzle
import com.selflearn.graphsudoku.domain.getHash
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

private const val FILE_NAME = "game_state.txt";

class LocalGameStorageImpl(
    filestorageDirectory: String,
    private val pathToStorageFile: File = File(filestorageDirectory, FILE_NAME)
) : IGameDataStorage {

    override suspend fun updateGame(game: SudokuPuzzle): GameStorageResult = withContext(Dispatchers.IO) {
        try {
            updateGameData(game)
            GameStorageResult.OnSuccess(game)
        } catch (e: Exception) {
            GameStorageResult.OnError(exception = e)
        }
    }

    private fun updateGameData(game: SudokuPuzzle) {
        try {
            val fileOutputStream = FileOutputStream(pathToStorageFile)
            val objectOutputStream = ObjectOutputStream(fileOutputStream)
            objectOutputStream.writeObject(game)
            objectOutputStream.close()
        } catch (e: Exception) {
            throw e;
        }
    }

    override suspend fun updateNode(x: Int, y: Int, color: Int, elapsedTime: Long): GameStorageResult =
        withContext(Dispatchers.IO) {
            try {
                val game = getGame()
                game.graph[getHash(x, y)]!!.first.color = color
                game.elapsedTime = elapsedTime
                updateGameData(game)
                GameStorageResult.OnSuccess(game)
            } catch (e: Exception) {
                GameStorageResult.OnError(e)
            }
        }

    private fun getGame(): SudokuPuzzle {
        try {
            val game: SudokuPuzzle
            val fileInputStream = FileInputStream(pathToStorageFile)
            val objectInputStream = ObjectInputStream(fileInputStream)
            game = objectInputStream.readObject() as SudokuPuzzle
            objectInputStream.close()
            return game
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getCurrentGame(): GameStorageResult {
        TODO("Not yet implemented")
    }
}