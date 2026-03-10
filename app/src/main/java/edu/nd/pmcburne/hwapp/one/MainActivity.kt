package edu.nd.pmcburne.hwapp.one

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Column
import edu.nd.pmcburne.hwapp.one.ui.DatePickerBar
import edu.nd.pmcburne.hwapp.one.ui.GameCard
import edu.nd.pmcburne.hwapp.one.ui.GenderToggle
import edu.nd.pmcburne.hwapp.one.ui.LoadingIndicator
import edu.nd.pmcburne.hwapp.one.ui.theme.HWStarterRepoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HWStarterRepoTheme {
                BasketballApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasketballApp(viewModel: GameViewModel = viewModel()) {
    val games by viewModel.games.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val selectedDate by viewModel.selectedDate.collectAsState()
    val selectedGender by viewModel.selectedGender.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("NCAA Basketball") })
        }
    ) { innerPadding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
        ) {
            GenderToggle(
                selectedGender = selectedGender,
                onGenderSelected = { viewModel.onGenderSelected(it) }
            )

            DatePickerBar(
                selectedDate = selectedDate,
                onDateSelected = { viewModel.onDateSelected(it) }
            )

            if (isLoading) {
                LoadingIndicator()
            } else {
                LazyColumn {
                    items(games) { game ->
                        GameCard(game = game)
                    }
                }
            }
        }
    }
}