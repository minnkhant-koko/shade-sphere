package dev.konathankoester.feature.practice.di

import dev.konathankoester.feature.practice.PracticeResultsViewModel
import dev.konathankoester.feature.practice.PracticeSessionViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val practiceModule = module {
    viewModel { PracticeSessionViewModel() }
    viewModel { PracticeResultsViewModel() }
}
