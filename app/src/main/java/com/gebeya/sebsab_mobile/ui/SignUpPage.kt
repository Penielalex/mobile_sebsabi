package com.gebeya.sebsab_mobile.ui

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gebeya.sebsab_mobile.R
import com.gebeya.sebsab_mobile.data.network.entity.Status
import com.gebeya.sebsab_mobile.data.network.entity.Worker
import com.gebeya.sebsab_mobile.ui.components.DatePicker
import com.gebeya.sebsab_mobile.ui.components.toFormattedDate
import com.gebeya.sebsab_mobile.ui.theme.poppinsFamily
import com.gebeya.sebsab_mobile.viewmodel.SignUpPageViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.Instant
import java.util.Calendar
import java.util.Date


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpPage(
    navToLoginPage:()->Unit,
    scope: CoroutineScope,
    snackbarHostState:SnackbarHostState
) {

    val name = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val date = remember {
        mutableStateOf(Instant.now())
    }
    val qualification = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val confirmPassword = remember { mutableStateOf("") }
    val passwordVisible = remember { mutableStateOf(false) }
    val confirmPasswordVisible = remember { mutableStateOf(false) }


    val signUpPageViewModel = hiltViewModel<SignUpPageViewModel>()
    val result by remember { signUpPageViewModel.result }.collectAsState()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    )
    {
        Spacer(modifier = Modifier.height(40.dp))
        Image(


            modifier = Modifier.height(100.dp),

            painter = painterResource(id = R.drawable.logo), contentDescription = "front image",
            colorFilter = ColorFilter.tint(Color(0XFFFAFF0F), blendMode = BlendMode.Dst)
        )
        Text(
            text = "Sebsabi",
            color = Color(0xFF909300),
            fontWeight = FontWeight.Medium,
            fontFamily = poppinsFamily,
            fontSize = 32.sp,

            textAlign = TextAlign.Justify
        )
        Spacer(modifier = Modifier.height(40.dp))
        TextField(
            modifier = Modifier
                .clip(CircleShape)
                .width(360.dp),

            value = name.value,
            onValueChange = { newValue ->
                // Set the new value to the mutableState value
                name.value = newValue
            },
            isError = signUpPageViewModel.nameError.value.isNotEmpty(),
            supportingText = {
                Text(
                    modifier = Modifier.padding(start = 10.dp),
                    text = signUpPageViewModel.nameError.value
                )
            },
            label = {
                Text(
                    text = "Full name",
                    fontWeight = FontWeight.Medium,
                    fontFamily = poppinsFamily,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Justify
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),

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
        //Spacer(modifier = Modifier.height(10.dp))
        TextField(
            modifier = Modifier
                .clip(CircleShape)
                .width(360.dp),

            value = email.value,
            onValueChange = { newValue ->
                // Set the new value to the mutableState value
                email.value = newValue
            },
            isError = signUpPageViewModel.emailError.value.isNotEmpty(),
            supportingText = {
                Text(
                    modifier = Modifier.padding(start = 10.dp),
                    text = signUpPageViewModel.emailError.value
                )
            },
            label = {
                Text(
                    text = "Email",
                    fontWeight = FontWeight.Medium,
                    fontFamily = poppinsFamily,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Justify
                )
            },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),


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

        TextField(
            modifier = Modifier
                .clip(CircleShape)
                .width(360.dp),

            value = qualification.value,
            onValueChange = { newValue ->
                // Set the new value to the mutableState value
                qualification.value = newValue
            },
            isError = signUpPageViewModel.qualificationError.value.isNotEmpty(),
            supportingText = {
                Text(
                    modifier = Modifier.padding(start = 10.dp),
                    text = signUpPageViewModel.qualificationError.value
                )
            },
            label = {
                Text(
                    text = "Qualification",
                    fontWeight = FontWeight.Medium,
                    fontFamily = poppinsFamily,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Justify
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),

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

        Column {
            Text(
                modifier = Modifier.padding(start = 10.dp),
                text = "Date of birth: ",
                color = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.5f),
                fontWeight = FontWeight.Medium,
                fontFamily = poppinsFamily,
                fontSize = 12.sp,
                textAlign = TextAlign.Justify
            )
            Spacer(modifier = Modifier.height(5.dp))

            DatePicker(
                front ="",
                date = {
                    date.value = it
                }
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        TextField(
            modifier = Modifier
                .clip(CircleShape)
                .width(360.dp),
            value = password.value,
            onValueChange = { newValue ->
                // Set the new value to the mutableState value
                password.value = newValue
            },
            isError = signUpPageViewModel.passwordError.value.isNotEmpty(),
            supportingText = {
                Text(
                    modifier = Modifier.padding(start = 10.dp),
                    text = signUpPageViewModel.passwordError.value
                )
            },
            visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (passwordVisible.value) {
                    Icons.Filled.Visibility

                } else {
                    Icons.Filled.VisibilityOff
                }
                val description = if (passwordVisible.value) "Hide password" else "Show password"

                IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                    Icon(imageVector = image, description)
                }
            },
            label = {
                Text(
                    text = "Password",
                    fontWeight = FontWeight.Medium,
                    fontFamily = poppinsFamily,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Justify
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),

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
        //Spacer(modifier = Modifier.height(10.dp))
        TextField(
            modifier = Modifier
                .clip(CircleShape)
                .width(360.dp),
            value = confirmPassword.value,
            onValueChange = { newValue ->
                // Set the new value to the mutableState value
                confirmPassword.value = newValue
            },
            isError = signUpPageViewModel.confirmPasswordError.value.isNotEmpty(),
            supportingText = {
                Text(
                    modifier = Modifier.padding(start = 10.dp),
                    text = signUpPageViewModel.confirmPasswordError.value
                )
            },
            visualTransformation = if (confirmPasswordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (confirmPasswordVisible.value) {
                    Icons.Filled.Visibility

                } else {
                    Icons.Filled.VisibilityOff
                }
                val description =
                    if (confirmPasswordVisible.value) "Hide password" else "Show password"

                IconButton(onClick = {
                    confirmPasswordVisible.value = !confirmPasswordVisible.value
                }) {
                    Icon(imageVector = image, description)
                }
            },
            label = {
                Text(
                    text = "Confirm Password",
                    fontWeight = FontWeight.Medium,
                    fontFamily = poppinsFamily,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Justify
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),

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
        // Spacer(modifier = Modifier.height(40.dp))
        Button(
            onClick = {
                signUpPageViewModel.validateInput(
                    name.value,
                    email.value,
                    password.value,
                    confirmPassword.value,
                    qualification.value,
                    date.value
                )
                if (
                    signUpPageViewModel.nameError.value.isEmpty() &&
                    signUpPageViewModel.emailError.value.isEmpty() &&
                    signUpPageViewModel.passwordError.value.isEmpty() &&
                    signUpPageViewModel.confirmPasswordError.value.isEmpty() &&
                    signUpPageViewModel.qualificationError.value.isEmpty() &&
                    signUpPageViewModel.dateError.value.isEmpty()
                ) {

                    val nameParts = name.value.split(" ")
                    val firstName = nameParts.firstOrNull() ?: ""
                    val lastName = nameParts.drop(1).joinToString(" ")
                    println("$firstName $lastName ${email.value} ${Date.from(date.value)}")
                    val age = calculateAge(date.value)
                    print("age: $age")


                    scope.launch {
                        try {
                            signUpPageViewModel.isloading.value = true // Set loading to true when registration starts
                            val registrationResult = signUpPageViewModel.registerWorker(
                                Worker(
                                    firstName = firstName,
                                    lastName = lastName,
                                    email = email.value,
                                    qualification = qualification.value,
                                    dob = Date.from(date.value).toFormattedDate(),
                                    password = password.value,
                                    age = age,
                                    isActive = Status.Active
                                )
                            )


                            println("Result value: ${registrationResult}")



                                if ( registrationResult== "User Registered") {
                                    snackbarHostState.showSnackbar("User registered")
                                } else {
                                    snackbarHostState.showSnackbar("User Not Registered")
                                }



                            println(result)




                        } finally {

                            signUpPageViewModel.isloading.value = false
                            if(signUpPageViewModel.isloading.value == false){println(result)}// Set loading to false when registration completes (success or fail)
                        }
                    }

                }
            },

            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 40.dp, end = 40.dp),
            enabled = !signUpPageViewModel.isloading.value
        ) {if (signUpPageViewModel.isloading.value) {
            // Show circular progress indicator when loading
            CircularProgressIndicator(
                modifier = Modifier
                    .size(20.dp)
                    .padding(end = 8.dp),
                color = Color.White
            )
        } else {
            // Show "Sign Up" text when not loading
            Text(
                text = "Sign Up",
                fontWeight = FontWeight.Medium,
                fontFamily = poppinsFamily,
                fontSize = 12.sp,
                color = Color.White
            )
        }

        }

        Text(
            modifier = Modifier.clickable { navToLoginPage() },
            text = "Already have an account?",
            fontWeight = FontWeight.Medium,
            fontFamily = poppinsFamily,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.primary,
        )
        Spacer(modifier = Modifier.height(40.dp))


    }


}

@SuppressLint("NewApi")
private fun calculateAge(dob: Instant): Int {
    val birthDate = Date.from(dob)
    val currentDate = Date()

    val birthCalendar = Calendar.getInstance().apply {
        time = birthDate
    }

    val currentCalendar = Calendar.getInstance().apply {
        time = currentDate
    }

    var age = currentCalendar.get(Calendar.YEAR) - birthCalendar.get(Calendar.YEAR)

    // Adjust age if the birth date hasn't occurred yet this year
    if (currentCalendar.get(Calendar.DAY_OF_YEAR) < birthCalendar.get(Calendar.DAY_OF_YEAR)) {
        age--
    }

    return age
}




