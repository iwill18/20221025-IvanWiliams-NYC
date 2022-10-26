package com.example.a20221025_ivanwilliams_nycschools.data.di

import com.example.a20221025_ivanwilliams_nycschools.data.api.BASE_URL
import com.example.a20221025_ivanwilliams_nycschools.data.api.NYCSchoolsInterface
import com.example.a20221025_ivanwilliams_nycschools.repository.Repository
import com.example.a20221025_ivanwilliams_nycschools.repository.RepositoryImpl
import com.example.a20221025_ivanwilliams_nycschools.ui.viewmodel.SchoolViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import io.reactivex.rxjava3.disposables.CompositeDisposable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun providesServiceApi(): NYCSchoolsInterface =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
            .create(NYCSchoolsInterface::class.java)

    @Provides
    fun providesCompositeDisposable(): CompositeDisposable =
        CompositeDisposable()
}

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepoModule {
    @Binds
    abstract fun providesRepository(repositoryImpl: RepositoryImpl): Repository
}

@InstallIn(ActivityComponent::class)
@Module
class ViewModelModule{
    @Provides
    fun providesVM(
        repository: Repository,
        compositeDisposable: CompositeDisposable
    ): SchoolViewModelProvider {
        return SchoolViewModelProvider(repository, compositeDisposable)
    }
}