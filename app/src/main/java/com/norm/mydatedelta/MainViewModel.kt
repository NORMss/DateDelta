package com.norm.mydatedelta

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlin.time.Duration.Companion.milliseconds

data class DateState(
    var firstDate: Long = 0L,
    var secondDate: Long = 0L,
    var isIncludeDeadline: Boolean = false,
    var deltaDateString: String = "",
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
            is Actions.OnIncludeDeadline -> onIncludeDeadline(actions.checked)
            is Actions.GetDeltaDateString -> calculateDeltaDateString()
        }
    }

    private fun setFirstDate(date: Long) {
        state.firstDate = date
    }

    private fun setSecondDate(date: Long) {
        state.secondDate = date
    }

    private fun onIncludeDeadline(checked: Boolean) {
        state.isIncludeDeadline = checked
        Log.d("MyLog", state.isIncludeDeadline.toString())
    }

    private fun calculateDeltaDateString() {
        if (state.isIncludeDeadline)
            state.deltaDateString = ((state.firstDate - state.secondDate).milliseconds).toString()
        else {
            state.deltaDateString =
                ((state.firstDate - state.secondDate).milliseconds).toString() + " + 1"
        }
    }
}