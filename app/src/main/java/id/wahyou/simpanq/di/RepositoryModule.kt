package id.wahyou.simpanq.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.wahyou.simpanq.data.database.RoomDB
import id.wahyou.simpanq.repository.DataRepository
import id.wahyou.simpanq.repository.local.LocalRepository
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    fun provideLocalRepository(
        db : RoomDB,
        disposable: CompositeDisposable
    ) : LocalRepository = LocalRepository(db, disposable)

    @Provides
    @Singleton
    fun provideDataRepository(
        localRepository: LocalRepository
    ) : DataRepository = DataRepository(localRepository)

    @Singleton
    @Provides
    fun provideDisposible() : CompositeDisposable = CompositeDisposable()
}