package edu.nd.pmcburne.hwapp.one.data.model

data class ApiResponse(
    val games: List<GameWrapper>
)

data class GameWrapper(
    val game: Game
)

data class Game(
    val gameID: String,
    val gameState: String,
    val startTime: String,
    val startDate: String,
    val currentPeriod: String,
    val contestClock: String,
    val finalMessage: String,
    val home: TeamInfo,
    val away: TeamInfo
)

// note that score is a string not an int
data class TeamInfo(
    val score: String,
    val winner: Boolean,
    val names: TeamNames
)

// note that this API always uses names.short and names.full is empty
data class TeamNames(
    val short: String
)