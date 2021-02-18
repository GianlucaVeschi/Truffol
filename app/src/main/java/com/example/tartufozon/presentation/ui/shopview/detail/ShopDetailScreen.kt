package com.example.tartufozon.presentation.ui.shopview.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tartufozon.R
import com.example.tartufozon.domain.model.Shop
import com.example.tartufozon.presentation.components.ShopDetailView
import com.example.tartufozon.util.Constants.SHOP_KEY

@Composable
fun ShopDetailScreen(navController: NavController){
    val shopDetail : Shop = navController.previousBackStackEntry?.arguments?.getParcelable<Shop>(SHOP_KEY)!!
    shopDetail.let {
        val scaffoldState = rememberScaffoldState()
        Scaffold(
            scaffoldState = scaffoldState,
            snackbarHost = {
                scaffoldState.snackbarHostState
            }
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                ShopDetailView(shop = shopDetail)
            }
        }
    }
}

@Composable
fun NewsStory() {
    val image = imageResource(R.drawable.panorama_view)
    MaterialTheme {
        val typography = MaterialTheme.typography
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            val imageModifier = Modifier
                .preferredHeight(180.dp)
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(4.dp))

            Image(image,
                modifier = imageModifier,
                contentDescription = null,
                contentScale = ContentScale.Crop)
            Spacer(Modifier.preferredHeight(16.dp))

            Text(
                "A day wandering through the sandhills " +
                        "in Shark Fin Cove, and a few of the " +
                        "sights I saw",
                style = typography.h6,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis)
            Text("Davenport, California",
                style = typography.body2)
            Text("December 2018",
                style = typography.body2)
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    NewsStory()
}

