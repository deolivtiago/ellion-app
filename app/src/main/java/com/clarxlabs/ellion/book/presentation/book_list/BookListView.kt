package com.clarxlabs.ellion.book.presentation.book_list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.clarxlabs.ellion.book.domain.Book
import com.clarxlabs.ellion.book.presentation.book_list.components.BookSearchBar
import com.clarxlabs.ellion.ui.theme.EllionTheme

@Composable
fun BookListView(
    viewModel: BookListViewModel,
    onBookClicked: (Book) -> Unit
) {
    BookList(
        state = viewModel.state,
        onAction = {
            when (it) {
                is BookListAction.OnBookClicked -> onBookClicked(it.book)
                else -> Unit
            }

            viewModel.onAction(it)
        }
    )
}

@Composable
private fun BookList(
    state: BookListModel,
    onAction: (BookListAction) -> Unit
) {
val keyboardController = LocalSoftwareKeyboardController.current

    Column(
horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()

    ) {
        BookSearchBar(
            searchQuery = state.searchQuery,
            onSearchQueryChange = { onAction(BookListAction.OnSearchQueryChange(it)) },
            onImeSearch = { keyboardController?.hide() },
            modifier = Modifier
                .widthIn(max = 400.dp)
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}

@Preview
@Composable
private fun BookListViewPreview() {
    EllionTheme {
        BookList(
            state = BookListModel(),
            onAction = {}
        )
    }
}