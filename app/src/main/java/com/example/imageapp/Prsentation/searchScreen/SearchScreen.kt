package com.example.imageapp.Prsentation.searchScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import com.example.imageapp.Prsentation.components.ImageVerticalGrid
import com.example.imageapp.Prsentation.components.ZoomedImageCard
import com.example.imageapp.domain.model.UnsplashImage
import com.example.imageapp.utils.SnackbarEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class) @Composable
fun SearchScreen(snackbarHostState: SnackbarHostState,
                 navHostController: NavHostController,
                 snackbarEvent: Flow<SnackbarEvent>,
                 scrollBehavior: TopAppBarScrollBehavior,
                 images: LazyPagingItems<UnsplashImage>,
                 favoriteImageIDs : List<String>,
                 onBackClick: () -> Unit = { navHostController.navigateUp() },
                 onSearch: (String) -> Unit,
                 onImageClick: (String) -> Unit,
                 toggleFavoriteStatus :(UnsplashImage)->Unit)
{

    val focusRequester = remember { FocusRequester() }
    val foucusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

//For image card
    var showImagePreview by remember { mutableStateOf(false) }
    var activeImage by remember { mutableStateOf<UnsplashImage?>(null) }


//

    var isSugsetionVisible by remember { mutableStateOf(false) }
    LaunchedEffect(key1 = true) {
        snackbarEvent.collect { event ->
            snackbarHostState.showSnackbar(message = event.message, duration = event.duration)
        }
    }


    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize(),
               horizontalAlignment = Alignment.CenterHorizontally) {
            var query by remember { mutableStateOf("") }

            LaunchedEffect(Unit) {
                delay(500)
                focusRequester.requestFocus()

            }

            SearchBar(modifier = Modifier
                .focusRequester(focusRequester)
                .onFocusChanged {
                    isSugsetionVisible = it.isFocused
                },
                      query = query,
                      onQueryChange = { query = it },
                      onSearch = {
                          onSearch(query)
                          keyboardController?.hide()
                          foucusManager.clearFocus()

                      },
                      active = false,
                      onActiveChange = {},
                      placeholder = { Text("Search") },
                      trailingIcon = {

                          IconButton(onClick = {
                              if (query.isNotEmpty())
                              {
                                  query = ""
                                  foucusManager.clearFocus()
                              }
                              else
                              {
                                  foucusManager.clearFocus()
                                  CoroutineScope(Dispatchers.Main).launch {
                                      delay(1000)
                                      onBackClick()
                                  }
                              }
                          }) {
                              Icon(imageVector = Icons.Filled.Close, contentDescription = "close")
                          }

                      },
                      leadingIcon = {
                          Icon(imageVector = Icons.Default.Search, contentDescription = "search")
                      }) {}


            AnimatedVisibility(visible = isSugsetionVisible) {
                LazyRow(contentPadding = PaddingValues(horizontal = 10.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(searchKeywords) { item: String ->
                        SuggestionChip(
                                onClick = {
                                    query = item
                                    keyboardController?.hide()
                                    foucusManager.clearFocus()
                                    onSearch(query)
                                },
                                label = { Text(text = item) },
                                modifier = Modifier,
                        )

                    }
                }

            }


//
            ImageVerticalGrid(
                    images = images,
                    onImageClick = onImageClick,
                    favoriteImageIDs = favoriteImageIDs,

                    onImageDragStart = { image ->
                        activeImage = image
                        showImagePreview = true
                    },
                    onImageDragEnd = { showImagePreview = false },

                    onFevClick = { toggleFavoriteStatus(it)} ,
                    isFev = false,
            )

        }
        ZoomedImageCard(images = activeImage, isVisibility = showImagePreview)


    }

}

val searchKeywords: List<String> = listOf("Landscape",
                                          "Portrait",
                                          "Nature",
                                          "Architecture",
                                          "Travel",
                                          "Food",
                                          "Animals",
                                          "Abstract",
                                          "Technology",
                                          "Fashion",
                                          "Sports",
                                          "Fitness",
                                          "Music",
                                          "Art",
                                          "City",
                                          "Culture",
                                          "Vintage",
                                          "Wellness",
                                          "Education",
                                          "Business")