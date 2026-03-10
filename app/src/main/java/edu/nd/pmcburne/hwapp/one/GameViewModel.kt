package edu.nd.pmcburne.hwapp.one

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import edu.nd.pmcburne.hwapp.one.data.local.AppDatabase
import edu.nd.pmcburne.hwapp.one.data.local.GameEntity
import edu.nd.pmcburne.hwapp.one.data.repository.GameRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = GameRepository(AppDatabase.getDatabase(application))

    private val _isLoading = MutableStateFlow(false)
    private val _selectedDate = MutableStateFlow(getTodayDate())
    private val _selectedGender = MutableStateFlow("men")

    val isLoading: StateFlow<Boolean> = _isLoading
    val selectedDate: StateFlow<String> = _selectedDate
    val selectedGender: StateFlow<String> = _selectedGender

    @OptIn(ExperimentalCoroutinesApi::class)
    val games: StateFlow<List<GameEntity>> = combine(
        _selectedDate,
        _selectedGender
    ) { date, gender -> Pair(date, gender) }
        .flatMapLatest { (date, gender) ->
            repository.getGamesFromDb(date, gender)
        }
        .let { flow ->
            val state = MutableStateFlow<List<GameEntity>>(emptyList())
            viewModelScope.launch {
                flow.collect { state.value = it }
            }
            state
        }

    init {
        fetchGames()
    }

    fun fetchGames() {
        viewModelScope.launch {
            _isLoading.value = true
            repository.fetchAndStoreGames(_selectedDate.value, _selectedGender.value)
            _isLoading.value = false
        }
    }

    fun onDateSelected(date: String) {
        _selectedDate.value = date
        fetchGames()
    }

    fun onGenderSelected(gender: String) {
        _selectedGender.value = gender
        fetchGames()
    }

    private fun getTodayDate(): String {
        val sdf = SimpleDateFormat("MM/dd/yyyy", Locale.US)
        sdf.timeZone = TimeZone.getTimeZone("UTC")
        return sdf.format(Date())
    }
}