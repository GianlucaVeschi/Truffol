package com.example.tartufozon.presentation.components.shimmer

import androidx.compose.animation.transition
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.WithConstraints
import androidx.compose.ui.platform.AmbientDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun LoadingDetailShimmer(
    imageHeight: Dp,
    padding: Dp = 16.dp
){
    WithConstraints(
        modifier = Modifier.fillMaxSize()
    ) {
        val cardWidthPx = with(AmbientDensity.current) { (maxWidth - (padding*2)).toPx() }
        val cardHeightPx = with(AmbientDensity.current) { (imageHeight - padding).toPx() }

        val cardAnimationDefinition = remember{
            ShimmerAnimationDefinitions(
                widthPx = cardWidthPx,
                heightPx = cardHeightPx,
            )
        }

        val cardShimmerTranslateAnim = transition(
            definition = cardAnimationDefinition.shimmerTranslateAnimation,
            initState = ShimmerAnimationDefinitions.AnimationState.START,
            toState = ShimmerAnimationDefinitions.AnimationState.END
        )

        val colors = listOf(
            Color.LightGray.copy(alpha = .9f),
            Color.LightGray.copy(alpha = .3f),
            Color.LightGray.copy(alpha = .9f),
        )
        val xCardShimmer = cardShimmerTranslateAnim[cardAnimationDefinition.xShimmerPropKey]
        val yCardShimmer = cardShimmerTranslateAnim[cardAnimationDefinition.yShimmerPropKey]

        ScrollableColumn {
            val brush = Brush.linearGradient(
                colors,
                start = Offset(xCardShimmer - cardAnimationDefinition.gradientWidth, yCardShimmer - cardAnimationDefinition.gradientWidth),
                end = Offset(xCardShimmer, yCardShimmer)
            )
            Surface(
                shape = MaterialTheme.shapes.small,
            ) {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .preferredSize(imageHeight)
                        .background(brush = brush)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Surface(
                shape = MaterialTheme.shapes.small,
                modifier = Modifier
                    .padding(vertical = 8.dp)
            ) {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .preferredHeight(imageHeight / 10)
                        .background(brush = brush)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Surface(
                shape = MaterialTheme.shapes.small,
                modifier = Modifier
                    .padding(vertical = 8.dp)
            ) {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .preferredHeight(imageHeight / 10)
                        .background(brush = brush)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Surface(
                shape = MaterialTheme.shapes.small,
                modifier = Modifier
                    .padding(vertical = 8.dp)
            ) {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .preferredHeight(imageHeight / 10)
                        .background(brush = brush)
                )
            }
        }
    }
}