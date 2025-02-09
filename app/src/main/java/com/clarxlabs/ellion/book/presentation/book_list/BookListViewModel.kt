package com.clarxlabs.ellion.book.presentation.book_list

import androidx.compose.runtime.*

import androidx.lifecycle.ViewModel

class BookListViewModel : ViewModel() {
     val state by mutableStateOf(BookListModel())

    fun onAction(action: BookListAction) {
        when(action) {
            is BookListAction.OnBookClicked -> {
                state.copy()
            }
            is BookListAction.OnSearchQueryChange -> {
                state.copy(searchQuery = action.query)
            }
            is BookListAction.OnTabSelected -> {
                state.copy(selectedTabIndex = action.index)
            }
        }
    }
}
