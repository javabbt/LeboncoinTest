package com.yannick.leboncoin.feature.home.presentation.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.leboncoin.resources.R
import com.yannick.leboncoin.base.presentation.compose.composable.useDebounce
import com.yannick.leboncoin.base.presentation.compose.theme.Typography

const val NothingFoundTag = "Nothing Found"
const val LoadingTag = "Loading"
const val SearchTag = "Search"
const val ListTag = "List"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onQueryChanged: (String) -> Unit,
    uiState: UiState,
    onRefresh: () -> Unit,
) {
    Scaffold(
        topBar = {
            CustomSearchBar(
                onSearchQuery = {
                    onQueryChanged(it)
                },
                onActiveChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag(SearchTag)
                    .padding(horizontal = 16.dp),
                placeHolder = {
                    Text(
                        text = stringResource(id = R.string.search_placeholder),
                        style = Typography.bodyLarge,
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = stringResource(id = R.string.search_placeholder),
                        tint = Color.Gray,
                    )
                },
                querySaved = uiState.query,
            )
        },
        modifier = Modifier.padding(vertical = 16.dp),
        floatingActionButton = {
            FloatingActionButton(onClick = { onRefresh() }) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = stringResource(id = R.string.refresh),
                )
            }
        },
    ) { padding ->
        if (uiState.isLoading) {
            Box(modifier = Modifier.fillMaxWidth()) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(padding)
                        .testTag(LoadingTag)
                        .align(Alignment.Center),
                )
            }
        } else {
            if (uiState.filteredAlbums.isEmpty()) {
                NothingFoundScreen(modifier = modifier.testTag(NothingFoundTag))
            } else {
                LazyColumn(
                    modifier = modifier
                        .padding(padding)
                        .testTag(ListTag)
                        .fillMaxSize(),
                ) {
                    items(
                        count = uiState.filteredAlbums.size,
                        key = { index -> uiState.filteredAlbums[index].id },
                    ) { index ->
                        val user = uiState.filteredAlbums[index]
                        ProfileCard(
                            userProfile = user,
                            clickAction = {
                            },
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun NothingFoundScreen(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
        ) {
            Image(
                painter = painterResource(id = R.drawable.nothing_found),
                contentDescription = "",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(200.dp)
                    .padding(16.dp),
            )
            Text(
                text = stringResource(id = R.string.nothing_found),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                textAlign = TextAlign.Center,
                fontSize = 13.sp,
                style = Typography.bodyLarge,
            )
        }
    }
}

@Composable
fun CustomSearchBar(
    onSearchQuery: (String) -> Unit,
    onActiveChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    placeHolder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    querySaved: String,
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    var query by remember {
        mutableStateOf(querySaved)
    }

    query.useDebounce {
        onSearchQuery(it)
    }

    var active by remember {
        mutableStateOf(false)
    }

    val showTrailingIcon by remember {
        derivedStateOf { query.isNotEmpty() }
    }

    Box(
        modifier = modifier,
    ) {
        BasicTextField(
            value = query,
            onValueChange = {
                query = it
            },
            textStyle = Typography.bodyLarge,
            enabled = enabled,
            singleLine = true,
            modifier = Modifier
                .height(56.dp)
                .focusRequester(focusRequester)
                .onFocusChanged { onActiveChange(it.isFocused) }
                .semantics {
                    onClick {
                        focusRequester.requestFocus()
                        true
                    }
                },
            decorationBox = { innerTextField ->
                Box(
                    contentAlignment = Alignment.CenterStart,
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            color = Color.LightGray,
                            shape = RoundedCornerShape(size = 30.dp),
                        )
                        .padding(horizontal = 16.dp, vertical = 12.dp), // inner padding

                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(),
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .align(Alignment.CenterStart),
                        ) {
                            leadingIcon?.let { it() }
                            Spacer(modifier = Modifier.width(8.dp))
                            Box {
                                if (query.isEmpty()) {
                                    placeHolder?.let { it() }
                                }
                                innerTextField()
                            }
                        }
                        if (showTrailingIcon) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .align(Alignment.CenterEnd),
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = stringResource(id = R.string.close),
                                    tint = Color.Gray,
                                    modifier = Modifier.clickable {
                                        query = ""
                                        active = false
                                        focusManager.clearFocus()
                                    },
                                )
                            }
                        }
                    }
                }
            },
        )
        LaunchedEffect(active) {
            if (!active) {
                focusManager.clearFocus()
            }
        }
    }
}
