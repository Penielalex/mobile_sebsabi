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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gebeya.sebsab_mobile.ui.components.CompletedJobs
import com.gebeya.sebsab_mobile.ui.components.JobsCard
import com.gebeya.sebsab_mobile.ui.components.MyJobsCard
import com.gebeya.sebsab_mobile.viewmodel.JobsPageViewModel
import kotlinx.coroutines.delay

@SuppressLint("SuspiciousIndentation")
@Composable
fun JobsPage(
    viewDetail: (id: Int) -> Unit,
    viewApplied: (id: Int) -> Unit
) {

    val jobsPageViewModel = hiltViewModel<JobsPageViewModel>()
    jobsPageViewModel.getApplied();
    jobsPageViewModel.getClaimed();
    jobsPageViewModel.getCompleted()
    val showLoading = remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(3000) // Simulate loading for 3 seconds
        showLoading.value = false // Update the state to hide the loading indicator
    }


    val appliedOrJob = remember { mutableStateOf("Applied") }
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {

        Spacer(modifier = Modifier.height(5.dp))
        Row(modifier = Modifier.padding(5.dp)) {
            Button(
                onClick = { appliedOrJob.value = "Applied" },
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0XFF909300))
            ) {
                Text(text = " Applied jobs ")
            }
            Spacer(modifier = Modifier.width(5.dp))
            Button(
                onClick = { appliedOrJob.value = "Claimed" },
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0XFF909300))
            ) {
                Text(text = " Your Jobs ")
            }
            Spacer(modifier = Modifier.width(5.dp))
            Button(
                onClick = { appliedOrJob.value = "Completed" },
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0XFF909300))
            ) {
                Text(text = "Completed")
            }
        }

        if (appliedOrJob.value == "Applied") {
            if (jobsPageViewModel.formsApplied.value.isNotEmpty()) {
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    items(jobsPageViewModel.formsApplied.value) { form ->
                        val formId = form["id"] as? Double ?: 0.0
                        val usageLimit = (form["usageLimit"] as? Double)?.toInt() ?: 0
                        JobsCard(
                            id = formId.toInt(),
                            title = "${form["title"]}",
                            description = "${form["description"]}",
                            usageLimit = usageLimit,
                            viewDetail = { })
                    }
                }
            } else {
                if (showLoading.value) {
                    // Show the loading indicator
                    LinearProgressIndicator(
                        modifier = Modifier.fillMaxWidth()
                    )
                } else {
                    // Show "No jobs available" message
                    Text(
                        text = "No jobs available",
                        modifier = Modifier.padding(16.dp),
                        color = Color.Red // Optionally, you can customize the text color
                    )
                }
            }
        } else if (appliedOrJob.value == "Completed") {
            if (jobsPageViewModel.formsCompleted.value.isNotEmpty()) {
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    items(jobsPageViewModel.formsCompleted.value) { form ->
                        val formId = form["id"] as? Double ?: 0.0
                        val usageLimit = (form["usageLimit"] as? Double)?.toInt() ?: 0
                        CompletedJobs(
                            id = formId.toInt(),
                            title = "${form["title"]}",
                            description = "${form["description"]}",
                            usageLimit = usageLimit,
                            viewDetail = { })
                    }
                }
            } else {
                if (showLoading.value) {
                    // Show the loading indicator
                    LinearProgressIndicator(
                        modifier = Modifier.fillMaxWidth()
                    )
                } else {
                    // Show "No jobs available" message
                    Text(
                        text = "No jobs available",
                        modifier = Modifier.padding(16.dp),
                        color = Color.Red // Optionally, you can customize the text color
                    )
                }
            }

        } else {
            if (jobsPageViewModel.formsState.value.isEmpty() && jobsPageViewModel.formsState.value.isEmpty()) {
                // Both lists are empty, display a message or handle it as per your UI logic
                Text(text = "No jobs available")
            } else {
                val formsList = if (jobsPageViewModel.formsState.value.isEmpty()) {
                    // If formsClaimed is empty, use formsApplied instead
                    jobsPageViewModel.formsApplied.value
                } else {
                    jobsPageViewModel.formsState.value
                }

                LazyColumn {
                    items(formsList) { form ->
                        val formId = form["id"] as? Double ?: 0.0
                        jobsPageViewModel.getProgress(formId.toLong())
                        val usageLimit = jobsPageViewModel.formProgress.value
                        print(usageLimit);

                        if (usageLimit != null) {
                            MyJobsCard(
                                id = formId.toInt(),
                                title = "${form["title"]}",
                                description = "${form["description"]}",
                                usageLimit = usageLimit,
                                viewDetail = { id -> viewDetail(id) })
                        }
                    }
                }
            }
        }
    }
}
