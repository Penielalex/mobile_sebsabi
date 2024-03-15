package com.gebeya.sebsab_mobile.ui.components


import android.app.DatePickerDialog
import android.os.Build
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gebeya.sebsab_mobile.ui.theme.poppinsFamily
import com.gebeya.sebsab_mobile.viewmodel.SignUpPageViewModel
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Calendar
import java.util.Date
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePicker(
    date: (Instant) -> Unit,
    front: String
){
    val signUpPageViewModel = hiltViewModel<SignUpPageViewModel>()
    val context = LocalContext.current

    val calender = Calendar.getInstance()
    val currentYear = remember{ mutableStateOf(calender.get(Calendar.YEAR)) }
    val currentMonth = remember{ mutableStateOf(calender.get(Calendar.MONTH)) }
    val currentDay = remember{ mutableStateOf(calender.get(Calendar.DAY_OF_MONTH)) }
    println("Calendar: $currentYear $currentMonth $currentDay")
    println("Calendar: ${calender.time}")
    val selectedDate = remember { mutableStateOf(front.takeUnless { it.isNullOrBlank() } ?: calender.time.toFormattedDate()) }

    LaunchedEffect(front) {
        val initialDate = front.takeUnless { it.isNullOrBlank() } ?: calender.time.toFormattedDate()
        selectedDate.value = initialDate
    }
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed = interactionSource.collectIsPressedAsState()

    val datePickerDialog = DatePickerDialog(
        context,
        { datePicker: DatePicker, year: Int, month: Int, day: Int ->
            val newDate = Calendar.getInstance()
            newDate.set(year, month, day)
            selectedDate.value = newDate.time.toFormattedDate()
            currentYear.value = year
            currentMonth.value = month
            currentDay.value = day
            date(newDate.time.toInstant())
        }, currentYear.value, currentMonth.value, currentDay.value
    )

    TextField(
        modifier = Modifier
            .clip(CircleShape)
            .width(360.dp),
        value = selectedDate.value,
        onValueChange = {},
        readOnly = true,
        textStyle = TextStyle(color = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.5f),fontWeight = FontWeight.Medium,fontFamily = poppinsFamily,fontSize = 12.sp,),
        interactionSource = interactionSource,
        isError = signUpPageViewModel.dateError.value.isNotEmpty(),
        supportingText = {Text(modifier = Modifier.padding(start=10.dp), text = signUpPageViewModel.dateError.value) },
        trailingIcon = { Icon(imageVector = Icons.Default.DateRange, contentDescription = "")},
        colors = TextFieldDefaults.textFieldColors(
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            unfocusedLabelColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.5f),
            unfocusedIndicatorColor = Color.Transparent,
            errorSupportingTextColor = Color.Red,
            errorCursorColor = Color.Red,
            errorIndicatorColor = Color.Red,
            errorLabelColor = Color.Red,

            )

    )

    if(isPressed.value){
        datePickerDialog.show()
    }

}

fun Date.toFormattedDate(): String{
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return simpleDateFormat.format(this)
}