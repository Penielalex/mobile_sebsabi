package com.gebeya.sebsab_mobile.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.gebeya.sebsab_mobile.viewmodel.SignUpPageViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpPage(
    navToLoginPage:()->Unit,
    scope: CoroutineScope,
    snackbarHostState:SnackbarHostState
){

    val name = remember{ mutableStateOf("")}
    val email = remember{ mutableStateOf("")}
    val password = remember{mutableStateOf("")}
    val confirmPassword = remember{ mutableStateOf("")}
    val passwordVisible= remember{ mutableStateOf(false)}
    val confirmPasswordVisible= remember{ mutableStateOf(false)}


    val signUpPageViewModel = hiltViewModel<SignUpPageViewModel>()


   Column (
       modifier = Modifier.fillMaxSize(),
       horizontalAlignment = Alignment.CenterHorizontally,
       verticalArrangement = Arrangement.Top
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

           value = name.value,
           onValueChange = { newValue ->
               // Set the new value to the mutableState value
               name.value = newValue },
           isError = signUpPageViewModel.nameError.value.isNotEmpty(),
           supportingText = {Text(modifier = Modifier.padding(start=10.dp), text = signUpPageViewModel.nameError.value) },
           label = { Text(text = "Full name",
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
       //Spacer(modifier = Modifier.height(10.dp))
       TextField(
           modifier = Modifier
               .clip(CircleShape)
               .width(360.dp),

           value = email.value,
           onValueChange = { newValue ->
               // Set the new value to the mutableState value
               email.value = newValue },
           isError = signUpPageViewModel.emailError.value.isNotEmpty(),
           supportingText = { Text(modifier = Modifier.padding(start=10.dp), text = signUpPageViewModel.emailError.value) },
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

       //Spacer(modifier = Modifier.height(10.dp))
       TextField(
           modifier = Modifier
               .clip(CircleShape)
               .width(360.dp),
           value = password.value,
           onValueChange = { newValue ->
               // Set the new value to the mutableState value
               password.value = newValue },
           isError = signUpPageViewModel.passwordError.value.isNotEmpty(),
           supportingText = { Text(modifier = Modifier.padding(start=10.dp), text = signUpPageViewModel.passwordError.value) },
           visualTransformation = if(passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
           trailingIcon = {
                          val image = if(passwordVisible.value){
                              Icons.Filled.Visibility

                          }else{Icons.Filled.VisibilityOff}
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
               confirmPassword.value = newValue },
           isError = signUpPageViewModel.confirmPasswordError.value.isNotEmpty(),
           supportingText = { Text(modifier = Modifier.padding(start=10.dp), text = signUpPageViewModel.confirmPasswordError.value) },
           visualTransformation = if(confirmPasswordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
           trailingIcon = {
               val image = if(confirmPasswordVisible.value){
                   Icons.Filled.Visibility

               }else{Icons.Filled.VisibilityOff}
               val description = if (confirmPasswordVisible.value) "Hide password" else "Show password"

               IconButton(onClick = {confirmPasswordVisible.value = !confirmPasswordVisible.value}){
                   Icon(imageVector  = image, description)
               }
           },
           label = { Text(text = "Confirm Password",
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
       Spacer(modifier = Modifier.height(40.dp))
       Button( onClick = {
           signUpPageViewModel.validateInput(name.value,email.value, password.value, confirmPassword.value)
           if(
               signUpPageViewModel.nameError.value.isEmpty() &&
               signUpPageViewModel.emailError.value.isEmpty() &&
               signUpPageViewModel.passwordError.value.isEmpty() &&
               signUpPageViewModel.confirmPasswordError.value.isEmpty()
           ){
               scope.launch{
                   snackbarHostState.showSnackbar("User Registered")
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

       Text(modifier = Modifier.clickable { navToLoginPage() },
           text = "Already have an account?",
           fontWeight = FontWeight.Medium,
           fontFamily = poppinsFamily,
           fontSize = 12.sp,
           color = MaterialTheme.colorScheme.primary,)



   }
}




