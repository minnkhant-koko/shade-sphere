package dev.konathankoester.shade_sphere

import android.app.Application
import dev.konathankoester.data.di.diModules
import dev.konathankoester.feature.news.di.newsModule
import dev.konathankoester.feature.practice.di.practiceModule
import dev.konathankoester.feature.reader.di.readerModule
import dev.konathankoester.feature.words.di.wordsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ShadeSphereApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ShadeSphereApp)
            modules(diModules + listOf(practiceModule, readerModule, newsModule, wordsModule))
        }
    }
}
