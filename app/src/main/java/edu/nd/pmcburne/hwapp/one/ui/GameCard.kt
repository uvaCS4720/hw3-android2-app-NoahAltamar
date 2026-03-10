package edu.nd.pmcburne.hwapp.one.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import edu.nd.pmcburne.hwapp.one.data.local.GameEntity

@Composable
fun GameCard(game: GameEntity) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            // putting current game status at the top
            Text(
                text = getStatusText(game),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(8.dp))

            // first away team row then home team row as display is typically (away) @ (home)
            TeamRow(
                teamName = game.awayTeam,
                score = game.awayScore,
                isWinner = game.awayWinner,
                gameState = game.gameState
            )

            Spacer(modifier = Modifier.height(4.dp))

            TeamRow(
                teamName = game.homeTeam,
                score = game.homeScore,
                isWinner = game.homeWinner,
                gameState = game.gameState
            )
        }
    }
}

@Composable
fun TeamRow(
    teamName: String,
    score: String,
    isWinner: Boolean,
    gameState: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = teamName,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = if (isWinner) FontWeight.Bold else FontWeight.Normal
        )
        if (gameState != "pre-game") {
            Text(
                text = score,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = if (isWinner) FontWeight.Bold else FontWeight.Normal
            )
        }
    }
}

fun getStatusText(game: GameEntity): String {
    return when (game.gameState) {
        "final" -> "Final"
        "live" -> "${game.currentPeriod} - ${game.contestClock}"
        else -> game.startTime
    }
}