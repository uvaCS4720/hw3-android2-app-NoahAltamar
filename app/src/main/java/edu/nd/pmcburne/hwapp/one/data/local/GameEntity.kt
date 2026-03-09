package edu.nd.pmcburne.hwapp.one.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "games")
data class GameEntity(
    @PrimaryKey
    val gameID: String,
    val gender: String,
    val gameState: String,
    val startTime: String,
    val startDate: String,
    val currentPeriod: String,
    val contestClock: String,
    val finalMessage: String,
    val homeTeam: String,
    val homeScore: String,
    val homeWinner: Boolean,
    val awayTeam: String,
    val awayScore: String,
    val awayWinner: Boolean
)