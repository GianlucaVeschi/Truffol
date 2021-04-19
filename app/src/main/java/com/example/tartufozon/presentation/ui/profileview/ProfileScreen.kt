package com.example.tartufozon.presentation.ui.profileview

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tartufozon.R
import com.example.tartufozon.presentation.ui.Screens

private val name = "Gianluca Veschi"
private val email = "gianluca.veschi00@gmail.com"
const val linkedInUrl = "https://www.linkedin.com/in/gianlucaveschi/"
const val githubUrl = "https://github.com/GianlucaVeschi"
const val githubRepoUrl = "https://github.com/GianlucaVeschi/Tartufozon"
const val gianlucaWebsiteUrl = "https://gianlucaveschi.com"

//NOTE: This stuff should usually be in a parent activity/Navigator
// We can pass callback to profileScreen to get the click.
private fun launchSocialActivity(context: Context, socialType: String) {
    val intent = when (socialType) {
        "github" -> Intent(Intent.ACTION_VIEW, Uri.parse(githubUrl))
        "repository" -> Intent(Intent.ACTION_VIEW, Uri.parse(githubRepoUrl))
        "mywebsite" -> Intent(Intent.ACTION_VIEW, Uri.parse(gianlucaWebsiteUrl))
        else -> Intent(Intent.ACTION_VIEW, Uri.parse(linkedInUrl))
    }
    context.startActivity(intent)
}

@ExperimentalMaterialApi
@Composable
fun ProfileScreen() {

    val navController: NavHostController = rememberNavController()

    NavHost(navController, startDestination = Screens.ProfileScreen.route) {
        composable(Screens.ProfileScreen.route) { ProfileScreenContent(navController) }
    }
}

@ExperimentalMaterialApi
@Composable
fun ProfileScreenContent(navController: NavController) {
    Scaffold {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .semantics { testTag = "Profile Screen" }
        ) {
            val scrollState = rememberScrollState(0)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(state = scrollState)
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                TopScrollingContent(scrollState)
                BottomScrollingContent(navController)
            }
        }
    }
}

@Composable
fun TopScrollingContent(scrollState: ScrollState) {
    Row {
        ProfileImage()
        Column(
            modifier = Modifier
                .padding(start = 8.dp, top = 48.dp)
        ) {
            Text(
                text = name,
                style = typography.h6.copy(fontSize = 18.sp),
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = "Android Developer based in Berlin.",
                style = typography.subtitle2
            )
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun BottomScrollingContent(navController: NavController) {

    Column(
        modifier = Modifier
            .background(MaterialTheme.colors.surface)
            .padding(8.dp)
    ) {
        AboutProjectSection()
        MoreInfoSection(navController)
    }
}

@Composable
fun AboutProjectSection() {
    Text(
        text = "About Project",
        style = typography.h6,
        modifier = Modifier.padding(start = 8.dp, top = 16.dp)
    )
    Divider(modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp))
    Text(
        text = stringResource(id = R.string.about_project),
        style = typography.body1,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
    )
}

@ExperimentalMaterialApi
@Composable
fun MoreInfoSection(navController: NavController) {
    val context = LocalContext.current
    Text(
        text = "More Info",
        style = typography.h6,
        modifier = Modifier.padding(start = 8.dp, top = 16.dp)
    )
    Divider(modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp))

    ListItem(
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_github_square_brands),
                contentDescription = "item icon"
            )
        },
        text = {
            Text(
                text = "Github",
                style = typography.body1.copy(fontWeight = FontWeight.Bold)
            )
        },
        secondaryText = { Text(text = "Tap to visit my GitHub profile") },
        modifier = Modifier
            .clickable(onClick = {
                launchSocialActivity(context, "githubUrl")
            })
    )

    ListItem(
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_email_32),
                contentDescription = "item icon"
            )
        },
        text = {
            Text(
                text = "Send me an email",
                style = typography.body1.copy(fontWeight = FontWeight.Bold)
            )
        },
        secondaryText = { Text(text = "Tap to write me about any concern or info at $email") },
        modifier = Modifier.clickable(
            onClick = {
                val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:${email}")
                }
                ContextCompat.startActivity(
                    context,
                    Intent.createChooser(emailIntent, "Send feedback"),
                    null)
            })
    )

    ListItem(
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_linkedin_brands),
                "linkedin icon"
            )
        },
        text = {
            Text(
                text = "Contact Me on Linkedin",
                style = typography.body1.copy(fontWeight = FontWeight.Bold)
            )
        },
        secondaryText = { Text(text = "Tap to visit my linkedin profile") },
        modifier = Modifier
            .clickable(onClick = { launchSocialActivity(context, "linkedin") })
    )

    ListItem(
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_person_32),
                "Person icon"
            )
        },
        text = {
            Text(
                text = "Visit my website",
                style = typography.body1.copy(fontWeight = FontWeight.Bold)
            )
        },
        secondaryText = { Text(text = "Tap to visit gianlucaveschi.com") },
        modifier = Modifier
            .clickable(onClick = { launchSocialActivity(context, "mywebsite") })
    )
    Divider(modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp))
}

@Composable
fun ProfileImage() {
    Image(
        painter = painterResource(id = R.drawable.p1),
        contentDescription = "animated image",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .padding(start = 16.dp)
            .clip(CircleShape)
    )
}

@ExperimentalMaterialApi
@Preview
@Composable
fun ShowProfileScreen() {
    ProfileScreen()
}
