package dev.konathankoester.database.di

import dev.konathankoester.database.ShadeSphereDatabase
import org.koin.dsl.module

val databaseModule = module {

    single {
        ShadeSphereDatabase.getAppDatabase(applicationContext = get())
    }

    single { get<ShadeSphereDatabase>().wordSenseDao() }
    single { get<ShadeSphereDatabase>().wordDao() }
    single { get<ShadeSphereDatabase>().highlightDao() }
    single { get<ShadeSphereDatabase>().userWordDao() }
}
