package com.example.tartufozon.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tartufozon.domain.model.Truffle
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun TruffleCard(
    truffle: Truffle,
    onClick: () -> Unit,
){
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .padding(
                bottom = 6.dp,
                top = 6.dp,
            )
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = 8.dp,
    ) {
//        Column() {
//            truffle.featuredImage?.let { url ->
//                Image(
//                    bitmap = imageResource(id = R.drawable.),
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .preferredHeight(225.dp),
//                    contentScale = ContentScale.Crop,
//                )
//            }
//            truffle.tartufoName?.let { title ->
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(top=12.dp, bottom=12.dp, start = 8.dp, end=8.dp)
//                ){
//                    Text(
//                        text = title,
//                        modifier = Modifier
//                            .fillMaxWidth(0.85f)
//                            .wrapContentWidth(Alignment.Start)
//                        ,
//                        style = MaterialTheme.typography.h5
//                    )
//                    val rank = truffle.rating.toString()
//                    Text(
//                        text = rank,
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .wrapContentWidth(Alignment.End)
//                            .align(Alignment.CenterVertically),
//                        style = MaterialTheme.typography.h6
//                    )
//                }
//            }
//        }
    }
}

