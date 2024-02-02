package com.gebeya.sebsab_mobile.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gebeya.sebsab_mobile.R
import com.gebeya.sebsab_mobile.ui.theme.poppinsFamily
import com.gebeya.sebsab_mobile.viewmodel.LoginPageViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginPage(

    navToSignUpPage:()->Unit,
    scope: CoroutineScope,
    snackbarHostState:SnackbarHostState
){


    val email = remember{ mutableStateOf("") }
    val password = remember{ mutableStateOf("") }
    val passwordVisible= remember{ mutableStateOf(false)}


    val loginPageViewModel= hiltViewModel<LoginPageViewModel>()

    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {
        Spacer(modifier = Modifier.height(40.dp))
        Image(


            modifier= Modifier.height(100.dp),

            painter = painterResource(id = R.drawable.logo), contentDescription ="front image",
            colorFilter = ColorFilter.tint(Color(0XFFFAFF0F), blendMode = BlendMode.Dst)
        )
        Text(text = "Sebsabi",
            color= Color(0xFF909300),
            fontWeight = FontWeight.Medium,
            fontFamily = poppinsFamily,
            fontSize = 32.sp,

            textAlign = TextAlign.Justify)
        Spacer(modifier = Modifier.height(40.dp))


        TextField(
            modifier = Modifier
                .clip(CircleShape)
                .width(360.dp),

            value = email.value,
            onValueChange = { newValue ->
                // Set the new value to the mutableState value
                email.value = newValue },
            isError = loginPageViewModel.emailError.value.isNotEmpty(),
            supportingText = { Text(modifier = Modifier.padding(start=10.dp), text = loginPageViewModel.emailError.value) },
            label = { Text(text = "Email",
                fontWeight = FontWeight.Medium,
                fontFamily = poppinsFamily,
                fontSize = 12.sp,
                textAlign = TextAlign.Justify) },
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


        TextField(
            modifier = Modifier
                .clip(CircleShape)
                .width(360.dp),

            value = password.value,
            onValueChange = { newValue ->
                // Set the new value to the mutableState value
                password.value = newValue },
            isError = loginPageViewModel.passwordError.value.isNotEmpty(),
            supportingText = { Text(modifier = Modifier.padding(start=10.dp), text = loginPageViewModel.passwordError.value) },
            visualTransformation = if(passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if(passwordVisible.value){
                    Icons.Filled.Visibility

                }else{
                    Icons.Filled.VisibilityOff}
                val description = if (passwordVisible.value) "Hide password" else "Show password"

                IconButton(onClick = {passwordVisible.value = !passwordVisible.value}){
                    Icon(imageVector  = image, description)
                }
            },
            label = { Text(text = "Password",
                fontWeight = FontWeight.Medium,
                fontFamily = poppinsFamily,
                fontSize = 12.sp,
                textAlign = TextAlign.Justify) },
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

        Text(modifier = Modifier
            .clickable { }
            .offset(x = 100.dp),

            text = "Forgot password?",
            fontWeight = FontWeight.Medium,
            fontFamily = poppinsFamily,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.primary,)

        Spacer(modifier = Modifier.height(40.dp))
        Button( onClick = {
            loginPageViewModel.validateInput(email.value, password.value)
            if(

                loginPageViewModel.emailError.value.isEmpty() &&
                loginPageViewModel.passwordError.value.isEmpty()

            ){
                scope.launch{
                    snackbarHostState.showSnackbar("User Logged-In")
                }
            }
        },

            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 40.dp, end = 40.dp),
        ) {
            Text(text = "Sign Up",
                fontWeight = FontWeight.Medium,
                fontFamily = poppinsFamily,
                fontSize = 12.sp,
                color = Color.White)

        }

        Text(modifier = Modifier.clickable { navToSignUpPage() },
            text = "Don't have an account?",
            fontWeight = FontWeight.Medium,
            fontFamily = poppinsFamily,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.primary,)



    }
}