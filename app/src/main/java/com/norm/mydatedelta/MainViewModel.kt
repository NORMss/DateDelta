package com.norm.mydatedelta

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.milliseconds

data class DateState(
    val firstDate: Long = 0L,
    val secondDate: Long = 0L,
    val isIncludeDeadline: Boolean = false,
    val deltaDateString: String = "",
)

sealed class Actions {
    data class SetFirstDate(val date: Long) : Actions()
    data class SetSecondDate(val date: Long) : Actions()
    data class OnIncludeDeadline(val checked: Boolean) : Actions()
    data object GetDeltaDateString : Actions()
}

class MainViewModel : ViewModel() {
    var state by mutableStateOf(DateState())
        private set

    fun onAction(actions: Actions) {
        when (actions) {
            is Actions.SetSecondDate -> setFirstDate(actions.date)
            is Actions.SetFirstDate -> setSecondDate(actions.date)
            is Actions.OnIncludeDeadline -> onIncludeDeadline()
            is Actions.GetDeltaDateString -> calculateDeltaDateString()
        }
    }

    private fun setFirstDate(date: Long) {
        state = state.copy(firstDate = date)
    }

    private fun setSecondDate(date: Long) {
        state = state.copy(secondDate = date)
    }

    private fun onIncludeDeadline() {
        state = state.copy(isIncludeDeadline = !state.isIncludeDeadline)
        Log.d("MyLog", state.isIncludeDeadline.toString())
    }

    private fun calculateDeltaDateString() {
        state = if (state.isIncludeDeadline)
            state.copy(deltaDateString = ((state.firstDate - state.secondDate).milliseconds + 1.days).toString())
        else {
            state.copy(deltaDateString = ((state.firstDate - state.secondDate).milliseconds).toString())
        }
    }
}