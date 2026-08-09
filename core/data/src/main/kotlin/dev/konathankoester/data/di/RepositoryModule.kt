package dev.konathankoester.data.di

import dev.konathankoester.ai_gemini.di.geminiModule
import dev.konathankoester.data.DefaultWordEnrichmentRepository
import dev.konathankoester.data.WordEnrichmentRepository
import dev.konathankoester.database.di.databaseModule
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val repositoryModule = module {

    single<WordEnrichmentRepository> {
        DefaultWordEnrichmentRepository(get(), get(), get(), get(), Dispatchers.IO)
    }
}

val diModules = listOf(
    geminiModule,
    databaseModule,
    repositoryModule,
)
