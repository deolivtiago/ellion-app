package com.clarxlabs.ellion.book.presentation.book_list

import com.clarxlabs.ellion.book.domain.Book

sealed interface BookListAction {
    data class OnSearchQueryChange(val query: String): BookListAction
    data class OnBookClicked(val book: Book): BookListAction
    data class OnTabSelected(val index: Int): BookListAction
}
