package edu.nd.pmcburne.hwapp.one.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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

            Text(
                text = getStatusText(game),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(8.dp))

            TeamRow(
                teamName = game.awayTeam,
                score = game.awayScore,
                isWinner = game.awayWinner,
                gameState = game.gameState,
                label = "AWAY"
            )

            Spacer(modifier = Modifier.height(4.dp))

            TeamRow(
                teamName = game.homeTeam,
                score = game.homeScore,
                isWinner = game.homeWinner,
                gameState = game.gameState,
                label = "HOME"
            )
        }
    }
}

@Composable
fun TeamRow(
    teamName: String,
    score: String,
    isWinner: Boolean,
    gameState: String,
    label: String
) {
    val isFinal = gameState == "final"
    val winnerBackground = if (isWinner && isFinal)
        Color(0xFF1B5E20).copy(alpha = 0.15f) else Color.Transparent

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(6.dp))
            .background(winnerBackground)
            .padding(horizontal = 8.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.outline,
                modifier = Modifier.width(36.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = teamName,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = if (isWinner && isFinal) FontWeight.Bold else FontWeight.Normal
            )
        }
        if (gameState != "pre-game") {
            Text(
                text = score,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = if (isWinner && isFinal) FontWeight.Bold else FontWeight.Normal,
                color = if (isWinner && isFinal) Color(0xFF2E7D32) else Color.Unspecified
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