package com.willowtreeapps.namegame

import com.willowtreeapps.common.*
import com.willowtreeapps.common.repo.MockRepositoryFactory
import com.willowtreeapps.common.util.takeRandomDistinct
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import java.lang.Exception
import java.lang.IllegalStateException

class ReducersTest {

    /*
    @Test
    fun `take random N items`() {
        val list = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 1, 2, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)
        val randomList = list.takeRandomDistinct(10)
        assert(randomList.size == 10)
        assert(randomList.distinctBy { it }.size == 10)
    }

    @Test
    fun `throw exception on N greater than size`() {
        val list = listOf(1, 2)
        val value = try {
            list.takeRandomDistinct(3)
        } catch (e: Exception) {
            e
        }

        assert(value is IllegalStateException)
    }
    @Test
    fun `isLoadingProfiles set false on failure`() {
        val initial = generateInitialTestState().copy(isLoadingItems = true)
        val final: AppState = reducer(initial, Actions.FetchingItemsFailedAction("Test failure")) as AppState

        assertFalse(final.isLoadingItems)
    }


    private fun generateInitialTestState(): AppState {
        val initialState = reducer(AppState(), Actions.FetchingItemsSuccessAction(runBlocking { ProfileItemRepository(MockRepositoryFactory().success()).fetchItems() }.response!!))
        return initialState
    }

    val justinTimberlake = Item(ItemId("0"), "", "Justin", "Timberlake")
    val bobEvans = Item(ItemId("1"), "", "Bob", "Evans")
    val stanLee = Item(ItemId("2"), "", "Stan", "Lee")
    val lukeSkywalker = Item(ItemId("3"), "", "Luke", "Skywalker")

    fun staticTestState(correctAnswer: Item): AppState {

        val items = listOf(
                justinTimberlake,
                bobEvans,
                stanLee,
                lukeSkywalker
        )
        val questions = listOf(
                Question(itemId = correctAnswer.id,
                        choices = items,
                        status = Question.Status.UNANSWERED
                )
        )
        return AppState(isLoadingItems = false,
                items = items,
                questions = questions
        )
    }

     */

}