package com.gebeya.sebsab_mobile.ui.components





import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.gebeya.sebsab_mobile.R

@Composable
fun CompletedJobs(
    id: Int,
    title: String,
    description: String,
    usageLimit: Int,
    viewDetail: (id: Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .padding(10.dp)
            .clickable {
                viewDetail(id)
            },
        shape = RoundedCornerShape(1.dp),
        backgroundColor = Color.Transparent
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            horizontalArrangement = Arrangement.Start

        ) {
            // Image on the left
            Image(
                painter = painterResource(id = R.drawable.logo), // Replace with your actual image resource
                contentDescription = null, // Provide a meaningful description
                modifier = Modifier
                    .size(48.dp)
                    .padding(end = 16.dp)
            )

            // Text content on the right
            Column (
                verticalArrangement = Arrangement.SpaceBetween
            ){
                Text(
                    text = title,
                    style = TextStyle(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(bottom = 8.dp),


                    )

                Text(
                    text = description,
                    style = TextStyle(fontWeight = FontWeight.Bold,color = Color(0XFF909300)),
                    modifier = Modifier.padding(bottom = 8.dp)
                        .height(60.dp)
                )
                Text(
                    text = "Payment Pending...",
                    style = TextStyle(color = Color.Red)
                )
            }

        }
    }
}