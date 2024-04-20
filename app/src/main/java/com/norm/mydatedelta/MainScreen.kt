@file:OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class
)

package com.norm.mydatedelta

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    state: DateState,
    onAction: (Actions) -> Unit,
) {
    val snackState = remember { SnackbarHostState() }
    val snackScope = rememberCoroutineScope()

    val stateDateRange = rememberDateRangePickerState()
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackState, Modifier.zIndex(1f))
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = padding.calculateTopPadding(),
                    bottom = padding.calculateBottomPadding(),
                ),
            verticalArrangement = Arrangement.Top,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, end = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                var checked by remember {
                    mutableStateOf(state.isIncludeDeadline)
                }
                Switch(
                    checked = state.isIncludeDeadline,
                    onCheckedChange = {
                        onAction(Actions.OnIncludeDeadline(it))
                    },
                )
                TextButton(
                    onClick = {
                        onAction(Actions.SetFirstDate(stateDateRange.selectedStartDateMillis!!))
                        onAction(Actions.SetSecondDate(stateDateRange.selectedEndDateMillis!!))
                        onAction(Actions.GetDeltaDateString)
                        snackScope.launch {
                            val range = state.deltaDateString
                            snackState.showSnackbar("Saved range (timestamps): $range")
                        }
                    },
                    enabled = stateDateRange.selectedEndDateMillis != null
                ) {
                    Text(
                        text = "Calculate Date"
                    )
                }
            }
            DateRangePicker(state = stateDateRange, modifier = Modifier.weight(1f))
        }
    }
}