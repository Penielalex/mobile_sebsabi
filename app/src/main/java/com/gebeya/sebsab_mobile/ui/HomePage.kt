package com.gebeya.sebsab_mobile.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gebeya.sebsab_mobile.ui.components.JobsCard
import com.gebeya.sebsab_mobile.viewmodel.HomePageViewModel

@Composable
fun HomePage(
    viewDetail: (id: Int) -> Unit
){
    val homepageViewModel = hiltViewModel<HomePageViewModel>()

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        Row(modifier = Modifier.padding(10.dp)) {
            Button(
                onClick = {  homepageViewModel.getFormsByStatus() },
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0XFF909300))
            ) {
                Text(text = "All")
            }
            Spacer(modifier = Modifier.width(10.dp))
            Button(
                onClick = { homepageViewModel.getFormsByStatus() },
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0XFF909300))
            ) {
                Text(text = "New Arrival")
            }
            Spacer(modifier = Modifier.width(10.dp))
            Button(
                onClick = { /*TODO*/ },
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0XFF909300))
            ) {
                Text(text = "Suggested")
            }
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
