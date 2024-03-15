package com.gebeya.sebsab_mobile.ui

import android.annotation.SuppressLint
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gebeya.sebsab_mobile.ui.components.JobsCard
import com.gebeya.sebsab_mobile.ui.components.MyJobsCard
import com.gebeya.sebsab_mobile.viewmodel.JobsPageViewModel

@SuppressLint("SuspiciousIndentation")
@Composable
fun JobsPage(
    viewDetail: (id: Int) -> Unit,
    viewApplied:(id: Int) -> Unit
){

    val jobsPageViewModel = hiltViewModel<JobsPageViewModel>()
    jobsPageViewModel.getApplied();
    jobsPageViewModel.getClaimed();


    val appliedOrJob = remember { mutableStateOf(true) }
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {

        Spacer(modifier = Modifier.height(10.dp))
        Row(modifier = Modifier.padding(10.dp)) {
            Button(
                onClick = { appliedOrJob.value=true },
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0XFF909300))
            ) {
                Text(text = " Applied jobs ")
            }
            Spacer(modifier = Modifier.width(10.dp))
            Button(
                onClick = { appliedOrJob.value=false},
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0XFF909300))
            ) {
                Text(text = " Your Jobs ")
            }
        }
        if(appliedOrJob.value){
            if(jobsPageViewModel.formsApplied.value.isNotEmpty()){
            LazyColumn( modifier = Modifier.fillMaxWidth() ){
                items(jobsPageViewModel.formsApplied.value){ form ->
                    val formId = form["id"] as? Double ?: 0.0
                    val usageLimit = (form["usageLimit"] as? Double)?.toInt() ?: 0
                    JobsCard(id = formId.toInt(), title = "${form["title"]}", description = "${form["description"]}", usageLimit =usageLimit , viewDetail = {  } )
                }
            }}else{
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }

        }else{
            if(jobsPageViewModel.formsState.value.isNotEmpty()) {
                LazyColumn {
                    items(jobsPageViewModel.formsState.value) { form ->
                        // Your UI code to display each form item
                        // For example:
                        val formId = form["id"] as? Double ?: 0.0
                        jobsPageViewModel.getProgress(formId.toLong())
                        val usageLimit = jobsPageViewModel.formProgress.value
                        print(usageLimit);

                        if (usageLimit != null) {
                            MyJobsCard(id = formId.toInt(), title = "${form["title"]}", description = "${form["description"]}", usageLimit = usageLimit, viewDetail = {id -> viewDetail(id) })
                        }
// Access other fields in a similar manner
                    }
                }
            }else{
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }

        }

    }
}