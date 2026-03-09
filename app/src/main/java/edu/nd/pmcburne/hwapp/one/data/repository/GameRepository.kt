package edu.nd.pmcburne.hwapp.one.data.repository

import edu.nd.pmcburne.hwapp.one.data.local.AppDatabase
import edu.nd.pmcburne.hwapp.one.data.local.GameEntity
import edu.nd.pmcburne.hwapp.one.data.network.RetrofitInstance
import kotlinx.coroutines.flow.Flow

class GameRepository(private val database: AppDatabase) {

    fun getGamesFromDb(date: String, gender: String): Flow<List<GameEntity>> {
        return database.gameDao().getGames(date, gender)
    }

    suspend fun fetchAndStoreGames(date: String, gender: String) {
        try {
            val year = date.substring(6, 10)
            val month = date.substring(0, 2)
            val day = date.substring(3, 5)

            val response = RetrofitInstance.api.getScores(gender, year, month, day)

            val entities = response.games.map { wrapper ->
                val game = wrapper.game
                GameEntity(
                    gameID = game.gameID,
                    gender = gender,
                    gameState = game.gameState,
                    startTime = game.startTime,
                    startDate = date,
                    currentPeriod = game.currentPeriod,
                    contestClock = game.contestClock,
                    finalMessage = game.finalMessage,
                    homeTeam = game.home.names.short,
                    homeScore = game.home.score,
                    homeWinner = game.home.winner,
                    awayTeam = game.away.names.short,
                    awayScore = game.away.score,
                    awayWinner = game.away.winner
                )
            }

            database.gameDao().insertGames(entities)
        } catch (e: Exception) {
            // Ensuring data remains while offline
        }
    }
}