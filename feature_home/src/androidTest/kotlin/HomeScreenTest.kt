package com.yannick.leboncoin.feature.home.presentation.screen.home

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.yannick.leboncoin.feature.home.domain.model.AlbumUiModel
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun homeScreenComponents_Displayed() {
        val mockUiState = UiState(
            isLoading = false,
            filteredAlbums = listOf(
                AlbumUiModel(
                    id = 1,
                    title = "Title",
                    thumbnailUrl = "https://via.placeholder.com/150",
                    url = "https://via.placeholder.com/150",
                    albumId = 1,
                ),

            ),
            query = "",
        )

        composeTestRule.setContent {
            HomeScreen(
                onQueryChanged = {},
                uiState = mockUiState,
                onRefresh = {},
            )
        }

        composeTestRule.onNodeWithTag(SearchTag).assertIsDisplayed()
        composeTestRule.onNodeWithTag(ListTag).assertIsDisplayed()
    }

    @Test
    fun homeScreen_LoadingState() {
        composeTestRule.setContent {
            HomeScreen(
                onQueryChanged = {},
                uiState = UiState(isLoading = true, filteredAlbums = emptyList(), query = ""),
                onRefresh = {},
            )
        }

        composeTestRule.onNodeWithTag(LoadingTag).assertIsDisplayed()
    }

    @Test
    fun homeScreen_EmptyState() {
        composeTestRule.setContent {
            HomeScreen(
                onQueryChanged = {},
                uiState = UiState(isLoading = false, filteredAlbums = emptyList(), query = ""),
                onRefresh = {},
            )
        }

        composeTestRule.onNodeWithTag(NothingFoundTag).assertIsDisplayed()
    }

    @Test
    fun homeScreen_RefreshInteraction() {
        var refreshInvoked = false

        composeTestRule.setContent {
            HomeScreen(
                onQueryChanged = {},
                uiState = UiState(isLoading = false, filteredAlbums = listOf(), query = ""),
                onRefresh = { refreshInvoked = true },
            )
        }

        composeTestRule.onNodeWithContentDescription("Refresh").performClick()
        assert(refreshInvoked)
    }
}
