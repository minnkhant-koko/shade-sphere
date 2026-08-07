package dev.konathankoester.shade_sphere

import android.app.Application
import dev.konathankoester.data.di.diModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ShadeSphereApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ShadeSphereApp)
            modules(diModules)
        }
    }
}