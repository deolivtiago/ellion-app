package com.clarxlabs.ellion.book.presentation.book_list

import com.clarxlabs.ellion.book.domain.Book

data class BookListModel(
    val searchQuery: String = "kotlin",
    val searchResults: List<Book> = emptyList<Book>(),
    val favoriteBooks: List<Book> = emptyList<Book>(),
    val isLoading: Boolean = false,
    val selectedTabIndex: Int = 0,
    val errorMessage: String = "",
)
