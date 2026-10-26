package com.nutridia

import com.nutridia.data.*
import com.nutridia.navigation.Destination
import kotlin.test.*

class AppStateTest {
    @Test fun waterCannotBecomeNegative() {
        val state = AppState()
        state.water = 100
        state.changeWater(-250)
        assertEquals(0, state.water)
        state.changeWater(250)
        assertEquals(250, state.water)
    }

    @Test fun portionScalesEveryNutrient() {
        val item = MealItem(DemoData.foods[2], 150.0)
        assertEquals(247.5, item.kcal, .0001)
        assertEquals(46.5, item.protein, .0001)
        assertEquals(0.0, item.carbs, .0001)
        assertEquals(5.4, item.fat, .0001)
    }

    @Test fun savingLunchPreservesOtherMealsAndAddsCalories() {
        val state = AppState()
        val previous = state.meals
        val before = state.meals.sumOf { it.kcal }
        state.beginMeal("Almoço")
        state.addFood(DemoData.foods[0], 100.0)
        state.saveMeal()
        assertEquals(before + 130, state.meals.sumOf { it.kcal }, .0001)
        assertEquals(previous[0], state.meals[0])
        assertEquals(previous[2], state.meals[2])
        assertEquals(previous[1].items.size + 1, state.meals[1].items.size)
        assertTrue(state.pendingItems.isEmpty())
        assertEquals(Destination.Diary, state.route)
    }

    @Test fun invalidPortionsAreRejected() {
        val state = AppState()
        state.addFood(DemoData.foods[0], -1.0)
        state.addFood(DemoData.foods[0], 0.0)
        state.addFood(DemoData.foods[0], Double.NaN)
        state.addFood(DemoData.foods[0], Double.POSITIVE_INFINITY)
        assertTrue(state.pendingItems.isEmpty())
    }

    @Test fun savingEmptySelectionDoesNotNavigateOrChangeDiary() {
        val state = AppState()
        val previous = state.meals
        state.beginMeal()
        state.saveMeal()
        assertEquals(previous, state.meals)
        assertEquals(Destination.AddMeal, state.route)
    }

    @Test fun challengeCannotBeMarkedTwiceInSession() {
        val state = AppState()
        val before = state.challenges.first { it.id == 1 }.completed
        repeat(3) { state.markChallenge(1) }
        val challenge = state.challenges.first { it.id == 1 }
        assertEquals(before + 1, challenge.completed)
        assertTrue(challenge.markedToday)
    }

    @Test fun challengeRequiresParticipation() {
        val state = AppState()
        state.markChallenge(3)
        assertEquals(0, state.challenges.first { it.id == 3 }.completed)
        state.joinChallenge(3)
        state.markChallenge(3)
        assertEquals(1, state.challenges.first { it.id == 3 }.completed)
    }

    @Test fun joiningGroupTwiceReturnsOriginalMembershipCount() {
        val state = AppState()
        val original = state.groups.first { it.id == 1 }
        state.toggleGroup(1)
        assertEquals(original.members + 1, state.groups.first { it.id == 1 }.members)
        state.toggleGroup(1)
        assertEquals(original, state.groups.first { it.id == 1 })
    }

    @Test fun returningFromFoodSkipsSearchAndPreservesMealParent() {
        val state = AppState()
        state.root(Destination.Diary)
        state.beginMeal()
        state.navigate(Destination.FoodSearch)
        state.navigate(Destination.FoodDetail)
        state.returnTo(Destination.AddMeal)
        assertEquals(Destination.AddMeal, state.route)
        state.back()
        assertEquals(Destination.Diary, state.route)
        assertFalse(state.canGoBack)
    }

    @Test fun likesCanBeToggledWithoutChangingOtherPosts() {
        val state = AppState()
        val original = state.posts
        state.toggleLike(1)
        assertEquals(original[0].likes + 1, state.posts[0].likes)
        assertEquals(original[1], state.posts[1])
        state.toggleLike(1)
        assertEquals(original, state.posts)
    }

    @Test fun commentsAreValidatedAndAttachedToSelectedPost() {
        val state = AppState()
        state.selectedPostId = 3
        val count = state.selectedPost!!.comments.size
        state.comment("")
        state.comment("a".repeat(501))
        assertEquals(count, state.selectedPost!!.comments.size)
        state.comment("  Boa ideia!  ")
        assertEquals(count + 1, state.selectedPost!!.comments.size)
        assertEquals("Boa ideia!", state.selectedPost!!.comments.last().text)
    }

    @Test fun sessionsDoNotShareMutableDataAndResetRemovesLocalChanges() {
        val first = AppState()
        val second = AppState()
        first.name = "Outro nome"
        first.publish("Publicação local", "Iniciantes")
        first.toggleLike(1)
        assertEquals(DemoData.posts, second.posts)
        first.clearSession()
        assertEquals(DemoData.posts, first.posts)
        assertEquals("Mariana Silva", first.name)
        assertEquals(Destination.Login, first.route)
    }
}
