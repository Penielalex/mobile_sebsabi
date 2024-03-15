package com.gebeya.sebsab_mobile.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gebeya.sebsab_mobile.data.network.entity.WorkerUpdate
import com.gebeya.sebsab_mobile.ui.components.DatePicker
import com.gebeya.sebsab_mobile.ui.components.toFormattedDate
import com.gebeya.sebsab_mobile.ui.theme.poppinsFamily
import com.gebeya.sebsab_mobile.viewmodel.ProfilePageViewModel
import com.gebeya.sebsab_mobile.viewmodel.SignUpPageViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.Instant
import java.util.Date


@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProfilePage(
    navToBack:() -> Unit,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState
) {


    val signUpPageViewModel = hiltViewModel<SignUpPageViewModel>()
    val profilePageViewModel = hiltViewModel<ProfilePageViewModel>()
    val worker by profilePageViewModel.worker.collectAsState()


    var name = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val date = remember { mutableStateOf<Instant?>(null) }
    val qualification = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val oldPassword = remember { mutableStateOf("") }
    var isDialogVisible by remember { mutableStateOf(false) }
    val passwordError = mutableStateOf("")

    val result by remember { profilePageViewModel.result }.collectAsState()

    if (worker != null) {
        Column(

            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        )
        {

            Spacer(modifier = Modifier.height(40.dp))
            Text(
                text = "My Profile",
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
                onValueChange = {
                    // Set the new value to the mutableState value
                    name.value = it
                },

                label = {
                    Text(
                        text = "${if (name.value.isNullOrEmpty()) "${worker?.firstName} ${worker?.lastName}" else ""}",
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
                    focusedLabelColor = Color.Transparent,
                    unfocusedLabelColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.5f),
                    unfocusedIndicatorColor = Color.Transparent,
                    errorSupportingTextColor = Color.Red,
                    errorCursorColor = Color.Red,
                    errorIndicatorColor = Color.Red,
                    errorLabelColor = Color.Red,

                    )
            )
            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                modifier = Modifier
                    .clip(CircleShape)
                    .width(360.dp),

                value = email.value,
                onValueChange = { newValue ->
                    // Set the new value to the mutableState value
                    email.value = newValue
                },

                label = {
                    Text(
                        text = "${if (email.value.isNullOrEmpty()) worker?.email else ""}",
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
                    focusedLabelColor = Color.Transparent,
                    unfocusedLabelColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.5f),
                    unfocusedIndicatorColor = Color.Transparent,
                    errorSupportingTextColor = Color.Red,
                    errorCursorColor = Color.Red,
                    errorIndicatorColor = Color.Red,
                    errorLabelColor = Color.Red,
                )
            )
            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                modifier = Modifier
                    .clip(CircleShape)
                    .width(360.dp),

                value = qualification.value,
                onValueChange = { newValue ->
                    // Set the new value to the mutableState value
                    qualification.value = newValue
                },


                label = {
                    Text(
                        text = "${if (qualification.value.isNullOrEmpty()) worker?.qualification else ""}",
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
                    focusedLabelColor = Color.Transparent,
                    unfocusedLabelColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.5f),
                    unfocusedIndicatorColor = Color.Transparent,
                    errorSupportingTextColor = Color.Red,
                    errorCursorColor = Color.Red,
                    errorIndicatorColor = Color.Red,
                    errorLabelColor = Color.Red,

                    )
            )
            Spacer(modifier = Modifier.height(10.dp))

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
                    front = "${worker?.dob?.substring(0, 10)}",
                    date = {
                        date.value = it
                    }
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier
                    .clickable { isDialogVisible = true }
                    .padding(8.dp)
            ) {
                Icon(
                    tint=MaterialTheme.colorScheme.primary,
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                        .padding(end = 8.dp)
                )
                Text(
                    text = "Reset Password",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 15.sp,
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.medium)
                        .clickable { isDialogVisible = true }
                        .padding(4.dp),
                )
            }
            if (isDialogVisible) {
                AlertDialog(
                    onDismissRequest = { isDialogVisible = false },
                    title = {
                        Text("Enter New Password")
                    },
                    text = {
                        Column {
                            TextField(
                                value = oldPassword.value,
                                onValueChange = { oldPassword.value = it },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                label = { Text("Old Password") },
                                keyboardOptions = KeyboardOptions(
                                    imeAction = ImeAction.Done
                                ),

                                isError = passwordError.value.isNotEmpty(),
                                supportingText = {
                                    Text(
                                        modifier = Modifier.padding(start = 10.dp),
                                        text = passwordError.value
                                    )
                                },
                                colors = TextFieldDefaults.textFieldColors(
                                    focusedLabelColor = MaterialTheme.colorScheme.primary,
                                    unfocusedLabelColor = MaterialTheme.colorScheme.tertiary.copy(
                                        alpha = 0.5f
                                    ),
                                    unfocusedIndicatorColor = Color.Transparent,
                                    errorSupportingTextColor = Color.Red,
                                    errorCursorColor = Color.Red,
                                    errorIndicatorColor = Color.Red,
                                    errorLabelColor = Color.Red,
                                )
                            )
                            TextField(
                                value = password.value,
                                onValueChange = { password.value = it },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                label = { Text("New Password") },
                                keyboardOptions = KeyboardOptions(
                                    imeAction = ImeAction.Done
                                ),

                                colors = TextFieldDefaults.textFieldColors(
                                    focusedLabelColor = MaterialTheme.colorScheme.primary,
                                    unfocusedLabelColor = MaterialTheme.colorScheme.tertiary.copy(
                                        alpha = 0.5f
                                    ),
                                    unfocusedIndicatorColor = Color.Transparent,
                                    errorSupportingTextColor = Color.Red,
                                    errorCursorColor = Color.Red,
                                    errorIndicatorColor = Color.Red,
                                    errorLabelColor = Color.Red,
                                )
                            )
                        }

                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                if (worker?.password == oldPassword.value) {
                                    println("passwords ${password.value}${oldPassword.value}")
                                    isDialogVisible = false
                                    scope.launch {
                                        snackbarHostState.showSnackbar("Save changes to reset password")
                                    }
                                } else {
                                    println("passwords ${password.value}${oldPassword.value}")
                                    passwordError.value = "Wrong Password"
                                }

                            }
                        ) {

                            Text("Reset")
                        }
                    },
                    dismissButton = {
                        Button(
                            onClick = {
                                password.value = ""
                                oldPassword.value = ""
                                isDialogVisible = false
                                println("passwords ${password.value}${oldPassword.value}")
                            }
                        ) {
                            Text("Cancel")
                        }
                    }
                )
            }

            Button(
                onClick = {

                    val nameParts = name.value.split(" ")
                    val firstName = nameParts.firstOrNull() ?: ""
                    val lastName = nameParts.drop(1).joinToString(" ")

                    scope.launch {
                        try {
                            profilePageViewModel.isloading.value = true
                            val updateResult = profilePageViewModel.updateProfile(
                                WorkerUpdate(
                                    firstName = if (name.value.isEmpty()) null else firstName,
                                    lastName = if (name.value.isEmpty()) null else lastName,
                                    email = if (email.value.isEmpty()) null else email.value,
                                    qualification = if (qualification.value.isEmpty()) null else qualification.value,
                                    dob = if (date.value == null) null else Date.from(date.value)
                                        .toFormattedDate(),
                                    password = if (password.value.isEmpty()) null else password.value,
                                    age = null,
                                    isActive = null
                                )
                            )

                            if (updateResult == "User Updated") {
                                snackbarHostState.showSnackbar("Profile Updated")
                            } else {
                                snackbarHostState.showSnackbar("There seems to be a problem, Please try again.")
                            }
                        } finally {
                            profilePageViewModel.isloading.value = false

                        }
                    }

                    println(worker?.password)


                },

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 40.dp, end = 40.dp),
                enabled = !profilePageViewModel.isloading.value

            ) {
                if (profilePageViewModel.isloading.value) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(20.dp)
                            .padding(end = 8.dp),
                        color = Color.White
                    )
                } else {
                    Text(
                        text = "Save Changes",
                        fontWeight = FontWeight.Medium,
                        fontFamily = poppinsFamily,
                        fontSize = 12.sp,
                        color = Color.White
                    )
                }
            }
            Text(
                modifier = Modifier.clickable {
                    signUpPageViewModel.logOut()
                    navToBack()
                },
                text = "Logout",
                fontWeight = FontWeight.Medium,
                fontFamily = poppinsFamily,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.primary,
            )


        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp), // Adjust padding as needed
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(40.dp)
                    .padding(10.dp)
            )
        }

    }


}
