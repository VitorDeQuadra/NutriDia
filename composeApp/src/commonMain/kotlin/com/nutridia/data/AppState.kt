package com.nutridia.data

import androidx.compose.runtime.*
import com.nutridia.navigation.Destination
import kotlin.math.roundToInt

/** Estado local demonstrativo. Nenhuma autenticação, operação remota ou persistência é simulada como real. */
class AppState {
    var route by mutableStateOf(Destination.Splash); private set
    private val backStack = mutableStateListOf<Destination>()
    val canGoBack get() = backStack.isNotEmpty()
    var message by mutableStateOf<String?>(null)
    var name by mutableStateOf("Mariana Silva")
    var email by mutableStateOf("mariana@email.com")
    var restrictions by mutableStateOf(setOf("Sem lactose"))
    var objective by mutableStateOf("Emagrecimento")
    var calories by mutableStateOf(1780)
    var waterGoal by mutableStateOf(2300)
    var waterPortion by mutableStateOf(250)
    var water by mutableStateOf(1500)
    var weight by mutableStateOf(72.6)
    var targetWeight by mutableStateOf(68.0)
    var theme by mutableStateOf("Do sistema")
    var reminders by mutableStateOf(setOf("Refeições", "Hidratação", "Comunidade"))
    var mealReminderTimes by mutableStateOf("07:00 · 12:30 · 19:30")
    var waterReminderTimes by mutableStateOf("A cada 2 h, das 8h às 20h")
    var showRanking by mutableStateOf(true)
    var onboardingComplete by mutableStateOf(false)
    var meals by mutableStateOf(DemoData.meals)
    var recordedMeals by mutableStateOf(0)
    var foods by mutableStateOf(DemoData.foods)
    var selectedMeal by mutableStateOf("Almoço")
    var selectedFood by mutableStateOf(DemoData.foods[2])
    var pendingItems by mutableStateOf<List<MealItem>>(emptyList())
    var favorites by mutableStateOf(setOf(3))
    var posts by mutableStateOf(DemoData.posts)
    var selectedPostId by mutableStateOf(3)
    var selectedGroupId by mutableStateOf(1)
    var postingGroup by mutableStateOf<String?>(null)
    var groups by mutableStateOf(DemoData.groups)
    var challenges by mutableStateOf(DemoData.challenges)
    var selectedChallengeId by mutableStateOf(1)
    var selectedContent by mutableStateOf(DemoData.contents[0])
    var savedContent by mutableStateOf(setOf(1))
    var savedOnly by mutableStateOf(false)
    var notifications by mutableStateOf(DemoData.notifications)
    val consumed get() = meals.sumOf { it.kcal }.roundToInt()
    val protein get() = meals.flatMap { it.items }.sumOf { it.protein }
    val carbs get() = meals.flatMap { it.items }.sumOf { it.carbs }
    val fat get() = meals.flatMap { it.items }.sumOf { it.fat }
    val selectedPost get() = posts.firstOrNull { it.id == selectedPostId }
    val selectedGroup get() = groups.first { it.id == selectedGroupId }
    val selectedChallenge get() = challenges.first { it.id == selectedChallengeId }

    fun navigate(destination: Destination, replace: Boolean = false) {
        if (route == destination) return
        if (!replace) backStack.add(route)
        route = destination
    }
    fun root(destination: Destination) { backStack.clear(); route = destination }
    fun back() { if (backStack.isNotEmpty()) route = backStack.removeAt(backStack.lastIndex) else root(Destination.Diary) }
    fun returnTo(destination: Destination) {
        val index = backStack.indexOfLast { it == destination }
        if (index >= 0) {
            while (backStack.size > index) backStack.removeAt(backStack.lastIndex)
            route = destination
        } else navigate(destination, replace = true)
    }
    fun notify(text: String) { message = text }
    fun unsupported(feature: String) = notify("$feature: integração ainda não disponível. Nenhuma operação remota foi realizada.")
    fun beginMeal(type: String = "Almoço") {
        selectedMeal = type
        pendingItems = emptyList()
        navigate(Destination.AddMeal)
    }
    fun addFood(food: Food, grams: Double) {
        if (grams > 0 && grams.isFinite()) pendingItems = pendingItems + MealItem(food, grams)
    }
    fun saveMeal() {
        if (pendingItems.isEmpty()) return
        meals = meals.map { if (it.type == selectedMeal) it.copy(items = it.items + pendingItems) else it }
        recordedMeals++
        pendingItems = emptyList()
        root(Destination.Diary)
        notify("Refeição registrada nesta sessão. Dados demonstrativos, sem sincronização.")
    }
    fun changeWater(delta: Int) { water = (water + delta).coerceAtLeast(0) }
    fun toggleFavorite(id: Int) { favorites = if (id in favorites) favorites - id else favorites + id }
    fun toggleSaved(id: Int) { savedContent = if (id in savedContent) savedContent - id else savedContent + id }
    fun updateProfile(newName: String, newRestrictions: Set<String>) {
        if (newName.isBlank()) return
        val oldName = name
        name = newName.trim()
        restrictions = newRestrictions
        posts = posts.map { post -> post.copy(
            author = if (post.author == oldName) name else post.author,
            comments = post.comments.map { if (it.author == oldName) it.copy(author = name) else it },
        ) }
    }
    fun toggleLike(id: Int) {
        posts = posts.map { if (it.id == id) it.copy(liked = !it.liked, likes = it.likes + if (it.liked) -1 else 1) else it }
    }
    fun comment(text: String) {
        if (text.isBlank() || text.length > 500) return
        posts = posts.map { if (it.id == selectedPostId) it.copy(comments = it.comments + Comment(name, text.trim(), "agora")) else it }
    }
    fun publish(text: String, group: String) {
        if (text.isBlank() || text.length > 1000) return
        posts = listOf(Post((posts.maxOfOrNull { it.id } ?: 0) + 1, name, group, text.trim(), "agora", 0)) + posts
        root(Destination.Community)
        notify("Publicação adicionada ao mural local de demonstração.")
    }
    fun toggleGroup(id: Int) {
        groups = groups.map { if (it.id == id) it.copy(joined = !it.joined, members = it.members + if (it.joined) -1 else 1) else it }
    }
    fun joinChallenge(id: Int) {
        challenges = challenges.map { if (it.id == id && !it.joined) it.copy(joined = true, participants = it.participants + 1) else it }
    }
    fun markChallenge(id: Int) {
        challenges = challenges.map { if (it.id == id && it.joined && !it.markedToday && it.completed < it.days)
            it.copy(completed = it.completed + 1, markedToday = true) else it }
    }
    fun clearSession() {
        name = "Mariana Silva"; email = "mariana@email.com"; restrictions = setOf("Sem lactose")
        meals = DemoData.meals; foods = DemoData.foods; pendingItems = emptyList(); water = 1500
        posts = DemoData.posts; groups = DemoData.groups; challenges = DemoData.challenges
        notifications = DemoData.notifications; savedContent = setOf(1); favorites = setOf(3)
        calories = 1780; waterGoal = 2300; weight = 72.6; targetWeight = 68.0
        objective = "Emagrecimento"; waterPortion = 250; reminders = setOf("Refeições", "Hidratação", "Comunidade")
        theme = "Do sistema"; showRanking = true; savedOnly = false; postingGroup = null
        recordedMeals = 0; mealReminderTimes = "07:00 · 12:30 · 19:30"; waterReminderTimes = "A cada 2 h, das 8h às 20h"
        selectedMeal = "Almoço"; selectedFood = DemoData.foods[2]; selectedPostId = 3
        selectedGroupId = 1; selectedChallengeId = 1; selectedContent = DemoData.contents[0]
        root(Destination.Login)
    }
}

fun number(value: Double, decimals: Int = 1): String {
    if (decimals == 0) return value.roundToInt().toString()
    val rounded = (value * 10).roundToInt()
    return "${rounded / 10},${kotlin.math.abs(rounded % 10)}"
}
