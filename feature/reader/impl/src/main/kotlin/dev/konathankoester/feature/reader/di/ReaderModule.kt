package dev.konathankoester.feature.reader.di

import dev.konathankoester.feature.reader.BookshelfViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val readerModule = module {
    viewModel { BookshelfViewModel() }
}
