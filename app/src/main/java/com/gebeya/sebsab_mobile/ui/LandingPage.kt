package com.gebeya.sebsab_mobile.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gebeya.sebsab_mobile.R
import com.gebeya.sebsab_mobile.ui.theme.poppinsFamily
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LandingPage(
    navToSignUpPage: () -> Unit

){
    val pagerState = rememberPagerState(pageCount = {
        3
    })
    LaunchedEffect(Unit) {
        while (true) {
            yield()
            delay(2600)
            pagerState.animateScrollToPage(
                page = (pagerState.currentPage + 1) % (pagerState.pageCount)
            )
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ){

        Image(

            contentScale = ContentScale.FillHeight,
            modifier= Modifier.fillMaxSize(),

            painter = painterResource(id = R.drawable.formimg), contentDescription ="front image",
            colorFilter = ColorFilter.tint(Color(0XFFFAFF0F), blendMode = BlendMode.Dst)
        )


        Column(modifier = Modifier.align(Alignment.CenterStart)


        ){ HorizontalPager(state = pagerState) { page ->
                // Our page content
                when (page) {
                    0 ->{
                        Column {
                            Text(
                                text = "Get Started",
                                fontWeight = FontWeight.SemiBold,
                                fontFamily = poppinsFamily,
                                fontSize = 30.sp,
                                color = Color.White,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 10.dp)
                            )
                            Text(
                                text = "Welcome to ሰብሳቢ, Where you can collect Data, Earn and make an Impact ",
                                fontWeight = FontWeight.Medium,
                                fontFamily = poppinsFamily,
                                fontSize = 12.sp,
                                lineHeight = 12.sp,
                                color = Color.White,
                                modifier = Modifier
                                    .width(300.dp)
                                    //.fillMaxWidth()
                                    .padding(start = 10.dp)
                            )

                        }
                }
                1 -> {
                Column {
                    Text(
                        text = "Looking for work?",
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = poppinsFamily,
                        fontSize = 30.sp,
                        color = Color.White,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp)
                    )
                    Text(
                        text = "Sign Up to  ሰብሳቢ, Where you can collect Data and Earn.",
                        fontWeight = FontWeight.Medium,
                        fontFamily = poppinsFamily,
                        fontSize = 12.sp,
                        color = Color.White,
                        lineHeight = 12.sp,
                        modifier = Modifier
                            .width(300.dp)
                            //.fillMaxWidth()
                            .padding(start = 10.dp)
                    )

                }
            }
                    2 -> {
                        Column {
                            Text(
                                text = "You want your data collected?",
                                fontWeight = FontWeight.SemiBold,
                                fontFamily = poppinsFamily,
                                fontSize = 30.sp,
                                lineHeight = 30.sp,
                                color = Color.White,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 10.dp)
                            )
                            Text(
                                text = "Sign up to our website and access our dedicated platform.",
                                fontWeight = FontWeight.Medium,
                                fontFamily = poppinsFamily,
                                fontSize = 12.sp,
                                lineHeight = 12.sp,
                                color = Color.White,
                                modifier = Modifier
                                    .width(300.dp)
                                    //.fillMaxWidth()
                                    .padding(start = 10.dp)
                            )

                        }
                    }
            }


            }
       }

        Button( modifier = Modifier.align(Alignment.BottomCenter)
            .fillMaxWidth()
            .padding(bottom= 70.dp, start=20.dp,end=20.dp ),
            onClick = {navToSignUpPage()} ) {
            Text(text = "Get Started",
                    fontWeight = FontWeight.Medium,
                fontFamily = poppinsFamily,
                fontSize = 12.sp,
                color = Color.White)

        }
    }
    Row(
        Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .offset(y = 110.dp)
            //.align(Alignment.BottomStart)
            .padding(start = 10.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Bottom
    ) {
        repeat(pagerState.pageCount) { iteration ->
            val color = if (pagerState.currentPage == iteration) Color(0xFF909300) else Color.LightGray
            val width= if(pagerState.currentPage == iteration) 40.dp else 16.dp

            Box(
                modifier = Modifier
                    .padding(2.dp)
                    //.clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp, bottomStart = 8.dp, bottomEnd = 8.dp))
                    .background(color, shape = RoundedCornerShape(8.dp))
                    //.size(56.dp)
                    .width(width)
                    .height(16.dp)
            )
        }
    }
    

        





}


