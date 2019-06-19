package com.willowtreeapps.common



data class SettingsViewState(val numQuestions: Int,
                             val categoryDisplayValues: List<String>,
                             val isMicModeEnabled: Boolean,
                             val isWillowTree: Boolean = false,
                             val signInBtnText: String)

data class BookListItemViewState(
        val title: String,
        val author: String,
        val coverImageUrl: String,
        val id: String)

