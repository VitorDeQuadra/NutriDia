// ORIGEM: CODEX | Data de criacao: 2026-10-20
package com.nutridia.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nutridia.core.components.*
import com.nutridia.data.AppState
import com.nutridia.feature.auth.*
import com.nutridia.feature.goals.InitialGoalsScreen
import com.nutridia.feature.goals.ProgressScreen
import com.nutridia.feature.goals.GoalsScreen
import com.nutridia.feature.diary.*
import com.nutridia.feature.community.*
import com.nutridia.feature.content.*
import com.nutridia.feature.profile.*

@Composable
fun NutriNavHost(state: AppState) {
    BackNavigation(state.canGoBack, state::back)
    val snackbar = remember { SnackbarHostState() }
    LaunchedEffect(state.message) {
        state.message?.let { val current = it; snackbar.showSnackbar(it); if (state.message == current) state.message = null }
    }
    Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
    Box(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = .3f)), contentAlignment = Alignment.Center) {
      Box(Modifier.widthIn(max = 640.dp).fillMaxSize().background(MaterialTheme.colorScheme.background).safeDrawingPadding()) {
        key(state.route) {
            when (state.route) {
                Destination.Splash -> SplashScreen { state.root(if (state.onboardingComplete) Destination.Login else Destination.Onboarding) }
                Destination.Onboarding -> OnboardingScreen { state.onboardingComplete = true; state.root(Destination.Login) }
                Destination.Login -> LoginScreen(onValidated = { email -> state.email = email; state.root(Destination.Diary)
                    state.notify("Modo demonstração: validação local, sem autenticação real.") }, onSignUp = { state.navigate(Destination.SignUp) })
                Destination.SignUp -> SignUpScreen(state)
                Destination.InitialGoals -> InitialGoalsScreen(state)
                Destination.Diary -> DiaryScreen(state)
                Destination.AddMeal -> AddMealScreen(state)
                Destination.FoodSearch -> FoodSearchScreen(state)
                Destination.FoodDetail -> FoodDetailScreen(state)
                Destination.History -> HistoryScreen(state)
                Destination.Progress -> ProgressScreen(state)
                Destination.Community -> CommunityScreen(state)
                Destination.CreatePost -> CreatePostScreen(state)
                Destination.PostDetail -> PostDetailScreen(state)
                Destination.Groups -> GroupsScreen(state)
                Destination.GroupDetail -> GroupDetailScreen(state)
                Destination.Challenges -> ChallengesScreen(state)
                Destination.ChallengeDetail -> ChallengeDetailScreen(state)
                Destination.Content -> ContentScreen(state)
                Destination.RecipeDetail -> RecipeDetailScreen(state)
                Destination.Profile -> ProfileScreen(state)
                Destination.EditProfile -> EditProfileScreen(state)
                Destination.Goals -> GoalsScreen(state)
                Destination.Notifications -> NotificationsScreen(state)
                Destination.Settings -> SettingsScreen(state)
            }
        }
        SnackbarHost(snackbar, Modifier.align(Alignment.BottomCenter).imePadding())
      }
    }
    }
}
