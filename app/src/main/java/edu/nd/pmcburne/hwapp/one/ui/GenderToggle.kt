package edu.nd.pmcburne.hwapp.one.ui

import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun GenderToggle(
    selectedGender: String,
    onGenderSelected: (String) -> Unit
) {
    val genders = listOf("men", "women")
    val selectedIndex = genders.indexOf(selectedGender)

    TabRow(selectedTabIndex = selectedIndex) {
        Tab(
            selected = selectedIndex == 0,
            onClick = { onGenderSelected("men") },
            text = { Text("Men's") }
        )
        Tab(
            selected = selectedIndex == 1,
            onClick = { onGenderSelected("women") },
            text = { Text("Women's") }
        )
    }
}