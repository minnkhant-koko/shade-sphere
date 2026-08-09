package dev.konathankoester.feature.words.di

import dev.konathankoester.feature.words.WordsDashboardViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val wordsModule = module {
    viewModel { WordsDashboardViewModel() }
}
