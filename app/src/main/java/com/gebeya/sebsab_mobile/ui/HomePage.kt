package com.gebeya.sebsab_mobile.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gebeya.sebsab_mobile.ui.components.JobsCard
import com.gebeya.sebsab_mobile.viewmodel.HomePageViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(
    viewDetail: (id: Int) -> Unit
){
    val homepageViewModel = hiltViewModel<HomePageViewModel>()
    var searchText by remember { mutableStateOf(TextFieldValue())}
    val contentList by homepageViewModel.contentListLiveData.observeAsState()

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            TextField(
                modifier = Modifier
                    .clip(CircleShape)
                    .weight(1f)
                    .padding(vertical = 12.dp)
                    .height(50.dp),
                value = searchText,
                onValueChange = { newValue ->
                    // Set the new value to the mutableState value
                    searchText = newValue
                },
                placeholder = {
                    Text(
                        text = "Search",
                        fontWeight = FontWeight.Medium,
                        fontSize = 12.sp
                    )
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent
                ),
                trailingIcon = {
                    IconButton(
                        onClick = { },
                        modifier = Modifier.size(48.dp),
                        content = {
                            Icon(Icons.Filled.Search, contentDescription = "Search", tint = Color(0XFF909300))
                        },
                        //tint = Color.Green // Set the icon button color to green
                    )
                }
            )
        }


        Row(modifier = Modifier.padding(10.dp)) {
            Button(
                onClick = {  homepageViewModel.getFormsByStatus() },
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0XFF909300))
            ) {
                Text(text = "All")
            }
            Spacer(modifier = Modifier.width(10.dp))


        }



        if (homepageViewModel.formList.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(homepageViewModel.formList) {
                    JobsCard(id = it.id, title = it.title, description = it.description, usageLimit = it.usageLimit, viewDetail = { id -> viewDetail(id) })
                }
            }
        } else {
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
