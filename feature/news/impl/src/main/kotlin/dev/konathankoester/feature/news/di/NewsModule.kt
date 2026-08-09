package dev.konathankoester.feature.news.di

import dev.konathankoester.feature.news.NewsFeedViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val newsModule = module {
    viewModel { NewsFeedViewModel() }
}
