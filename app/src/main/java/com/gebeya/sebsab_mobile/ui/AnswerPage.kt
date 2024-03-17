package com.gebeya.sebsab_mobile.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.*
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gebeya.sebsab_mobile.data.network.entity.Answer
import com.gebeya.sebsab_mobile.data.network.entity.UserResponse
import com.gebeya.sebsab_mobile.ui.theme.poppinsFamily
import com.gebeya.sebsab_mobile.viewmodel.AnswerPageViewModel
import com.gebeya.sebsab_mobile.viewmodel.JobsPageViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnswerPage(
    id: Int,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState
) {
    val jobsPageViewModel = hiltViewModel<JobsPageViewModel>()
    val answerPageViewModel= hiltViewModel<AnswerPageViewModel>()


    var answers = remember { mutableStateOf(mutableMapOf<Long, Answer>()) }



    jobsPageViewModel.getFormById(id)
    val selectedForm by jobsPageViewModel.selectedForm.collectAsState()

    if (selectedForm.isNullOrEmpty()) {
        // Loading indicator
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp), // Adjust padding as needed
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(60.dp)
                    .padding(10.dp)
            )
        }
    } else {
        // Displaying form details
        selectedForm?.let { form ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),

            ) {
                // Form Title
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
                            shape = MaterialTheme.shapes.medium,
                        )// Set padding
                        .background(
                            color = Color.Transparent, // Set the background color
                            shape = RoundedCornerShape(25.dp) // Set the shape to CircleShape or any other shape
                        )// Add rounded corners (optional)
                ){
                Text(
                    text = form["title"] as? String ?: "",
                    modifier = Modifier.padding(10.dp),
                    fontFamily = poppinsFamily
                )}

                // Form Description
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
                ){
                Text(
                    text = form["description"] as? String ?: "",
                    modifier = Modifier.padding(10.dp),
                    fontFamily = poppinsFamily
                )}
Spacer(modifier = Modifier.height(20.dp))
                // Questions Section
                Text(
                    text = "Questions:",
                    modifier = Modifier.padding(10.dp),
                    fontFamily = poppinsFamily,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold,
                )

                // LazyColumn for Questions
                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ){
                    form["questions"]?.let { questions ->
                        // Assuming each question is a map with a "text" key
                        for (question in questions as List<Map<String, Any>>) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                                    .padding(16.dp)
                            ) {
                                Text(
                                    text = "${questions.indexOf(question)+1}) ${question["questionText"] as? String ?: ""}",
                                    modifier = Modifier.padding(bottom = 8.dp),
                                            fontFamily = poppinsFamily,
                                    fontWeight = FontWeight.SemiBold,

                                )

                                when (question["questionType"] as? String) {
                                    "TEXT" -> {
                                        // Display text input field
                                        val questionId = question["id"] as Double ?: 0
                                        val textState = remember(questionId) { mutableStateOf("") }
                                        TextField(
                                            value = textState.value,
                                            onValueChange = {newValue ->
                                                textState.value = newValue
                                                answers.value[questionId.toLong()] = Answer(questionId.toLong(),listOf(),textState.value,null)
                                            },
                                            label = { Text("Insert answer") },
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(8.dp)
                                                .clip(RoundedCornerShape(10.dp))
                                                .height(100.dp),
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
                                       // Text("${answers.value.values.toList()}")
                                    }
                                    "TRUE_FALSE" ->{
                                        var trueFalse by remember { mutableStateOf<Boolean?>(null) }
                                        val questionId = question["id"] as Double ?: 0

                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier.padding(bottom = 4.dp)
                                        ) {
                                            RadioButton(
                                                selected =  trueFalse == true,
                                                onClick = {
                                                    trueFalse = true
                                                    answers.value[questionId.toLong()] = Answer(questionId.toLong(),listOf(),
                                                        trueFalse.toString(),null)
                                                    // Handle true option
                                                },
                                                modifier = Modifier
                                                    .padding(end = 8.dp)
                                                    .size(24.dp)
                                            )
                                            Text("True")

                                            Spacer(modifier = Modifier.width(16.dp)) // Add some space between options

                                            RadioButton(
                                                selected = trueFalse == false,
                                                onClick = {
                                                    trueFalse = false
                                                    answers.value[questionId.toLong()] = Answer(questionId.toLong(),listOf(),
                                                        trueFalse.toString(),null)
                                                    // Handle false option
                                                },
                                                modifier = Modifier
                                                    .padding(end = 8.dp)
                                                    .size(24.dp)
                                            )
                                            Text("False")
                                        }
                                    }


                                    "MULTIPLE_CHOICE" -> {
                                        // Display multiple choice options
                                        val options = (question["multipleChoiceOptions"] as? List<Map<String, Any>>)?.toMutableList() ?: mutableListOf()
                                        val questionId = question["id"] as Double ?: 0

                                        var selectedIndex by remember { mutableStateOf(-1) }

                                        options.forEachIndexed { index, option ->
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                modifier = Modifier.padding(bottom = 4.dp)
                                            ) {
                                                RadioButton(
                                                    selected = index == selectedIndex,
                                                    onClick = {
                                                        // Set the selected index to the clicked option
                                                        selectedIndex = index
                                                        answers.value[questionId.toLong()] = Answer(questionId.toLong(),listOf((option["id"] as Double).toLong()),
                                                            null,null)

                                                    },
                                                    modifier = Modifier
                                                        .padding(end = 8.dp)
                                                        .size(24.dp)
                                                )
                                                Text(
                                                    text = "${option["optionText"]}",
                                                    modifier = Modifier.weight(1f)
                                                )
//                                                Text(
//                                                    text = "id  ${option["id"]}",
//                                                    modifier = Modifier.weight(1f)
//                                                )
                                            }
                                        }
                                    }

                                    "RATING_SCALE" -> {
                                        // Display rating range input
                                        val questionId = question["id"] as Double ?: 0
                                        var rating by remember { mutableStateOf(0) }
                                        Column {
                                            StarRatingBar(
                                                maxStars = 5,
                                                rating = rating,
                                                onRatingChanged = {
                                                    rating = it
                                                    answers.value[questionId.toLong()] = Answer(questionId.toLong(),listOf(),
                                                        null,rating)
                                                })
                                            //Text("$rating")
                                        }

                                    }
                                    else -> {
                                        // Handle other question types as needed
                                    }
                                }
                            }
                        }
                    }
                }

                // Submit Answers Button
                Button(
                    onClick = {
                              scope.launch {
                                  try{
                                      answerPageViewModel.isloading.value = true
                                      val progress=jobsPageViewModel.getProgress(id.toLong())
                                      val usage = selectedForm?.let { form -> form["usageLimit"]}
                                      if(progress != usage){
                                      val submissionResult = answerPageViewModel.sumbitAnswer(
                                          UserResponse(
                                              formId = id.toLong(),
                                              answers= answers.value.values.toList()
                                          )
                                      )

                                      if ( submissionResult== "Answer Submitted") {
                                          snackbarHostState.showSnackbar("Answer has been Submitted")
                                      } else {
                                          snackbarHostState.showSnackbar("You have reached Your limit. Thank You!")
                                      }}else{
                                          snackbarHostState.showSnackbar("You have reached Your limit. Thank You")
                                      }
                                  }finally{
                                      answerPageViewModel.isloading.value = false

                                  }
                              }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    enabled = !answerPageViewModel.isloading.value
                ) {
                    if (answerPageViewModel.isloading.value) {
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
                            text = "Submit Amswer",
                            fontWeight = FontWeight.Medium,
                            fontFamily = poppinsFamily,
                            fontSize = 12.sp,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun StarRatingBar(
    maxStars: Int = 5,
    rating: Int,
    onRatingChanged: (Int) -> Unit
) {
    val density = LocalDensity.current.density
    val starSize = (12f * density).dp
    val starSpacing = (0.5f * density).dp

    Row(
        modifier = Modifier.selectableGroup(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 1..maxStars) {
            val isSelected = i <= rating
            val icon = if (isSelected) Icons.Filled.Circle else Icons.Default.Circle
            val iconTintColor = if (isSelected) Color(0xFFFFC700) else Color(0x20FFFFFF)
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTintColor,
                modifier = Modifier
                    .selectable(
                        selected = isSelected,
                        onClick = {
                            onRatingChanged(i)
                        }
                    )
                    .width(starSize)
                    .height(starSize)
            )

            if (i < maxStars) {
                Spacer(modifier = Modifier.width(starSpacing))
            }
        }
    }
}



