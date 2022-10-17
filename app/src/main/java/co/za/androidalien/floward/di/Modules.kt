package co.za.androidalien.floward.di

import co.za.androidalien.floward.utils.Constants
import co.za.androidalien.floward.data.database.MyDatabase
import co.za.androidalien.floward.data.services.PostService
import co.za.androidalien.floward.data.services.UserService
import co.za.androidalien.floward.data.repository.PostRepository
import co.za.androidalien.floward.data.repository.UserRepository
import co.za.androidalien.floward.ui.viewmodel.MainActivityViewModel
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private val databaseModule = module {
    single { MyDatabase.getInstance(androidContext()) }
    single { get<MyDatabase>().postDao() }
    single { get<MyDatabase>().userDao() }
}

private fun provideOkHttpClient(): OkHttpClient {
    return OkHttpClient
        .Builder()
        .build()
}

private val viewModelModule = module {
    viewModel { MainActivityViewModel(get(), get()) }
}

private val repoModule = module {
    single { UserRepository(get(), get()) }
    single { PostRepository(get(), get()) }
}

private fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
    Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(Constants.BASE_URL)
    .client(okHttpClient)
    .build()

private val networkModule = module {
    single { provideOkHttpClient() }
    single { provideRetrofit(get()) }

    single { get<Retrofit>().create(UserService::class.java) }
    single { get<Retrofit>().create(PostService::class.java) }
}


val allModules = listOf(
    databaseModule,
    networkModule,
    repoModule,
    viewModelModule,
)
