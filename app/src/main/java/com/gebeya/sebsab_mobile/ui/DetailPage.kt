package com.gebeya.sebsab_mobile.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.gebeya.sebsab_mobile.R
import com.gebeya.sebsab_mobile.data.network.entity.Proposal
import com.gebeya.sebsab_mobile.ui.theme.poppinsFamily
import com.gebeya.sebsab_mobile.viewmodel.HomePageViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPage(
    id:Int,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState
){

    val homepageViewModel = hiltViewModel<HomePageViewModel>()
    var isDialogVisible by remember { mutableStateOf(false) }
    var proposalText by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }

    val formModel = homepageViewModel.getFormById(id)



    Column (
        modifier = Modifier.padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ){
        Spacer(modifier = Modifier.height(40.dp))
        Image(


            modifier = Modifier.height(100.dp),

            painter = painterResource(id = R.drawable.logo), contentDescription = "front image",
            colorFilter = ColorFilter.tint(Color(0XFFFAFF0F), blendMode = BlendMode.Dst)
        )

        Spacer(modifier = Modifier.height(40.dp))

        Column {


            Text(
                text = "Title",
                color = MaterialTheme.colorScheme.primary,
                fontFamily = poppinsFamily,
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold,
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()// Set the background color
                    .border(
                        2.dp,
                        MaterialTheme.colorScheme.primary,
                        shape = MaterialTheme.shapes.medium
                    )// Set padding
                    .background(
                        color = Color.Transparent, // Set the background color
                        shape = RoundedCornerShape(25.dp) // Set the shape to CircleShape or any other shape
                    )// Add rounded corners (optional)
            ) {
                if (formModel != null) {
                    Text(
                        modifier = Modifier.padding(10.dp),
                        text = formModel.title,
                        fontFamily = poppinsFamily
                    )
                }
            }
            Text(
                text = "Description:",
                color = MaterialTheme.colorScheme.primary,
                fontFamily = poppinsFamily,
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold,
            )
            Box(

                modifier = Modifier
                    .fillMaxWidth()// Set the background color
                    .border(
                        2.dp,
                        MaterialTheme.colorScheme.primary,
                        shape = MaterialTheme.shapes.medium,
                    )// Set padding
                    .background(
                        color = Color.Transparent, // Set the background color
                        shape = RoundedCornerShape(25.dp) // Set the shape to CircleShape or any other shape
                    )// Add rounded corners (optional)
            ) {
                if (formModel != null) {
                    Text(
                        modifier = Modifier.padding(10.dp),
                        text = formModel.description,
                        fontFamily = poppinsFamily
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))

            if (formModel != null) {
                Text(
                    modifier = Modifier.padding(10.dp),
                    text = "Required ${formModel.usageLimit} people"
                )
            }
        }
        Column(
            modifier = Modifier.padding(10.dp)
                .fillMaxWidth(),

        ) {

            Button(
                modifier = Modifier.padding(10.dp)
                    .fillMaxWidth(),
                onClick = { /*TODO*/ },
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray)
            ) {
                Text(text = " Save ", color = Color(0XFF909300))
            }
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                modifier = Modifier.padding(10.dp)
                    .fillMaxWidth(),
                onClick = { isDialogVisible=true },
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0XFF909300)),

            ) {
                    Text(text = " Apply ")

            }
        }

        if(isDialogVisible){
            Dialog(onDismissRequest = { isDialogVisible = false },
                properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
            ) {
                Column (
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .padding(16.dp)
                        .fillMaxWidth()
                ){

                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "Proposal submission:",
                        color = MaterialTheme.colorScheme.primary,fontFamily = poppinsFamily, fontSize = 19.sp, fontWeight = FontWeight.Bold,
                    )
                    TextField(
                        value = proposalText,
                        onValueChange = { newText -> proposalText = newText },
                        label = { Text("Write your proposal for the job") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .height(200.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    TextField(
                        value = price,
                        onValueChange = { newText -> price = newText },
                        label = { Text("Price per Person") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .height(50.dp),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(
                            onClick = {
                                scope.launch {
                                    try{

                                        val proposalResult= homepageViewModel.submitProposal(id.toLong(),
                                            Proposal(
                                           proposalText= proposalText,
                                            ratePerForm = price.toDouble()
                                        )
                                        )
                                        print("Proposal Result $proposalResult")




                                    }finally{
                                       isDialogVisible = false
                                        snackbarHostState.showSnackbar("Proposal Submitted")

                                    }
                                }


                            },
                            shape = RoundedCornerShape(30.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0XFF909300))
                        ) {
                            Icon(tint=Color.White,imageVector = Icons.Outlined.Send,contentDescription = null)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Send")
                        }
                    }


                }


            }
        }
    }


}