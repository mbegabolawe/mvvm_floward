package co.za.androidalien.floward

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import co.za.androidalien.floward.di.allModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class FlowardApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@FlowardApplication)
            androidLogger(Level.ERROR)
            modules(allModules)
        }
         }
}